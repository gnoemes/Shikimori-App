package com.gnoemes.shikimoriapp.presentation.view.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gnoemes.shikimoriapp.BuildConfig;
import com.gnoemes.shikimoriapp.R;
import com.google.firebase.analytics.FirebaseAnalytics;

public class SettingsFragment extends PreferenceFragmentCompat {

    private final static String KEY = "IsRestartDialogShowing";
    private MaterialDialog restartDialog;
    private FirebaseAnalytics analytics;

    private boolean isDarkTheme;


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        analytics = FirebaseAnalytics.getInstance(getContext());
        restartDialog = new MaterialDialog.Builder(getContext())
                .title(R.string.attention)
                .content(R.string.need_restart)
                .titleColorAttr(R.attr.colorText)
                .contentColorAttr(R.attr.colorText)
                .backgroundColorAttr(R.attr.colorBackgroundWindow)
                .autoDismiss(false)
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .positiveText(R.string.restart)
                .positiveColorAttr(R.attr.colorAction)
                .onPositive((dialog, which) -> {
                    updateActivity();
                    dialog.dismiss();
                })
                .build();

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY)) {
            if (savedInstanceState.getBoolean(KEY)) {
                restartDialog.show();
            }
        }

        findPreference(getResources().getString(R.string.pref_about_program)).setSummary(BuildConfig.VERSION_NAME);
        findPreference(getResources().getString(R.string.pref_dark_theme)).setOnPreferenceChangeListener((preference, newValue) -> {
            restartDialog.show();
            isDarkTheme = (boolean) newValue;
            return true;
        });
        findPreference(getResources().getString(R.string.pref_auto_dark_theme)).setOnPreferenceChangeListener((preference, newValue) -> {
            restartDialog.show();
            return true;
        });

        analytics.setUserProperty("theme", String.valueOf(isDarkTheme));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View root = getView();
        if (root != null) {
            root.findViewById(android.support.v7.preference.R.id.list).setPadding(0, 0, 0, 0);
        }
        setDividerHeight(0);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY, restartDialog.isShowing());
    }

    private void updateActivity() {
        Intent i = getContext().getPackageManager().getLaunchIntentForPackage(getContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
