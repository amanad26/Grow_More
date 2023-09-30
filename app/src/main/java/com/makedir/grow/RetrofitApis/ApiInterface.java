package com.makedir.grow.RetrofitApis;


import static com.makedir.grow.RetrofitApis.BaseUrls.AddUserBank;
import static com.makedir.grow.RetrofitApis.BaseUrls.AddWithdrawRequest;
import static com.makedir.grow.RetrofitApis.BaseUrls.addOrder;
import static com.makedir.grow.RetrofitApis.BaseUrls.buyProduct;
import static com.makedir.grow.RetrofitApis.BaseUrls.chatUser;
import static com.makedir.grow.RetrofitApis.BaseUrls.checkIn;
import static com.makedir.grow.RetrofitApis.BaseUrls.forgortPassword;
import static com.makedir.grow.RetrofitApis.BaseUrls.getAllUsersProducts;
import static com.makedir.grow.RetrofitApis.BaseUrls.getAppdata;
import static com.makedir.grow.RetrofitApis.BaseUrls.getMyinvitedUser;
import static com.makedir.grow.RetrofitApis.BaseUrls.getPlans;
import static com.makedir.grow.RetrofitApis.BaseUrls.getSliders;
import static com.makedir.grow.RetrofitApis.BaseUrls.getSubscription;
import static com.makedir.grow.RetrofitApis.BaseUrls.getUpiIds;
import static com.makedir.grow.RetrofitApis.BaseUrls.getUserBank;
import static com.makedir.grow.RetrofitApis.BaseUrls.getUserProfile;
import static com.makedir.grow.RetrofitApis.BaseUrls.getUsersListAdmin;
import static com.makedir.grow.RetrofitApis.BaseUrls.getWithdrawRequestUser;
import static com.makedir.grow.RetrofitApis.BaseUrls.rechargeBalance;
import static com.makedir.grow.RetrofitApis.BaseUrls.updatePriceUser;
import static com.makedir.grow.RetrofitApis.BaseUrls.updateUserProfile;
import static com.makedir.grow.RetrofitApis.BaseUrls.uploadImage;

