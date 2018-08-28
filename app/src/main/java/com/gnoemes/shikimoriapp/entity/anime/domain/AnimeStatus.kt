package com.gnoemes.shikimoriapp.entity.anime.domain

import com.google.gson.annotations.SerializedName

enum class AnimeStatus {
    @SerializedName("anons")
    ANONS,
    @SerializedName("ongoing")
    ONGOING,
    @SerializedName("released")
    RELEASED,
    @SerializedName("")
    NONE
}