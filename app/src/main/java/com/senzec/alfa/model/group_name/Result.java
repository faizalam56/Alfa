package com.senzec.alfa.model.group_name;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by senzec on 12/10/17.
 */
public class Result {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("group_name")
    @Expose
    private String groupName;
    @SerializedName("jobs_detail")
    @Expose
    private JobsDetail jobsDetail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public JobsDetail getJobsDetail() {
        return jobsDetail;
    }

    public void setJobsDetail(JobsDetail jobsDetail) {
        this.jobsDetail = jobsDetail;
    }

}
