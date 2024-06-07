package com.masterofanalysis.api;

import java.util.List;

public class SliderResponse {
    private String result;
    private String message;
    private List<String> slider_image_urls;

    public String getResult() {
        return result;
    }
    public String getMessage() {
        return message;
    }

    public List<String> getUrls() {
        return slider_image_urls;
    }


}
