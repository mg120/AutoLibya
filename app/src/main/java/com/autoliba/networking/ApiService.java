package com.autoliba.networking;

import com.autoliba.activities.SendCodeModel;
import com.autoliba.model.AdDetailsModel;
import com.autoliba.model.AddNewCommentModel;
import com.autoliba.model.AddNewModel;
import com.autoliba.model.AutoNewsModel;
import com.autoliba.model.AutoShowsModel;
import com.autoliba.model.ChangePassModel;
import com.autoliba.model.ChatMessagesResponseModel;
import com.autoliba.model.DeleteAdModel;
import com.autoliba.model.FilterTypesModel;
import com.autoliba.model.ForgetPassSendMailModel;
import com.autoliba.model.GetChattersModel;
import com.autoliba.model.HomeModel;
import com.autoliba.model.PartBrandsModel;
import com.autoliba.model.ProfileDataModel;
import com.autoliba.model.SearchResultModel;
import com.autoliba.model.SendMessageResponseModel;
import com.autoliba.model.SettingInfoModel;
import com.autoliba.model.UpdateAdModel;
import com.autoliba.model.UserSignUpModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Ma7MouD on 11/1/2018.
 */

public interface ApiService {

    // ------------------ Login -------------------------
    @POST("/api/login")
    Call<UserSignUpModel> login(@Query("username") String username,
                                @Query("password") String password,
                                @Query("firebase_token") String firebase_token,
                                @Query("device_id") String device_id);


    // ------------------ Sign Up ----------------------
    @Multipart
    @POST("/api/userregister")
    Call<UserSignUpModel> userRegister(@Part("username") RequestBody username,
                                       @Query("firstname") String firstname,
                                       @Query("lastname") String lastname,
                                       @Query("email") String email,
                                       @Query("country") String country,
                                       @Query("area") String area,
                                       @Query("phone") String phone,
                                       @Query("address") String address,
                                       @Query("password") String password,
                                       @Query("confirmpass") String confirmpass,
                                       @Query("firebase_token") String firebase_token,
                                       @Query("device_id") String device_id,

                                       @Part MultipartBody.Part user_image);


    // ------------------- Auto SignUp ----------------------
    @Multipart
    @POST("/api/autoregister")
    Call<UserSignUpModel> autoRegister(@Part("username") RequestBody username,
                                       @Query("firstname") String firstname,
                                       @Query("lastname") String lastname,
                                       @Query("email") String email,
                                       @Query("country") String country,
                                       @Query("area") String area,
                                       @Query("phone") String phone,
                                       @Query("address") String address,
                                       @Query("password") String password,
                                       @Query("confirmpass") String confirmpass,
                                       @Query("firebase_token") String firebase_token,
                                       @Query("device_id") String device_id,

                                       @Part MultipartBody.Part user_image);


    // -------------------  Home Data -----------------------
    @POST("/api/getallads")
    Call<HomeModel> homeData(@Query("type") String type_id);


    // ------------------- Profile Data -----------------------
    @FormUrlEncoded
    @POST("/api/profile")
    Call<ProfileDataModel> getProfileData(@Field("user_id") String user_id);


    // ------------------- Add New --------------------------
    @Multipart
    @POST("/api/savead")
    Call<AddNewModel> add_New(@Query("title") String title,
                              @Query("type") String type,
                              @Query("used") String used,
                              @Query("brand") String brand,
                              @Query("country") String country,
                              @Query("city") String city,
                              @Query("modal") String modal,
                              @Query("price") String price,
                              @Query("phone") String phone,
                              @Query("email") String email,
                              @Query("ad_desc") String ad_desc,
                              @Query("neogation") String neogation,
                              @Query("phonecontact") String phonecontact,
                              @Part("user_id") RequestBody user_id,

                              @Part MultipartBody.Part user_image,
                              @Part List<MultipartBody.Part> item_imgs);


//    // -------------------- Update Add --------------------------
//    @Multipart
//    @POST("/api/updatead")
//    Call<UpdateAdModel> update_ad(@Query("title") String title,
//                                  @Query("type") String type,
//                                  @Query("used") String used,
//                                  @Query("brand") String brand,
//                                  @Query("country") String country,
//                                  @Query("city") String city,
//                                  @Query("modal") String modal,
//                                  @Query("price") String price,
//                                  @Query("phone") String phone,
//                                  @Query("email") String email,
//                                  @Query("ad_desc") String ad_desc,
//                                  @Query("neogation") String neogation,
//                                  @Query("phonecontact") String phonecontact,
//                                  @Query("user_id") String user_id,
//
//                                  @Part MultipartBody.Part user_image,
//                                  @Part List<MultipartBody.Part> item_imgs);


