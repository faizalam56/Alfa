package com.senzec.alfa.parse_api_adapter;

import com.senzec.alfa.adapter.GroupNameParameter;
import com.senzec.alfa.adapter.LoginParameter;
import com.senzec.alfa.adapter.SignupParameter;
import com.senzec.alfa.model.academic.AcademicJobModel;
import com.senzec.alfa.model.add_job.AddJobRequest;
import com.senzec.alfa.model.add_job.AddJobResponse;
import com.senzec.alfa.model.college_list.CollegeListModel;
import com.senzec.alfa.model.college_list.web_api.CollegeListWebApiModel;
import com.senzec.alfa.model.group_name.GroupNameWebApiModel;
import com.senzec.alfa.model.groupfeed.GroupFeedRequest;
import com.senzec.alfa.model.groupfeed.GroupFeedResponseModel;
import com.senzec.alfa.model.login.LoginModel;
import com.senzec.alfa.model.login.SocialLoginRequest;
import com.senzec.alfa.model.myprofile.MyProfileModel;
import com.senzec.alfa.model.profile_detail.ProfileDetailModel;
import com.senzec.alfa.model.signup.SignupModel;
import com.senzec.alfa.model.signup.SocialSignupRequest;
import com.senzec.alfa.model.signup.SocialSignupResponse;
import com.senzec.alfa.model.socket_chat.ChatHistoryModel;
import com.senzec.alfa.model.socket_chat.parameterized_request.ChatPostRequest;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {


    @POST("user/user_registration")
    Call<SignupModel> signupResponse(@Body SignupParameter signupParameter);

    @POST("user/login")
    Call<LoginModel> loginResponse(@Body LoginParameter loginParameter);

    @Headers({"Content-Type: application/json", "Cache-Control: max-age=640000"})
    @POST("user/add_additional_information")
    Call<AcademicJobModel> getUser(@Body String body);

    @Multipart
    @POST("media/uploadFile/{user_id}")
    Call<MyProfileModel> uploadImageFile(@Part MultipartBody.Part file, @Part("imgFile") RequestBody name, @Path("user_id") String user_id);

    @Multipart
    @POST("media/uploadFile/{user_id}")
    Call<MyProfileModel> uploadCVFile(@Part MultipartBody.Part file, @Part("cvFile") RequestBody name, @Path("user_id") String user_id);

    @GET("user/profile/{user_id}")
    Call<ProfileDetailModel> profileDetailResponse(@Path("user_id") String user_id);

    @GET("user/show_groups/{user_id}")
    Call<CollegeListModel> collegeListResponse(@Path("user_id") String user_id);
    /*@GET("user/show_groups/59d8714e458ac81a370d29b9")
    Call<CollegeListModel> collegeListResponse();*/
    @POST("user/group_feed")
    Call<GroupFeedResponseModel> findGroupFeed(@Body GroupFeedRequest groupFeedRequest);

    @Headers({"Content-Type: application/json", "Cache-Control: max-age=640000"})
    @POST("user/join_groups")
    Call<CollegeListWebApiModel> setUserJoinedGroup(@Body String body);

    @POST("user/all_job_post")
    Call<GroupNameWebApiModel> getAllJobPost(@Body GroupNameParameter groupNameParameter);

    @POST("user/job_post")
    Call<AddJobResponse> addJob(@Body AddJobRequest addJobRequest);

    @POST("user/user_registration")
    Call<SocialSignupResponse> socialSignUp(@Body SocialSignupRequest socialSignupRequest);

    @POST("user/social_login")
    Call<SocialSignupResponse> socialLogin(@Body SocialLoginRequest socialLoginRequest);

    @POST("chat/subGroup_history")
    Call<ChatHistoryModel> chatPreviousHistory(@Body ChatPostRequest chatPostRequest);

}
