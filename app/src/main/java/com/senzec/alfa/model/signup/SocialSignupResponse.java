package com.senzec.alfa.model.signup;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ravi on 16/10/17.
 */

public class SocialSignupResponse implements Serializable {
    public String response_code;
    public String response_message;
    public String token;
    public Result result;

    public class Result implements Serializable{
        public String __v;
        public String type;
        public String social_type;
        public String social_id;
        public String password;
        public String user_name;
        public String phone_number;
        public String _id;
        public String created_at;
        public String is_active;
        public List<String> user_group;
        public String notification_status;
        public String verify_referal_code;
        public String otp_authenticate;
        public List<String> job_info;
        public List<String> academic_info;
    }
}
