package com.senzec.alfa.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by senzec on 7/10/17.
 */

public class Result {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("email_id")
    @Expose
    private String emailId;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("phone_number")
    @Expose
    private Integer phoneNumber;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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

}
