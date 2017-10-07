package com.senzec.alfa.parse_api_adapter;

import com.senzec.alfa.adapter.LoginParameter;
import com.senzec.alfa.adapter.SignupParameter;
import com.senzec.alfa.model.academic.AcademicJobModel;
import com.senzec.alfa.model.login.LoginModel;
import com.senzec.alfa.model.signup.SignupModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {


    @POST("user/user_registration")
    Call<SignupModel> signupResponse(@Body SignupParameter signupParameter);

    @POST("user/login")
    Call<LoginModel> loginResponse(@Body LoginParameter loginParameter);

    @Headers({"Content-Type: application/json", "Cache-Control: max-age=640000"})
    @POST("user/add_additional_information")
    Call<AcademicJobModel> getUser(@Body String body);

}
