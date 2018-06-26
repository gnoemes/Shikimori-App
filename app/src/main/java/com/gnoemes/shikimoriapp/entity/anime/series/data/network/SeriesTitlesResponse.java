package com.gnoemes.shikimoriapp.entity.anime.series.data.network;

import com.google.gson.annotations.SerializedName;

public class SeriesTitlesResponse {
    @SerializedName("en")
    private String en;
    @SerializedName("ru")
    private String ru;
    @SerializedName("romaji")
    private String romaji;
    @SerializedName("ja")
    private String ja;
    @SerializedName("short")
    private String shortTitle;

    public String getEn() {
        return en;
    }

    public String getRu() {
        return ru;
    }

    public String getRomaji() {
        return romaji;
    }

    public String getJa() {
        return ja;
    }

    public String getShortTitle() {
        return shortTitle;
    }
}