import com.makedir.grow.Model.AboutusModel;
import com.makedir.grow.Model.BankModel;
import com.makedir.grow.Model.CheckAppData;
import com.makedir.grow.Model.FaqModel;
import com.makedir.grow.Model.ForgotPasswordModel;
import com.makedir.grow.Model.LiveSliderModel;
import com.makedir.grow.Model.LoginModel;
import com.makedir.grow.Model.LogoutModel;
import com.makedir.grow.Model.MyProductModel;
import com.makedir.grow.Model.PaymentResponseModel;
import com.makedir.grow.Model.PlanListModel;
import com.makedir.grow.Model.PravicyPolicyModel;
import com.makedir.grow.Model.SignUpModel;
import com.makedir.grow.Model.SubscriptionModel;
import com.makedir.grow.Model.TermsModel;
import com.makedir.grow.Model.UpdateModel;
import com.makedir.grow.Model.UpiIdsModel;
import com.makedir.grow.Model.UserListModel;
import com.makedir.grow.Model.WithdrawModel;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("signupWithEmail")
    Call<SignUpModel> signupWithEmail(@Field("email") String email,
                                      @Field("password") String password,
                                      @Field("fcm") String fcm,
                                      @Field("invite_code") String invite_code);


    @FormUrlEncoded
    @POST("signupWithPhone")
    Call<SignUpModel> signupWithPhone(@Field("mobile") String mobile,
                                      @Field("password") String password,
                                      @Field("fcm") String fcm,
                                      @Field("with_password") String with_password,
                                      @Field("name") String name,
                                      @Field("invite_code") String invite_code
    );

    @FormUrlEncoded
    @POST("loginWithEmail")
    Call<LoginModel> loginWithEmail(@Field("email") String email,
                                    @Field("password") String password,
                                    @Field("fcm") String fcm);


    @FormUrlEncoded
    @POST("loginWithPhone")
    Call<LoginModel> loginWithMobile(@Field("mobile") String mobile,
                                     @Field("password") String password,
                                     @Field("fcm") String fcm);

    @POST("getTerms ")
    Call<TermsModel> getTerms();

    @POST("getAboutUs")
    Call<AboutusModel> getAboutUs();


    @POST("get_faq")
    Call<FaqModel> get_faq();


    @POST("getPrivacyPolicy ")
    Call<PravicyPolicyModel> getPrivacyPolicy();


    @GET(getSubscription)
    Call<SubscriptionModel> getSubscription();


    @FormUrlEncoded
    @POST(addOrder)
    Call<LogoutModel> addOrder(
            @Field("user_id") String user_Id,
            @Field("total_price") String total_price,
            @Field("start_date") String start_date,
            @Field("end_date") String end_date,
            @Field("subscription_id") String subscription_id
    );


    @FormUrlEncoded
    @POST(getUserProfile)
    Call<LoginModel> userProfile(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(updateUserProfile)
    Call<LogoutModel> updateProfileImage(@Field("user_id") String user_id, @Field("image") String image);

    @Multipart
    @POST(uploadImage)
    Call<LogoutModel> uploadImage(@Part MultipartBody.Part filePart);

    @FormUrlEncoded
    @POST(updateUserProfile)
    Call<LoginModel> updateUserProfile(
            @Field("user_id") String user_id,
            @Field("name") String name,
            @Field("isPrime") String isPrime,
            @Field("mobile") String mobile,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST(forgortPassword)
    Call<ForgotPasswordModel> forgotPassword(@Field("email") String email);

    @GET(getUsersListAdmin)
    Call<UserListModel> getAllUsersList();


    @FormUrlEncoded
    @POST(updatePriceUser)
    Call<LogoutModel> updatePrice(
            @Field("user_id") String user_id,
            @Field("price") String price
    );


    @POST("payment_token")
    Call<PaymentResponseModel> paymentStart(@Header("Authorization") String header, @Body Map<String, Object> map);


    @GET(getSliders)
    Call<LiveSliderModel> getAllSliders();

    @GET(getPlans)
    Call<PlanListModel> getAllPlans();

    @GET(getAppdata)
    Call<CheckAppData> checkAppData();

    @FormUrlEncoded
    @POST(rechargeBalance)
    Call<UpdateModel> rechargeBalance(
            @Field("user_id") String user_id,
            @Field("user_name") String user_name,
            @Field("user_mobile") String user_mobile,
            @Field("payment_id") String payment_id,
            @Field("amount") String amount,
            @Field("current_amount") String current_amount,
            @Field("isRecharge") String isRecharge,
            @Field("date") String date
    );


    @FormUrlEncoded
    @POST(buyProduct)
    Call<UpdateModel> buyProduct(
            @Field("user_id") String user_id,
            @Field("user_name") String user_name,
            @Field("user_mobile") String user_mobile,
            @Field("amount") String amount,
            @Field("start_date") String start_date,
            @Field("end_date") String end_date,
            @Field("dayPrice") String dayPrice,
            @Field("reaminingWalltet") String reaminingWalltet,
            @Field("plan_name") String plan_name
    );

    @FormUrlEncoded
    @POST(AddWithdrawRequest)
    Call<UpdateModel> addWithdrawRequest(
            @Field("user_id") String user_id,
            @Field("user_name") String name,
            @Field("user_mobile") String user_mobile,
            @Field("amount") String amount,
            @Field("amount_to_pay") String amount_pay,
            @Field("date") String date,
            @Field("remaining") String remaining,
            @Field("remaining_balance") String remaining_balance
    );

    @FormUrlEncoded
    @POST(AddUserBank)
    Call<UpdateModel> addBankDetails(
            @Field("user_id") String user_id,
            @Field("bank_name") String bank_name,
            @Field("account_holder_name") String account_holder_name,
            @Field("ifsc_code") String ifsc_code,
            @Field("account_number") String account_number
    );

    @FormUrlEncoded
    @POST(getWithdrawRequestUser)
    Call<WithdrawModel> getWithdrawRequest(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(getAllUsersProducts)
    Call<MyProductModel> getProducts(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(getUserBank)
    Call<BankModel> getBank(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(checkIn)
    Call<UpdateModel> checkIn(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(getMyinvitedUser)
    Call<UserListModel> getMyInvitedFriends(
            @Field("invite_id") String invite_id
    );

    @FormUrlEncoded
    @POST(chatUser)
    Call<UpdateModel> addChat(
            @Field("sender_id") String sender_id,
            @Field("reiever_id") String reiever_id,
            @Field("meessage") String meessage,
            @Field("date") String date
    );

    @GET(getUpiIds)
    Call<UpiIdsModel> getUpiIds();

}
