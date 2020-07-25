package com.myproject.salesdemomvvm.network;

import com.myproject.salesdemomvvm.allclients.AllClientsModel;
import com.myproject.salesdemomvvm.home.SliderModel;
import com.myproject.salesdemomvvm.login.ExecutiveModel;
import com.myproject.salesdemomvvm.viewfollowups.ViewFollowupModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiHelper {

    @FormUrlEncoded
    @POST("login_executive.php")
    Call<List<ExecutiveModel>> loginExecutive(@Field("username") String username,
                                              @Field("password") String password,
                                              @Field("token") String token);

    @POST("get_slider.php")
    Call<ArrayList<SliderModel>> getSlider();

    @FormUrlEncoded
    @POST("add_client.php")
    Call<String> addClint(@Field("executive_username") String executive_username,
                          @Field("executive_name") String executive_name,
                          @Field("company_name") String company_name,
                          @Field("name") String name,
                          @Field("contact") String contact,
                          @Field("name1") String name1,
                          @Field("contact1") String contact1,
                          @Field("name2") String name2,
                          @Field("contact2") String contact2,
                          @Field("address") String address,
                          @Field("area") String area,
                          @Field("city") String city,
                          @Field("pincode") String pincode);

    @FormUrlEncoded
    @POST("get_my_all_clients.php")
    Call<ArrayList<AllClientsModel>> getAllClients(@Field("username") String username);

    @FormUrlEncoded
    @POST("search_my_all_clients.php")
    Call<ArrayList<AllClientsModel>> searchClients(@Field("username") String username,
                                                   @Field("search_entity") String search_entity);

    @FormUrlEncoded
    @POST("update_password_executive.php")
    Call<String> updatePassword(@Field("username") String username,
                                @Field("newpass") String newpass);


    @FormUrlEncoded
    @POST("add_followup.php")
    Call<String> addFollowup(@Field("ticketno") String ticketno,
                             @Field("clientname") String clientname,
                             @Field("description") String description,
                             @Field("status") String status,
                             @Field("next_followup") String next_followup,
                             @Field("upload_url") String upload_url,
                             @Field("by_username") String by_username,
                             @Field("by_name") String by_name);

    @FormUrlEncoded
    @POST("get_all_followup.php")
    Call<ArrayList<ViewFollowupModel>> getAllFollowup(@Field("ticketno") String ticketno);

    @Multipart
    @POST("upload_pdf.php")
    Call<String> uploadPdf(@Part MultipartBody.Part file,
                           @Part("file")RequestBody name);


}
