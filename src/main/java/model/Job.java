package model;

import model.ESeniority;

import java.util.Date;

public class Job {
    private int id;
    private String title;
    private Date startDate;
    private Date endDate;
    private ESeniority seniority;
    private String company;

    public Job(int id, String title, Date startDate, Date endDate, ESeniority seniority, String company) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.seniority = seniority;
        this.company = company;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ESeniority getSeniority() {
        return seniority;
    }

    public void setSeniority(ESeniority seniority) {
        this.seniority = seniority;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
