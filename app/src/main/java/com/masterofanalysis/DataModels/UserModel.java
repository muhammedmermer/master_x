package com.masterofanalysis.DataModels;

import com.google.gson.annotations.SerializedName;


public class UserModel {
    @SerializedName("result")
    private String result;
    @SerializedName("message")
    private String message;
    @SerializedName("uid")
    private String uid;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("country")
    private String country;
    @SerializedName("create_date")
    private String create_date;
    @SerializedName("last_login")
    private String last_login;
    @SerializedName("notification_id")
    private String notification_id;
    @SerializedName("vıp_date")
    private String vıp_date;
    @SerializedName("credit_count")
    private int credit_count;
    @SerializedName("device_id")
    private String DeviceId;
    @SerializedName("phone_language")
    private String phone_language;
    @SerializedName("referans_id")
    private String referans_id;
    @SerializedName("androidVersion")
    private String androidVersion;
    @SerializedName("deviceName")
    private String deviceName;
    @SerializedName("ban_status")
    private String ban_status;

    @SerializedName("now_time")
    private String now_time;

    public String getNow_time() {
        return now_time;
    }

    public void setNow_time(String now_time) {
        this.now_time = now_time;
    }
    public String getAndroidVersion() {
        return androidVersion;
    }
    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }
    public String getDeviceName() {
        return deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public String getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(String notification_id) {
        this.notification_id = notification_id;
    }

    public String getVıp_date() {
        return vıp_date;
    }

    public void setVıp_date(String vıp_date) {
        this.vıp_date = vıp_date;
    }

    public int getCredit_count() {
        return credit_count;
    }

    public void setCredit_count(int credit_count) {
        this.credit_count = credit_count;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String DeviceId) {
        this.DeviceId = DeviceId;
    }

    public String getPhone_language() {
        return phone_language;
    }

    public void setPhone_language(String phone_language) {
        this.phone_language = phone_language;
    }

    public String getReferans_id() {
        return referans_id;
    }

    public void setReferans_id(String referans_id) {
        this.referans_id = referans_id;
    }

    public String getBan_status() {
        return ban_status;
    }

    public void setBan_status(String ban_status) {
        this.ban_status = ban_status;
    }


    /*
    public boolean isname() {
        return name == null;
    }

     */


    public UserModel() {
    }

}
