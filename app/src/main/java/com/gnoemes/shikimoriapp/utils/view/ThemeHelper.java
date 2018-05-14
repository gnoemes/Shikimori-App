package com.gnoemes.shikimoriapp.utils.view;

import android.content.Context;
import android.support.v7.preference.PreferenceManager;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.data.AppConfig;

import org.joda.time.DateTime;

public class ThemeHelper {

    public static int applyTheme(Context contextThemeWrapper) {
        if (ThemeHelper.isDarkThemeEnabled(contextThemeWrapper)) {
//            contextThemeWrapper.setTheme(R.style.AppThemeDark);
            return R.style.AppThemeDark;
        } else if (ThemeHelper.isAutoDarkThemeEnabled(contextThemeWrapper)) {
            return R.style.AppThemeDark;
        }
        return R.style.AppThemeLight;
    }

    private static boolean isAutoDarkThemeEnabled(Context context) {
        int hours = DateTime.now().getHourOfDay();
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(context.getString(R.string.pref_auto_dark_theme),
                        false)
                && (hours > AppConfig.AUTO_THEME_START_HOURS
                || hours < AppConfig.AUTO_THEME_END_HOURS);
    }


    private static boolean isDarkThemeEnabled(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(context.getString(R.string.pref_dark_theme),
                        false);
    }
}
