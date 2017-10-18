package com.senzec.alfa.model.socket_chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by senzec on 17/10/17.
 */
public class SenderId {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("user_name")
    @Expose
    private String userName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
