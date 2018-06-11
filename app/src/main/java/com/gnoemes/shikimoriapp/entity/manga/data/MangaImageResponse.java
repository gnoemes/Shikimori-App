package com.gnoemes.shikimoriapp.entity.manga.data;

import com.google.gson.annotations.SerializedName;

public class MangaImageResponse {
    @SerializedName("original")
    private String imageOriginalUrl;
    @SerializedName("preview")
    private String imagePreviewUrl;
    @SerializedName("x96")
    private String imageX96Url;
    @SerializedName("x48")
    private String imageX48Url;

    public String getImageOriginalUrl() {
        return imageOriginalUrl;
    }

    public String getImagePreviewUrl() {
        return imagePreviewUrl;
    }

    public String getImageX96Url() {
        return imageX96Url;
    }

    public String getImageX48Url() {
        return imageX48Url;
    }
}
