package com.gnoemes.shikimoriapp.entity.anime.series.data.network;

import com.google.gson.annotations.SerializedName;

public class TranslationResponseData {

    @SerializedName("data")
    TranslationResponse response;

    public TranslationResponse getResponse() {
        return response;
    }
}
