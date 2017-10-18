package com.senzec.alfa.model.profile_detail;

/**
 * Created by senzec on 11/10/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AcademicInfo {

    @SerializedName("final_year")
    @Expose
    private Integer finalYear;
    @SerializedName("joining_year")
    @Expose
    private Integer joiningYear;
    @SerializedName("college_name")
    @Expose
    private String collegeName;
    @SerializedName("qualification")
    @Expose
    private String qualification;
    @SerializedName("_id")
    @Expose
    private String id;

    public Integer getFinalYear() {
        return finalYear;
    }

    public void setFinalYear(Integer finalYear) {
        this.finalYear = finalYear;
    }

    public Integer getJoiningYear() {
        return joiningYear;
    }

    public void setJoiningYear(Integer joiningYear) {
        this.joiningYear = joiningYear;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
