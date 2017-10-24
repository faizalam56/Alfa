package com.senzec.alfa.model.profile_detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobInfo {

    @SerializedName("joining_year")
    @Expose
    private Integer joiningYear;
    @SerializedName("job_designation")
    @Expose
    private String jobDesignation;
    @SerializedName("final_year")
    @Expose
    private Integer finalYear;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("_id")
    @Expose
    private String id;

    public Integer getJoiningYear() {
        return joiningYear;
    }

    public void setJoiningYear(Integer joiningYear) {
        this.joiningYear = joiningYear;
    }

    public String getJobDesignation() {
        return jobDesignation;
    }

    public void setJobDesignation(String jobDesignation) {
        this.jobDesignation = jobDesignation;
    }

    public Integer getFinalYear() {
        return finalYear;
    }

    public void setFinalYear(Integer finalYear) {
        this.finalYear = finalYear;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}