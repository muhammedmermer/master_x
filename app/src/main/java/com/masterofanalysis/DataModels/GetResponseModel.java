package com.masterofanalysis.DataModels;

import com.google.gson.annotations.SerializedName;


public class GetResponseModel {
    @SerializedName("result")
    private String result;
    @SerializedName("message")
    private String message;

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
}
