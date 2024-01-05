package model;

import model.ESeniority;

import java.time.LocalDate;
import java.util.Date;

/**
 * The class representing details about a candidate's job.
 */
public class Job {
    private int id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private ESeniority seniority;
    private String company;

    /**
     *
     * @param id of the job
     * @param title of the job
     * @param startDate employment date
     * @param endDate leave date
     * @param seniority job's seniority
     * @param company the company where the job took place within
     */
    public Job(int id, String title, LocalDate startDate, LocalDate endDate, ESeniority seniority, String company) {
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
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
