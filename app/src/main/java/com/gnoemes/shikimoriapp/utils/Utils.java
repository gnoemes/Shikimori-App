package com.gnoemes.shikimoriapp.utils;

import com.gnoemes.shikimoriapp.BuildConfig;

public class Utils {

    //KOTLIN WHERE ARE U

    public static String appendHostIfNeed(String url) {
        if (url.contains("http")) {
            return url;
        }
        return BuildConfig.ShikimoriBaseUrl.concat(url);
    }
}
