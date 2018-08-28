package com.gnoemes.shikimoriapp.entity.common.data

import com.google.gson.annotations.SerializedName

data class ImageResponse(
        @field:SerializedName("original") val imageOriginalUrl: String,
        @field:SerializedName("preview") val imagePreviewUrl: String,
        @field:SerializedName("x96") val imageX96Url: String,
        @field:SerializedName("x48") val imageX48Url: String
)