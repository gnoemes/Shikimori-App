package com.gnoemes.shikimoriapp.presentation.view.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gnoemes.shikimoriapp.BuildConfig;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.data.SettingsExtras;
import com.google.firebase.analytics.FirebaseAnalytics;

public class SettingsFragment extends PreferenceFragmentCompat {

    private final static String KEY = "IsRestartDialogShowing";
    private MaterialDialog restartDialog;
    private FirebaseAnalytics analytics;

    private boolean isDarkTheme;
    private String naming;


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        analytics = FirebaseAnalytics.getInstance(getContext());
//        restartDialog = new MaterialDialog.Builder(getContext())
//                .title(R.string.attention)
//                .content(R.string.need_restart)
//                .titleColorAttr(R.attr.colorText)
//                .contentColorAttr(R.attr.colorText)
//                .backgroundColorAttr(R.attr.colorBackgroundWindow)
//                .autoDismiss(false)
//                .cancelable(false)
//                .canceledOnTouchOutside(false)
//                .positiveText(R.string.restart)
//                .positiveColorAttr(R.attr.colorAction)
//                .onPositive((dialog, which) -> {
//                    updateActivity();
//                    dialog.dismiss();
//                })
//                .build();

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

        String namingSummary = getPreferenceManager().getSharedPreferences().getBoolean(SettingsExtras.IS_ROMADZI_NAMING, false)
                ? getContext().getString(R.string.on_romadzi) : getContext().getString(R.string.on_russian);
        findPreference(getResources().getString(R.string.pref_naming)).setSummary(namingSummary);
//        findPreference(getResources().getString(R.string.pref_naming)).setOnPreferenceClickListener(preference -> {
//            new MaterialDialog.Builder(getContext())
//                    .title(R.string.titles_naming)
//                    .itemsCallback((dialog, itemView, position, text) -> {
//                        String summary = position == 1 ? getContext().getString(R.string.on_romadzi) : getContext().getString(R.string.on_russian);
//                        preference.setSummary(summary);
//                        getPreferenceManager().getSharedPreferences().edit().putBoolean(SettingsExtras.IS_ROMADZI_NAMING, position == 1).apply();
//                    })
//                    .titleColorAttr(R.attr.colorText)
//                    .contentColorAttr(R.attr.colorText)
//                    .alwaysCallSingleChoiceCallback()
//                    .backgroundColorAttr(R.attr.colorBackgroundWindow)
//                    .items(R.array.naming_types)
//                    .canceledOnTouchOutside(true)
//                    .itemsColorAttr(R.attr.colorText)
//                    .autoDismiss(true)
//                    .build()
//                    .show();
//            return false;
//        });

        analytics.setUserProperty("theme", String.valueOf(isDarkTheme));

        naming = getPreferenceManager().getSharedPreferences().getBoolean(SettingsExtras.IS_ROMADZI_NAMING, false) ? "romadzi" : "russian";
        analytics.setUserProperty("titles_naming", naming);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View root = getView();
        if (root != null) {
            root.findViewById(android.support.v7.preference.R.id.list_item).setPadding(0, 0, 0, 0);
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
