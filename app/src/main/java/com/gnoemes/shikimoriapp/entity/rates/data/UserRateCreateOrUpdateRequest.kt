package com.gnoemes.shikimoriapp.entity.rates.data

import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserRateCreateOrUpdateRequest(
        @field:SerializedName("user_rate") val userRate: UserRateResponse
)