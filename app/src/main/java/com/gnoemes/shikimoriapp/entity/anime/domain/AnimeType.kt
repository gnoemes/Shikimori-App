package com.gnoemes.shikimoriapp.entity.anime.domain

import com.google.gson.annotations.SerializedName

enum class AnimeType {
    @SerializedName("tv")
    TV,
    @SerializedName("movie")
    MOVIE,
    @SerializedName("ova")
    OVA,
    @SerializedName("ona")
    ONA,
    @SerializedName("special")
    SPECIAL,
    @SerializedName("music")
    MUSIC,
    @SerializedName("tv_13")
    TV_13,
    @SerializedName("tv_24")
    TV_24,
    @SerializedName("tv_48")
    TV_48,
    @SerializedName("")
    NONE;

    override fun toString(): String {
        return name.toLowerCase()
    }
}