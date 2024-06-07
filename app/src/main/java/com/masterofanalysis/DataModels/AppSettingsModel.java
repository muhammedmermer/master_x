package com.masterofanalysis.DataModels;

import com.google.gson.annotations.SerializedName;


public class AppSettingsModel {
    @SerializedName("result")
    private String result;
    @SerializedName("message")
    private String message;
    @SerializedName("version_name")
    private String version_name;
    @SerializedName("version_control")
    private Boolean version_control;
    @SerializedName("maintenance_control")
    private Boolean maintenance_control;
    @SerializedName("maintenance_date")
    private String maintenance_minute;
    @SerializedName("youtube_link")
    private String youtube_link;
    @SerializedName("instagram_link")
    private String instagram_link;
    @SerializedName("whatsapp_link")
    private String whatsapp_link;
    @SerializedName("telegram_link")
    private String telegram_link;
    @SerializedName("website_link")
    private String website_link;
    @SerializedName("privacy_policy_link")
    private String Privacy_Policy_Url;
    @SerializedName("text_tr")
    private String text_tr;
    @SerializedName("text_en")
    private String text_en;
    @SerializedName("button_tr")
    private String button_tr;
    @SerializedName("button_en")
    private String button_en;
    @SerializedName("referral_link")
    private String referral_link;
    @SerializedName("now_time")
    private String now_time;

    @SerializedName("ban_status")
    private Boolean ban_status;

    public Boolean getBan_status() {
        return ban_status;
    }

    @SerializedName("reference_control")
    private Boolean reference_control;
    @SerializedName("reference_title_tr")
    private String reference_title_tr;
    @SerializedName("reference_title_en")
    private String reference_title_en;
    @SerializedName("reference_exp_tr")
    private String reference_exp_tr;
    @SerializedName("reference_exp_en")
    private String reference_exp_en;

    public Boolean getReference_control() {
        return reference_control;
    }

    public void setReference_control(Boolean reference_control) {
        this.reference_control = reference_control;
    }

    public String getReference_title_tr() {
        return reference_title_tr;
    }

    public void setReference_title_tr(String reference_title_tr) {
        this.reference_title_tr = reference_title_tr;
    }

    public String getReference_title_en() {
        return reference_title_en;
    }

    public void setReference_title_en(String reference_title_en) {
        this.reference_title_en = reference_title_en;
    }

    public String getReference_exp_tr() {
        return reference_exp_tr;
    }

    public void setReference_exp_tr(String reference_exp_tr) {
        this.reference_exp_tr = reference_exp_tr;
    }

    public String getReference_exp_en() {
        return reference_exp_en;
    }

    public void setReference_exp_en(String reference_exp_en) {
        this.reference_exp_en = reference_exp_en;
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

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public Boolean getVersion_control() {
        return version_control;
    }

    public void setVersion_control(Boolean version_control) {
        this.version_control = version_control;
    }

    public Boolean getMaintenance_control() {
        return maintenance_control;
    }

    public void setMaintenance_control(Boolean maintenance_control) {
        this.maintenance_control = maintenance_control;
    }

    public String getMaintenance_minute() {
        return maintenance_minute;
    }

    public void setMaintenance_minute(String maintenance_minute) {
        this.maintenance_minute = maintenance_minute;
    }

    public String getYoutube_link() {
        return youtube_link;
    }

    public void setYoutube_link(String youtube_link) {
        this.youtube_link = youtube_link;
    }

    public String getInstagram_link() {
        return instagram_link;
    }

    public void setInstagram_link(String instagram_link) {
        this.instagram_link = instagram_link;
    }

    public String getWhatsapp_link() {
        return whatsapp_link;
    }

    public void setWhatsapp_link(String whatsapp_link) {
        this.whatsapp_link = whatsapp_link;
    }

    public String getTelegram_link() {
        return telegram_link;
    }

    public void setTelegram_link(String telegram_link) {
        this.telegram_link = telegram_link;
    }

    public String getWebsite_link() {
        return website_link;
    }

    public void setWebsite_link(String website_link) {
        this.website_link = website_link;
    }

    public String getPrivacy_Policy_Url() {
        return Privacy_Policy_Url;
    }

    public void setPrivacy_Policy_Url(String privacy_Policy_Url) {
        Privacy_Policy_Url = privacy_Policy_Url;
    }
    public String getText_tr() {
        return text_tr;
    }

    public void setText_tr(String text_tr) {
        this.text_tr = text_tr;
    }

    public String getText_en() {
        return text_en;
    }

    public void setText_en(String text_en) {
        this.text_en = text_en;
    }

    public String getButton_tr() {
        return button_tr;
    }

    public void setButton_tr(String button_tr) {
        this.button_tr = button_tr;
    }

    public String getButton_en() {
        return button_en;
    }

    public void setButton_en(String button_en) {
        this.button_en = button_en;
    }

    public String getReferral_link() {
        return referral_link;
    }

    public void setReferral_link(String referral_link) {
        this.referral_link = referral_link;
    }

    public String getNow_time() {
        return now_time;
    }
}
