package com.gnoemes.shikimoriapp.entity.user.data;

import com.google.gson.annotations.SerializedName;

public class UserImageResponse {

    @SerializedName("x160")
    private String x160Url;

    @SerializedName("x148")
    private String x148Url;

    @SerializedName("x80")
    private String x80Url;

    @SerializedName("x64")
    private String x64Url;

    @SerializedName("x48")
    private String x48Url;

    @SerializedName("x32")
    private String x32Url;

    @SerializedName("x16")
    private String x16Url;

    public String getX160Url() {
        return x160Url;
    }

    public String getX148Url() {
        return x148Url;
    }

    public String getX80Url() {
        return x80Url;
    }

    public String getX64Url() {
        return x64Url;
    }

    public String getX48Url() {
        return x48Url;
    }

    public String getX32Url() {
        return x32Url;
    }

    public String getX16Url() {
        return x16Url;
    }
}
