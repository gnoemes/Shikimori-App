package com.gnoemes.shikimoriapp.entity.rates.data

import com.google.gson.annotations.SerializedName

data class UserRateCreateOrUpdateRequest(
        @field:SerializedName("user_rate") val userRate: UserRateResponse
)