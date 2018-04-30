package com.gnoemes.shikimoriapp.entity.anime.series.data.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TranslationListResponse {

    @SerializedName("data")
    private List<TranslationResponse> translationResponses;

    public List<TranslationResponse> getTranslationResponses() {
        return translationResponses;
    }
}
