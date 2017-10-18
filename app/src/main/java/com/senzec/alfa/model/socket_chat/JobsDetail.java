package com.senzec.alfa.model.socket_chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by senzec on 17/10/17.
 */
public class JobsDetail {

    @SerializedName("chats")
    @Expose
    private List<Chat> chats = null;

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

}
