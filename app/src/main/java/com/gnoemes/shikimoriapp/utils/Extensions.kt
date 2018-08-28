package com.gnoemes.shikimoriapp.utils

import android.text.TextUtils
import com.gnoemes.shikimoriapp.BuildConfig

fun String.appendHostIfNeed(): String {
    return if (this.contains("http")) this else BuildConfig.ShikimoriBaseUrl + this
}

fun String.firstUpperCase(): String? {
    return if (TextUtils.isEmpty(this)) null else this.substring(0, 1).toUpperCase() + this.substring(1)
}