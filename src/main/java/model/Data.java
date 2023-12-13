package model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;

public class Data {
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pi", "root", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isUserValid(String username, String password) throws SQLException {
        String md5Password = DigestUtils.md5Hex(password);
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE username = '" + username + "' AND password = '" + md5Password + "'");
        ResultSet rs = ps.executeQuery();
        return rs.isBeforeFirst();
    }

    public static List<Role> roles = new ArrayList<>(getPosts());

    private static List<Role> getPosts() {
        List<Role> roles = new ArrayList<>();
        User user = new User(1, "Andra", "Enea", "Avatar.png");

        Role role1 = new Role();
        role1.setId(1);
        role1.setTitle("Senior Software Developer");
        role1.setCity("Timisoara, Romania");
        role1.setSalaryBudget("15k - 20k");
        role1.getSkills().add( "Angular");
        role1.getSkills().add( "Github");
        role1.getSkills().add( "Jira");
        role1.setStatus(new Status(5, 10, 20));

        Candidate candidate1 = new Candidate(1, "Maria", "Piersica", 25, "Brasov, Romania","https://i.imgur.com/w7X0qJu.png");
        Job job1 = new Job(1, "Intern", LocalDate.of(2020, 3, 12), LocalDate.of(2021, 2, 10), ESeniority.ENTRY, "Nokia");
        Job job2 = new Job(2, "Junior Developer", LocalDate.of(2021, 5, 2), LocalDate.of(2022, 12, 12), ESeniority.JUNIOR, "Intel");
        Job job3 = new Job(3, "Middle Developer", LocalDate.of(2022, 12, 19), LocalDate.of(2023, 12, 5), ESeniority.MIDDLE, "IBM");
        candidate1.getJobs().add(job3);
        candidate1.getJobs().add(job2);
        candidate1.getJobs().add(job1);
        candidate1.getSkills().add("JavaScript");
        candidate1.getSkills().add("Angular");
        candidate1.getSkills().add( "AWS");
        candidate1.getSkills().add( "C++");
        candidate1.getSkills().add( "Python");
        candidate1.getSkills().add("Java" );
        candidate1.getSkills().add("MySQL");

        Candidate candidate2 = new Candidate(2, "Mihai", "Pop", 30, "Timisoara, Romania","https://i.imgur.com/jULTfan.png");
        Job job4 = new Job(4, "Intern", LocalDate.of(2016,5, 10), LocalDate.of(2017, 4, 10), ESeniority.ENTRY, "Continental");
        Job job5 = new Job(5, "Junior Developer", LocalDate.of(2017, 5, 2), LocalDate.of(2022, 12, 12), ESeniority.JUNIOR, "Intel");
        Job job6 = new Job(6, "Middle Developer", LocalDate.of(2022, 12, 19), LocalDate.of(2023, 12, 5), ESeniority.MIDDLE, "Nokia");
        candidate2.getJobs().add(job6);
        candidate2.getJobs().add(job5);
        candidate2.getJobs().add(job4);
        candidate2.getSkills().add("JavaScript");
        candidate2.getSkills().add("Angular");
        candidate2.getSkills().add("AWS");
        candidate2.getSkills().add( "C++");
        candidate2.getSkills().add("Python");
        candidate2.getSkills().add("Java");
        candidate2.getSkills().add("MySQL");

        role1.getCandidates().add(candidate1);
        role1.getCandidates().add(candidate2);
        role1.setOwner(user);
        role1.setActive(true);
        role1.setDate(LocalDate.of(2023, 12, 4));

        Role role2 = new Role();
        role2.setId(3);
        role2.setTitle("Middle Software Developer");
        role2.setCity("Timisoara, Romania");
        role2.setSalaryBudget("6k - 8k");
        role2.getSkills().add("Python");
        role2.getSkills().add("Jenkins");
        role2.getSkills().add("MongoDB");

        role2.setOwner(user);
        role2.setActive(true);
        role2.setDate(LocalDate.of(2023, 12, 2));
        role2.getCandidates().add(candidate1);

        Candidate candidate6 = new Candidate(3, "Andrei", "Neacsu", 28, "Timisoara, Romania","https://imgur.com/YDEKMnk.png");
        Job job7 = new Job(7, "Junior", LocalDate.of(2018, 12, 10), LocalDate.of(2020, 4, 10), ESeniority.JUNIOR, "Continental");
        candidate6.getJobs().add(job7);
        candidate6.getJobs().add(job4);

        candidate6.getSkills().add("C++");
        candidate6.getSkills().add("MySQL");
        candidate6.getSkills().add("Java");
        candidate6.getSkills().add("Python");
        candidate6.getSkills().add("Jenkins");

        role2.getCandidates().add(candidate6);
        role2.setStatus(new Status(20, 40, 45));

        Role role3 = new Role();
        role3.setId(3);
        role3.setTitle("Middle Software Developer");
        role3.setCity("Timisoara, Romania");
        role3.setSalaryBudget("6k - 8k");
        role3.getSkills().add("Python");
        role3.getSkills().add("Jenkins");
        role3.getSkills().add("MongoDB");

        role3.setOwner(user);
        role3.setActive(false);
        role3.setDate(LocalDate.of(2023, 12, 2));
        role3.getCandidates().add(candidate1);

        Candidate candidate3 = new Candidate(3, "Andrei", "Neacsu", 28, "Timisoara, Romania","https://imgur.com/YDEKMnk.png");
        Job job10 = new Job(7, "Junior", LocalDate.of(2018, 12, 10), LocalDate.of(2020, 4, 10), ESeniority.JUNIOR, "Continental");
        candidate3.getJobs().add(job10);
        candidate3.getJobs().add(job4);

        candidate3.getSkills().add("C++");
        candidate3.getSkills().add("MySQL");
        candidate3.getSkills().add("Java");
        candidate3.getSkills().add("Python");
        candidate3.getSkills().add("Jenkins");

        role3.getCandidates().add(candidate3);
        role3.setStatus(new Status(20, 40, 45));

        Role role4 = new Role();
        role4.setId(4);
        role4.setTitle("Graphic Designer");
        role4.setCity("Oradea, Romania");
        role4.setSalaryBudget("4k - 7k");
        role4.getSkills().add("Figma");
        role4.getSkills().add("Adobe");
        role4.getSkills().add("CSS");
        role4.getSkills().add("HTML");

        role4.setOwner(user);
        role4.setActive(true);

        Candidate candidate4 =  new Candidate(4, "Larisa", "Soare", 19, "Arad, Romania","https://imgur.com/08SGkW1.png");
        candidate4.getSkills().add("Figma");
        candidate4.getSkills().add("Blender");
        candidate4.getSkills().add("Photoshop");
        candidate4.getSkills().add("Canva");
        Job job9 = new Job(9, "UI / UX Designer", LocalDate.of(2022, 12, 12), LocalDate.of(2023, 5,23),ESeniority.JUNIOR, "Cobalt Sign");
        candidate4.getJobs().add(job9);
        role4.getCandidates().add(candidate4);

        Candidate candidate5 = new Candidate(5, "Mircea", "Enescu", 23, "Timisoara, Romania", "https://i.imgur.com/WFUT6eE.png");
        candidate5.getSkills().add("Adobe");
        candidate5.getSkills().add("Blender");
        candidate5.getSkills().add("Photoshop");
        candidate5.getSkills().add("Figma");
        Job job8 = new Job(8, "UI / UX Designer", LocalDate.of(2021, 12, 12), LocalDate.of(2023, 11,23),ESeniority.JUNIOR, "Cobalt Sign");
        candidate5.getJobs().add(job8);
        role4.getCandidates().add(candidate5);
        role4.setDate(LocalDate.of(2023, 11, 23));
        role4.setStatus(new Status(10, 18, 20));

        roles.add(role1);
        roles.add(role2);
        roles.add(role3);
        roles.add(role4);
        return roles;
    }

}
