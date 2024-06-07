package com.masterofanalysis.api;

import androidx.annotation.Keep;

import com.masterofanalysis.DataModels.AppSettingsModel;
import com.masterofanalysis.DataModels.DataSecimler;
import com.masterofanalysis.DataModels.Datamatches;
import com.masterofanalysis.DataModels.GetResponseModel;
import com.masterofanalysis.DataModels.GetUserModel;
import com.masterofanalysis.DataModels.ProductsModel;
import com.masterofanalysis.DataModels.SetReferenceModel;
import com.masterofanalysis.DataModels.UserModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @Keep
    @FormUrlEncoded
    @POST("api.php")
    Call<UserModel> CreateUser(
            @Field("action") String action,
            @Field("api_key") String api_key,
            @Field("uid") String uid,
            @Field("name") String name,
            @Field("email") String email,
            @Field("country") String country,
            @Field("notification_id") String notification_id,
            @Field("device_id") String DeviceId,
            @Field("phone_language") String phone_language,
            @Field("androidVersion") String androidVersion,
            @Field("deviceName") String deviceName);
    @Keep
    @GET("api.php")
    Call<AppSettingsModel> GetAppSettings(
            @Query("action") String action,
            @Query("uid") String uid,
            @Query("timezone") String timezone,
            @Query("api_key") String api_key);
    @Keep
    @GET("api.php")
    Call<GetUserModel> GetUser(
            @Query("action") String action,
            @Query("uid") String uid,
            @Query("timezone") String timezone,
            @Query("api_key") String api_key);
    @Keep
    @GET("api.php")
    Call<Datamatches> GetUcretsiz(
            @Query("action") String action,
            @Query("uid") String uid,
            @Query("timezone") String timezone,
            @Query("api_key") String api_key);
    @Keep
    @GET("api.php")
    Call<SliderResponse> getSliderImages(
            @Query("action") String action,
            @Query("uid") String uid,
            @Query("api_key") String apiKey
    );
    @Keep
    @GET("api.php")
    Call<Datamatches> GetSeckin(
            @Query("action") String action,
            @Query("uid") String uid,
            @Query("timezone") String timezone,
            @Query("api_key") String api_key);
    @Keep
    @GET("api.php")
    Call<DataSecimler> GetSecimler(
            @Query("action") String action,
            @Query("uid") String uid,
            @Query("timezone") String timezone,
            @Query("api_key") String api_key);
    @Keep
    @GET("api.php")
    Call<Datamatches> GetSecimleric(
            @Query("action") String action,
            @Query("uid") String uid,
            @Query("timezone") String timezone,
            @Query("secimid") String secimid,
            @Query("api_key") String api_key);
    @Keep
    @GET("api.php")
    Call<ProductsModel> GetProducts(
            @Query("action") String action,
            @Query("uid") String uid,
            @Query("timezone") String timezone,
            @Query("api_key") String api_key);
    @Keep
    @FormUrlEncoded
    @POST("api.php")
    Call<SetReferenceModel> SetReferance(
            @Field("action") String action,
            @Field("api_key") String api_key,
            @Field("uid") String uid,
            @Field("reference_id") String name);


    @Keep
    @FormUrlEncoded
    @POST("api.php")
    Call<GetResponseModel> Set_Purchase(
            @Field("action") String action,
            @Field("api_key") String api_key,
            @Field("uid") String uid,
            @Field("productid") String productid,
            @Field("purchaseToken") String purchaseToken,
            @Field("productType") String productType,
            @Field("productprize") String productprize);

    @Keep
    @FormUrlEncoded
    @POST("api.php")
    Call<GetResponseModel> Delete_user(
            @Field("action") String action,
            @Field("api_key") String api_key,
            @Field("uid") String uid);

    @Keep
    @POST("api.php")
    Call<GetResponseModel> userHasSubs(@Body Map<String, Object> userData);
}
