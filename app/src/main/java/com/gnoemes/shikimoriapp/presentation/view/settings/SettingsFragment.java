package com.gnoemes.shikimoriapp.presentation.view.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gnoemes.shikimoriapp.BuildConfig;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.entity.app.data.SettingsExtras;

import java.util.Arrays;
import java.util.List;

public class SettingsFragment extends PreferenceFragmentCompat {

    private final static String KEY = "IsRestartDialogShowing";
    private MaterialDialog restartDialog;
    private MaterialDialog translationTypeDialog;


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

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

        List<String> types = Arrays.asList(getResources().getStringArray(R.array.translations_type));
        Preference translationType = findPreference(getResources().getString(R.string.pref_translation_type));
        translationType.setOnPreferenceClickListener(preference -> {
            translationTypeDialog = new MaterialDialog.Builder(getContext())
                    .title(R.string.pref_translation_type_title)
                    .itemsCallback((dialog, itemView, position, text) -> {
                        dialog.dismiss();
                        translationType.setSummary(types.get(position));
                        getPreferenceManager().getSharedPreferences().edit().putString(SettingsExtras.TRANSLATION_TYPE, convertTypeValue(position)).apply();
                    })
                    .titleColorAttr(R.attr.colorText)
                    .contentColorAttr(R.attr.colorText)
                    .alwaysCallSingleChoiceCallback()
                    .backgroundColorAttr(R.attr.colorBackgroundWindow)
                    .items(types)
                    .canceledOnTouchOutside(true)
                    .itemsColorAttr(R.attr.colorText)
                    .build();

            translationTypeDialog.show();
            return false;
        });

        translationType.setSummary(convertTypeValue(getPreferenceManager().getSharedPreferences().getString(SettingsExtras.TRANSLATION_TYPE, null), types));

        findPreference(getResources().getString(R.string.pref_about_program)).setSummary(BuildConfig.VERSION_NAME);
        findPreference(getResources().getString(R.string.pref_dark_theme)).setOnPreferenceChangeListener((preference, newValue) -> {
            restartDialog.show();
            return true;
        });
        findPreference(getResources().getString(R.string.pref_auto_dark_theme)).setOnPreferenceChangeListener((preference, newValue) -> {
            restartDialog.show();
            return true;
        });
    }

    private String convertTypeValue(int position) {
        switch (position) {
            case 0:
                return TranslationType.VOICE_RU.getType();
            case 1:
                return TranslationType.SUB_RU.getType();
            case 2:
                return TranslationType.RAW.getType();
            case 3:
                return TranslationType.VOICE_EN.getType();
            case 4:
                return TranslationType.SUB_EN.getType();
        }
        return TranslationType.VOICE_RU.getType();
    }

    private String convertTypeValue(@Nullable String type, List<String> types) {
        if (TranslationType.VOICE_RU.isEqualType(type)) {
            return types.get(0);
        } else if (TranslationType.SUB_RU.isEqualType(type)) {
            return types.get(1);
        } else if (TranslationType.RAW.isEqualType(type)) {
            return types.get(2);
        } else if (TranslationType.VOICE_EN.isEqualType(type)) {
            return types.get(3);
        } else if (TranslationType.SUB_EN.isEqualType(type)) {
            return types.get(4);
        } else {
            return getResources().getString(R.string.pref_not_choosed_summary);
        }
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
