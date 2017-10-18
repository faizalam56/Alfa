package com.senzec.alfa.model.socket_chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by senzec on 17/10/17.
 */
public class Result {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("jobs_detail")
    @Expose
    private List<JobsDetail> jobsDetail = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<JobsDetail> getJobsDetail() {
        return jobsDetail;
    }

    public void setJobsDetail(List<JobsDetail> jobsDetail) {
        this.jobsDetail = jobsDetail;
    }

}
