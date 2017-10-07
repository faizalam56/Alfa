package com.senzec.alfa.model.groupfeed;

/**
 * Created by senzec on 7/10/17.
 */

public class GroupfeedModel {

    public GroupfeedModel(String groupName, int sizeOfpost) {
        this.groupName = groupName;
        this.sizeOfpost = sizeOfpost;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getSizeOfpost() {
        return sizeOfpost;
    }

    public void setSizeOfpost(int sizeOfpost) {
        this.sizeOfpost = sizeOfpost;
    }

    String groupName;
    int sizeOfpost;
}
