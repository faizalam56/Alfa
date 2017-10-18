package com.senzec.alfa.model.socket_chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by senzec on 17/10/17.
 */
public class Chat {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("receiverId")
    @Expose
    private ReceiverId receiverId;
    @SerializedName("senderId")
    @Expose
    private SenderId senderId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ReceiverId getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(ReceiverId receiverId) {
        this.receiverId = receiverId;
    }

    public SenderId getSenderId() {
        return senderId;
    }

    public void setSenderId(SenderId senderId) {
        this.senderId = senderId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
