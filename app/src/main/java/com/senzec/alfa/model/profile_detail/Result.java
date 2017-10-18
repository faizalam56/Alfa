package com.senzec.alfa.model.profile_detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by senzec on 11/10/17.
 */

public class Result {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("email_id")
    @Expose
    private String emailId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("phone_number")
    @Expose
    private Integer phoneNumber;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("user_groups")
    @Expose
    private List<Object> userGroups = null;
    @SerializedName("notification_status")
    @Expose
    private Integer notificationStatus;
    @SerializedName("verify_referal_code")
    @Expose
    private Boolean verifyReferalCode;
    @SerializedName("otp_authenticate")
    @Expose
    private Boolean otpAuthenticate;
    @SerializedName("job_info")
    @Expose
    private List<JobInfo> jobInfo = null;
    @SerializedName("academic_info")
    @Expose
    private List<AcademicInfo> academicInfo = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public List<Object> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(List<Object> userGroups) {
        this.userGroups = userGroups;
    }

    public Integer getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(Integer notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public Boolean getVerifyReferalCode() {
        return verifyReferalCode;
    }

    public void setVerifyReferalCode(Boolean verifyReferalCode) {
        this.verifyReferalCode = verifyReferalCode;
    }

    public Boolean getOtpAuthenticate() {
        return otpAuthenticate;
    }

    public void setOtpAuthenticate(Boolean otpAuthenticate) {
        this.otpAuthenticate = otpAuthenticate;
    }

    public List<JobInfo> getJobInfo() {
        return jobInfo;
    }

    public void setJobInfo(List<JobInfo> jobInfo) {
        this.jobInfo = jobInfo;
    }

    public List<AcademicInfo> getAcademicInfo() {
        return academicInfo;
    }

    public void setAcademicInfo(List<AcademicInfo> academicInfo) {
        this.academicInfo = academicInfo;
    }

}