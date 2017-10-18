package com.senzec.alfa.model.profile_detail;

/**
 * Created by senzec on 11/10/17.
 */


 import com.google.gson.annotations.Expose;
 import com.google.gson.annotations.SerializedName;

public class ProfileDetailModel {

    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("response_message")
    @Expose
    private String responseMessage;
    @SerializedName("result")
    @Expose
    private Result result;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}


