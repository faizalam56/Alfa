package com.senzec.alfa.model.add_job;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ravi on 13/10/17.
 */

public class AddJobResponse implements Serializable{
    public String response_code;
    public String response_message;
    public List<Result> result;

    public class Result implements Serializable{
        public String n;
        public String nModified;
        public String ok;
    }
}
