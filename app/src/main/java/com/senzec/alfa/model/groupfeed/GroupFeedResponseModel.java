package com.senzec.alfa.model.groupfeed;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ravi on 12/10/17.
 */

public class GroupFeedResponseModel implements Serializable{
    public String response_code;
    public String response_message;
    public List<Result> result;


    public class  Result implements Serializable{
        public String _id;
        public String group_name;
        public String type;
        /*public String updated_at;
        public String created_at;
        public String subscribers;
        public List<JobsDetails> jobs_detail;
        public List<String> batch;
        public List<Tags> tags;
        public String is_active;
        public String __v;*/
        public String jobCounts;
    }

   /* public class JobsDetails implements Serializable{

    }
    public class Tags implements Serializable{

    }*/
}
