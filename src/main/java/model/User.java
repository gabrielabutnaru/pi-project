package model;
/**
 * The class corresponding to the user.
 */
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String avatar;

    /**
     * The complete constructor of the class User.
     * @param id of user
     * @param firstName of user
     * @param lastName of user
     * @param avatar url to the user's avatar picture
     */
    public User(int id, String firstName, String lastName, String avatar) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
    }

    public User() {}

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setId(int id) {
        this.id = id;
    }
}
