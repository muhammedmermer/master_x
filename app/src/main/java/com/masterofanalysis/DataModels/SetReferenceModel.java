package com.masterofanalysis.DataModels;

import com.google.gson.annotations.SerializedName;


public class SetReferenceModel {
    @SerializedName("result")
    private String result;
    @SerializedName("message")
    private String message;
    @SerializedName("reference_two")
    private String reference_two;

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

    public String getReference_two() {
        return reference_two;
    }

    public void setReference_two(String reference_two) {
        this.reference_two = reference_two;
    }

    public SetReferenceModel() {
    }

}
