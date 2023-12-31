package model;

import java.sql.*;
import java.util.*;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * The class that manages all the app data.
 */
public class Data {
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pi", "root", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Map<String, ESeniority> stringToESeniority = new HashMap<>(){{
        put("ENTRY", ESeniority.ENTRY);
        put("JUNIOR", ESeniority.JUNIOR);
        put("MIDDLE", ESeniority.MIDDLE);
        put("SENIOR", ESeniority.SENIOR);
    }};

    private static final Map<String, EStatus> stringToEStatus = new HashMap<>(){{
        put("DEFAULT", EStatus.DEFAULT);
        put("IN_TOUCH", EStatus.IN_TOUCH);
        put("EMPLOYED", EStatus.EMPLOYED);
        put("OMITTED", EStatus.OMITTED);
        put("FAILED", EStatus.FAILED);
    }};

    public static final Map<EStatus, String> EStatusToString = new HashMap<>(){{
        put(EStatus.DEFAULT, "DEFAULT");
        put(EStatus.IN_TOUCH, "IN_TOUCH");
        put(EStatus.EMPLOYED, "EMPLOYED");
        put(EStatus.OMITTED, "OMITTED");
        put(EStatus.FAILED, "FAILED");
    }};

    public static void setCurrentUser(User currentUser) {
        Data.currentUser = currentUser;
    }

    private static User currentUser;

    /**
     * @param username of the user
     * @param password of the user
     * @return true if user login was successful, false otherwise
     */
    public static boolean isSuccessfullyLoggedIn(String username, String password) throws SQLException {
        String md5Password = DigestUtils.md5Hex(password);
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE username = '" + username + "' AND password = '" + md5Password + "'");
        ResultSet rs = ps.executeQuery();
        if (!rs.isBeforeFirst())
            return false;
        rs.next();
        currentUser = new User(rs.getInt("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("avatar"));
        return true;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static List<Role> roles = new ArrayList<>();

    /**
     * This function loads all the roles from the database.
     */
    public static void loadRoles() throws SQLException {
        roles.clear();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM roles WHERE owner = " + currentUser.getId());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            roles.add(getRoleFromRow(rs));
        }

        ps = connection.prepareStatement("SELECT * FROM roles LEFT JOIN users_roles ON users_roles.roleId = roles.id HAVING users_roles.userId = " + currentUser.getId());
        rs = ps.executeQuery();
        while (rs.next()) {
            roles.add(getRoleFromRow(rs));
        }
        roles.sort(Collections.reverseOrder());
    }

    /**
     * @param rsRole result set for a specific role
     * @return a role
     */
    private static Role getRoleFromRow(ResultSet rsRole) throws SQLException {
        Role role = new Role(rsRole.getInt("id"), rsRole.getString("title"), rsRole.getString("city"), rsRole.getTimestamp("date"), rsRole.getString("salaryBudget"), rsRole.getBoolean("isActive"), getUser(rsRole.getInt("owner")));
        role.getSkills().addAll(new ArrayList<>(Arrays.stream(rsRole.getString("skills").split(",")).toList()));
        PreparedStatement psCandidate = connection.prepareStatement("SELECT * FROM candidates LEFT JOIN statuses ON candidates.id = statuses.candidateId HAVING statuses.roleId = " + rsRole.getInt("id"));
        ResultSet rsCandidate = psCandidate.executeQuery();
        while (rsCandidate.next()) {
            Candidate candidate = new Candidate(rsCandidate.getInt("id"), rsCandidate.getString("firstName"), rsCandidate.getString("lastName"), rsCandidate.getInt("age"), rsCandidate.getString("city"), rsCandidate.getString("avatar"), rsCandidate.getString("mail"), rsCandidate.getString("phone"), stringToEStatus.get(rsCandidate.getString("type")));
            Arrays.stream(rsCandidate.getString("skills").split(",")).forEach(s -> candidate.getSkills().add(s));
            PreparedStatement psJob = connection.prepareStatement("SELECT * FROM jobs WHERE owner = " + rsCandidate.getInt("id"));
            ResultSet rsJob = psJob.executeQuery();
            while (rsJob.next()) {
                candidate.getJobs().add(new Job(rsJob.getInt("id"), rsJob.getString("title"), rsJob.getDate("startDate").toLocalDate(), rsJob.getDate("endDate").toLocalDate(), stringToESeniority.get(rsJob.getString("seniority")), rsJob.getString("company")));
            }
            role.getCandidates().add(candidate);
        }
        return role;
    }

    /**
     * @param userId id of current user
     * @return user data from the database
     */
    private static User getUser(int userId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE id = " + userId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return new User(rs.getInt("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("avatar"));
    }

    /**
     * This function archives a given role.
     * @param roleId id of current role
     */
    public static void archiveRole(int roleId) throws SQLException {
        connection.prepareStatement("UPDATE roles SET isActive = 0 WHERE id = " + roleId).executeUpdate();
    }

    /**
     * This function sets the status for a candidate on a certain role.
     * @param status of candidate
     * @param candidateId id of candidate
     * @param roleId id of role
     */
    public static void setCandidateStatus(EStatus status, int candidateId, int roleId) throws SQLException {
        connection.prepareStatement("UPDATE statuses SET type = '" + EStatusToString.get(status) + "' WHERE candidateId = " + candidateId + " AND roleId = " + roleId).executeUpdate();
    }

    private static int currentRoleId;

    /**
     * @return the current role
     */
    public static Role getCurrentRole() {
        for (Role role : roles) {
            if (role.getId() == currentRoleId)
                return role;
        }
        return null;
    }

    public static int getCurrentRoleId() {
        return currentRoleId;
    }

    public static void setCurrentRoleId(int currentRoleId) {
        Data.currentRoleId = currentRoleId;
    }

    private static int currentCandidateId;

    /**
     * @return the current candidate
     */
    public static Candidate getCurrentCandidate() {
        for (Role role : roles) {
            if (role.getId() == currentRoleId) {
                for (Candidate candidate : role.getCandidates()) {
                    if (candidate.getId() == currentCandidateId) {
                        return candidate;
                    }
                }
            }
        }
        return null;
    }

    public static int getCurrentCandidateId() {
        return currentCandidateId;
    }

    public static void setCurrentCandidateId(int currentCandidateId) {
        Data.currentCandidateId = currentCandidateId;
    }

    /**
     * @param title of the new role
     * @param city of the new role
     * @param salaryBudget for the new role
     * @param skills required for the new role
     * @return a new role
     */
    public static Integer addNewRoleToDataBase(String title, String city, String salaryBudget, String skills) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO roles (title, isActive, owner, date, city, salaryBudget, skills) VALUES ('" + title + "', '" + 1 + "', '" + currentUser.getId() + "', NOW(), '" + city + "', '" + salaryBudget + "', '" + skills + "');", Statement.RETURN_GENERATED_KEYS);
        statement.executeUpdate();
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            else {
                return null;
            }
        }
    }

    /**
     * @return a list of all the candidates from the database
     */
    public static List<Candidate> getAllCandidates() throws SQLException {
        List<Candidate> candidates = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM candidates");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Candidate c = new Candidate();
            c.setId(rs.getInt("id"));
            Arrays.stream(rs.getString("skills").split(",")).forEach(s -> c.getSkills().add(s));
            candidates.add(c);
        }
        return candidates;
    }

    /**
     * This function attributes a candidate to a role.
     * @param candidateId id of candidate
     * @param roleId id of role
     */
    public static void addCandidateToRole(int candidateId, int roleId) throws SQLException {
        connection.prepareStatement("INSERT INTO statuses(type, candidateId, roleId) VALUES ('DEFAULT', '" + candidateId + "', '" + roleId + "')").executeUpdate();
    }

    /**
     * This function shares a role to another user.
     * @param roleId id of role
     * @param userId id of user
     */
    public static void shareRoleToUser(int roleId, int userId) throws SQLException {
        connection.prepareStatement("INSERT INTO users_roles(userId, roleId) VALUES ('" + userId + "', '" + roleId + "')").executeUpdate();
    }

    /**
     * @return a list of all the users, without the current one that is logged in
     */
    public static List<User> getUsersWithoutCurrent() throws SQLException {
        List<User> users = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT id, firstName, lastName, avatar FROM users WHERE id != " + currentUser.getId());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setAvatar(rs.getString("avatar"));
            users.add(user);
        }
        return users;
    }

    /**
     * @param roleId id of role
     * @param userId id of user
     * @return true if the role is shared with a certain user, false otherwise
     */
    public static Boolean isRoleSharedWithUser(int roleId, int userId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM users_roles WHERE userId = '" + userId + "' AND roleId = '" + roleId + "'");
        ResultSet rs = ps.executeQuery();
        return rs.isBeforeFirst();
    }

    /**
     * This function unshares a role with a certain user.
     * @param roleId id of role
     * @param userId if of user
     */
    public static void unshareRoleWithUser(int roleId, int userId) throws SQLException {
        connection.prepareStatement("DELETE FROM users_roles WHERE userId = " + userId + " AND roleId = " + roleId).executeUpdate();
    }

    /**
     * @param roleId id of role
     * @return a list of users which have that role as being shared with
     */
    public static List<User> getRoleSharedWithUsers(int roleId) throws SQLException {
        List<User> users = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM users LEFT JOIN users_roles ON users.id = users_roles.userId HAVING users_roles.roleId = " + roleId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            User user = new User();
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            users.add(user);
        }
        return users;
    }
}

