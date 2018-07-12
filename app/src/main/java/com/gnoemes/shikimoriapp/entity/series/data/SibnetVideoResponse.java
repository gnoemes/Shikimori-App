package com.gnoemes.shikimoriapp.entity.series.data;

import com.google.gson.annotations.SerializedName;

public class SibnetVideoResponse {

    @SerializedName("src")
    private String url;
    @SerializedName("type")
    private String type;

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }
}
