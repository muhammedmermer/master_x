package com.masterofanalysis.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SliderResponse {
    @SerializedName("result")
    private String result;

    @SerializedName("slider_image_urls")
    private List<String> sliderImageUrls;

    // Getter ve Setter metodları
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<String> getSliderImageUrls() {
        return sliderImageUrls;
    }

    public void setSliderImageUrls(List<String> sliderImageUrls) {
        this.sliderImageUrls = sliderImageUrls;
    }
}
