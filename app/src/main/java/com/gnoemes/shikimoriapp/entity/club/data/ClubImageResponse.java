package com.gnoemes.shikimoriapp.entity.club.data;

import com.google.gson.annotations.SerializedName;

public class ClubImageResponse {
    @SerializedName("original")
    private String original;
    @SerializedName("main")
    private String main;
    @SerializedName("x96")
    private String x96;
    @SerializedName("x73")
    private String x73;
    @SerializedName("x48")
    private String x48;

    public String getOriginal() {
        return original;
    }

    public String getMain() {
        return main;
    }

    public String getX96() {
        return x96;
    }

    public String getX73() {
        return x73;
    }

    public String getX48() {
        return x48;
    }
}
