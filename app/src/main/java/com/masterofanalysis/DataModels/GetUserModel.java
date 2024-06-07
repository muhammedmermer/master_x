package com.masterofanalysis.DataModels;

import com.google.gson.annotations.SerializedName;


public class GetUserModel {
    @SerializedName("result")
    private String result;
    @SerializedName("message")
    private String message;
    @SerializedName("vip_date")
    private String vip_date;
    @SerializedName("credit_count")
    private int credit_count;
    @SerializedName("referans_id")
    private String referans_id;
    @SerializedName("referans_id_two")
    private String referans_id_two;

    public String getReferans_id_two() {
        return referans_id_two;
    }

    public void setReferans_id_two(String referans_id_two) {
        this.referans_id_two = referans_id_two;
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

    public String getVip_date() {
        return vip_date;
    }

    public void setVip_date(String vip_date) {
        this.vip_date = vip_date;
    }

    public int getCredit_count() {
        return credit_count;
    }

    public void setCredit_count(int credit_count) {
        this.credit_count = credit_count;
    }

    public String getReferans_id() {
        return referans_id;
    }

    public void setReferans_id(String referans_id) {
        this.referans_id = referans_id;
    }

    public GetUserModel() {
    }

}