    // --------------------- Update Profile -------------------
    @Multipart
    @POST("/api/updateprofile")
    Call<UserSignUpModel> update_profile(@Part("user_id") RequestBody user_id,
                                         @Query("firstname") String firstname,
                                         @Query("lastname") String lastname,
                                         @Query("email") String email,
                                         @Query("phone") String phone,
                                         @Query("area") String area,
                                         @Query("country") String country,
                                         @Query("address") String address,

                                         @Part MultipartBody.Part user_image);


    // --------------------- Auto Shows ----------------------------
    @GET("/api/autoshows")
    Call<AutoShowsModel> autoShows();


    // --------------------- Auto News ----------------------------
    @GET("/api/autonews")
    Call<AutoNewsModel> autoNews();


    // --------------------- part Brands ----------------------------
    @GET("/api/partbrands")
    Call<PartBrandsModel> partBrands();


    // ----------------------  Ad Delails -----------------------------
    @POST("/api/showad")
    Call<AdDetailsModel> ad_itemDetails(@Query("ad_id") String ad_id);


    // ----------------------  Add Comment ---------------------------
    @POST("/api/addcomment")
    Call<AddNewCommentModel> add_comment(@Query("user_id") String user_id,
                                         @Query("ad_id") String ad_id,
                                         @Query("name") String name,
                                         @Query("email") String email,
                                         @Query("image") String image,
                                         @Query("comment") String comment);


    // ---------------------- Edit Ad -----------------------------
    @Multipart
    @POST("/api/updatead")
    Call<UpdateAdModel> Update_ad(@Part("user_id") RequestBody user_id,
                                  @Part("ad_id") RequestBody ad_id,
                                  @Part("title") RequestBody title,
                                  @Part("type") RequestBody type,
                                  @Part("used") RequestBody used,
                                  @Part("brand") RequestBody brand,
                                  @Part("country") RequestBody country,
                                  @Part("city") RequestBody city,
                                  @Part("modal") RequestBody modal,
                                  @Part("price") RequestBody price,
                                  @Part("phone") RequestBody phone,
                                  @Part("email") RequestBody email,
                                  @Part("ad_desc") RequestBody ad_desc,
                                  @Part("neogation") RequestBody neogation,
                                  @Part("phonecontact") RequestBody phonecontact,

                                  @Part MultipartBody.Part user_image,
                                  @Part List<MultipartBody.Part> item_imgs);


    // ---------------------- Delete Ad -----------------------------
    @POST("/api/delad")
    Call<DeleteAdModel> delete_ad(@Query("ad_id") String ad_id,
                                  @Query("user_id") String user_id);


    // ---------------------- Setting Info -----------------------------
    @GET("/api/settinginfo")
    Call<SettingInfoModel> getSettingInfo();


    // ----------------------- Get Chatters --------------------------
    @POST("/api/getchaters")
    Call<GetChattersModel> getChattersRoom(@Query("user_id") String user_id);


    // ----------------------- Send Message --------------------------
    @POST("/api/makechat")
    Call<SendMessageResponseModel> send_msg(@Query("sender_id") String sender_id,
                                            @Query("receiver_id") String receiver_id,
                                            @Query("message") String message);

    // ----------------------- Send Message --------------------------
    @POST("/api/getchat")
    Call<ChatMessagesResponseModel> chat_messages(@Query("sender_id") String sender_id,
                                                  @Query("receiver_id") String receiver_id);


    // ----------------------- Search --------------------------------
    @POST("/api/autosearch")
    Call<HomeModel> search(@Query("type") String type,
                           @Query("brand") String brand,
                           @Query("modal") String modal,
                           @Query("minprice") String minprice,
                           @Query("maxprice") String maxprice,
                           @Query("city") String city);


    // ------------------------ Forget Password -----------------------
    // ---------------- 1 - Send Email --------------------
    @POST("/api/forgetpassword")
    Call<ForgetPassSendMailModel> forgetPassword_mail(@Query("email") String email);

    // ---------------- 2 - Send Code --------------------
    @POST("/api/activcode")
    Call<SendCodeModel> send_Code(@Query("email") String email,
                                  @Query("forgetcode") String forgetcode);

    // ---------------- 3 - New Passsword --------------------
    @POST("/api/rechangepass")
    Call<UserSignUpModel> new_Pass(@Query("email") String email,
                                   @Query("new_password") String new_password,
                                   @Query("confirmpassword") String confirmpassword);



    // -------------------- Change Password ---------------------
    @POST("/api/changepassword")
    Call<ChangePassModel> change_Pass(@Query("user_id") String user_id,
                                      @Query("old_password") String old_password,
                                      @Query("new_password") String new_password,
                                      @Query("confirmpassword") String confirmpassword);



    // -------------------- Auto Show Ads ------------------------
    @POST("/api/myads")
    Call<HomeModel> getAutoShowAds(@Query("user_id") String user_id);



    // ------------------- All Filter Types -----------------------
    @GET("/api/getalltypes")
    Call<FilterTypesModel> allTypes();


}