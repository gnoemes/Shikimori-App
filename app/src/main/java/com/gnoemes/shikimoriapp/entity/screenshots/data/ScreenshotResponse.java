package com.gnoemes.shikimoriapp.entity.screenshots.data;

import com.google.gson.annotations.SerializedName;

public class ScreenshotResponse {

    @SerializedName("original")
    private String original;
    @SerializedName("preview")
    private String preview;

    public String getOriginal() {
        return original;
    }

    public String getPreview() {
        return preview;
    }
}
