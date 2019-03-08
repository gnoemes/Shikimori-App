package com.gnoemes.shikimoriapp.entity.common.data

import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ImageResponse(
        @field:SerializedName("original") val original: String?,
        @field:SerializedName("preview") val preview: String?,
        @field:SerializedName("x96") val x96: String?,
        @field:SerializedName("x48") val x48: String?
)