package com.gnoemes.shikimoriapp.utils;

import android.content.Context;
import android.support.v7.preference.PreferenceManager;

import com.gnoemes.shikimoriapp.entity.app.data.SettingsExtras;

public class PrefUtils {

    public static boolean isRomandziNaming(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(SettingsExtras.IS_ROMADZI_NAMING,
                        false);
    }
}
