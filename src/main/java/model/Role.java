package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Role {
    private int id;
    private String title;
    private String city;
    private Date date;
    private String salaryBudget;
    private List<String> skills;
    private Boolean isActive;
    private List<Candidate> candidates;
    private List<User> sharedWith;
    private Status status;
    private User owner;

    public Role() {
        this.skills = new ArrayList<>();
        this.candidates = new ArrayList<>();
        this.sharedWith = new ArrayList<>();
    }

    public Role(int id, String title, String city, Date date, String salaryBudget, Boolean isActive, User owner, Status status) {
        this.id = id;
        this.title = title;
        this.city = city;
        this.date = date;
        this.salaryBudget = salaryBudget;
        this.skills = new ArrayList<>();
        this.isActive = isActive;
        this.candidates = new ArrayList<>();
        this.sharedWith = new ArrayList<>();
        this.status = status;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSalaryBudget() {
        return salaryBudget;
    }

    public void setSalaryBudget(String salaryBudget) {
        this.salaryBudget = salaryBudget;
    }

    public List<String> getSkills() {
        return skills;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public List<User> getSharedWith() {
        return sharedWith;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", city='" + city + '\'' +
                ", date=" + date +
                ", salaryBudget='" + salaryBudget + '\'' +
                ", skills=" + skills +
                ", isActive=" + isActive +
                ", candidates=" + candidates +
                ", sharedWith=" + sharedWith +
                ", status=" + status +
                ", owner=" + owner +
                '}';
    }
}