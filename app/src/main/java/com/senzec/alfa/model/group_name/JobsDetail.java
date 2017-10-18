package com.senzec.alfa.model.group_name;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by senzec on 12/10/17.
 */
public class JobsDetail {

    @SerializedName("cv")
    @Expose
    private List<Object> cv = null;
    @SerializedName("chats")
    @Expose
    private List<Object> chats = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("job_type")
    @Expose
    private String jobType;
    @SerializedName("job_name")
    @Expose
    private String jobName;
    @SerializedName("job_creater_id")
    @Expose
    private JobCreaterId jobCreaterId;
    @SerializedName("job_experience")
    @Expose
    private String jobExperience;
    @SerializedName("salary")
    @Expose
    private Integer salary;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("contact_email")
    @Expose
    private String contactEmail;
    @SerializedName("company_name")
    @Expose
    private String companyName;

    public List<Object> getCv() {
        return cv;
    }

    public void setCv(List<Object> cv) {
        this.cv = cv;
    }

    public List<Object> getChats() {
        return chats;
    }

    public void setChats(List<Object> chats) {
        this.chats = chats;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public JobCreaterId getJobCreaterId() {
        return jobCreaterId;
    }

    public void setJobCreaterId(JobCreaterId jobCreaterId) {
        this.jobCreaterId = jobCreaterId;
    }

    public String getJobExperience() {
        return jobExperience;
    }

    public void setJobExperience(String jobExperience) {
        this.jobExperience = jobExperience;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
