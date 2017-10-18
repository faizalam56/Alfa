package com.senzec.alfa.to_json.collegelist;

import java.util.List;

/**
 * Created by senzec on 12/10/17.
 */

public class CollegeWithMenu {

    List<String> group_id;
    String user_id;

    public CollegeWithMenu(List<String> group_id, String user_id){

        this.group_id = group_id;
        this.user_id = user_id;
    }

}