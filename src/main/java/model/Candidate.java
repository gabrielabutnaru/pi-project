package model;

import java.util.ArrayList;
import java.util.List;

/**
 * The class corresponding to the candidate.
 */
public class Candidate {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private List<Job> jobs;
    private String city;
    private List<String> skills;
    private String avatar;
    private EStatus status;
    private String phone;
    private String mail;


    /**
     * The default constructor of the Candidate class.
     */
    public Candidate() {
        this.jobs = new ArrayList<>();
        this.skills = new ArrayList<>();
        this.status = EStatus.DEFAULT;
    }

    /**
     * The complete constructor of the Candidate class.
     * @param id of the candidate
     * @param firstName of the candidate
     * @param lastName of the candidate
     * @param age of the candidate
     * @param city in which the candidate lives in
     * @param avatar url to the candidate's avatar picture
     * @param mail of the candidate
     * @param phone number of the candidate
     * @param status of the candidate
     */
    public Candidate(int id, String firstName, String lastName, int age, String city, String avatar, String mail, String phone, EStatus status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.jobs = new ArrayList<>();
        this.city = city;
        this.skills = new ArrayList<>();
        this.avatar = avatar;
        this.mail = mail;
        this.phone = phone;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<String> getSkills() {
        return skills;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
