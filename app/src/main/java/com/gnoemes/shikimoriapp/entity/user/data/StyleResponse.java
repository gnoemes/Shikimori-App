package com.gnoemes.shikimoriapp.entity.user.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StyleResponse {
    @Expose
    @SerializedName("compiled_css")
    private String css;

    public String getCss() {
        return css;
    }
}
