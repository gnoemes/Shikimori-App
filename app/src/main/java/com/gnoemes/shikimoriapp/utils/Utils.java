package com.gnoemes.shikimoriapp.utils;

import android.text.TextUtils;

import com.gnoemes.shikimoriapp.BuildConfig;

public class Utils {

    //KOTLIN WHERE ARE U

    public static String appendHostIfNeed(String url) {
        if (url.contains("http")) {
            return url;
        }
        return BuildConfig.ShikimoriBaseUrl.concat(url);
    }

    public static String firstUpperCase(String word) {
        if (TextUtils.isEmpty(word)) {
            return null;
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
}
