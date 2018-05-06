package com.gnoemes.shikimoriapp.data.local.preferences;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.di.app.qualifiers.SettingsQualifier;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.PlayerType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.TranslationDubberSettings;
import com.gnoemes.shikimoriapp.entity.app.data.SettingsExtras;
import com.gnoemes.shikimoriapp.entity.app.domain.UserSettings;
import com.gnoemes.shikimoriapp.entity.app.domain.UserStatus;
import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class UserPreferenceSourceImpl implements UserPreferenceSource {

    private SharedPreferences sharedPreferences;
    private UserSettingsConverter converter;
    private BehaviorSubject<UserSettings> settingsPublishSubject;
    private Gson gson;

    @Inject
    public UserPreferenceSourceImpl(@SettingsQualifier SharedPreferences sharedPreferences,
                                    @NonNull UserSettingsConverter converter,
                                    @NonNull Gson gson) {
        this.sharedPreferences = sharedPreferences;
        this.converter = converter;
        this.gson = gson;
        settingsPublishSubject = BehaviorSubject.create();
        settingsPublishSubject.onNext(getUserSettings());
    }

    @Override
    public Observable<UserSettings> getUserSettingsObservable() {
        return settingsPublishSubject;
    }

    @Override
    public Completable saveUserSettings(@NonNull UserSettings userSettings) {
        return Completable.fromAction(() -> saveSettings(userSettings));
    }

    private void saveSettings(UserSettings userSettings) {
        saveUserStatus(userSettings.getStatus());
        saveUserBrief(userSettings.getUserBrief());
        saveWizardFlag(userSettings.getNeedShowWizard());
        saveTranslationType(userSettings.getTranslationType());
        saveTranslationDubberSettings(userSettings.getDubberSettings());
        savePlayerType(userSettings.getPlayerType());

        settingsPublishSubject.onNext(getUserSettings());
    }

    private UserSettings getUserSettings() {
        return new UserSettings(getUserStatus(),
                getUserBrief(),
                getWizardFlag(),
                getTranslationType(),
                getTranslationDubberSettings(),
                getPlayerType());
    }

    private void saveUserStatus(@Nullable UserStatus userStatus) {
        if (userStatus == null) {
            return;
        }
        getEditor().putString(SettingsExtras.USER_STATUS, userStatus.toString()).commit();
    }

    private UserStatus getUserStatus() {
        return converter.convertStatus(getPrefs().getString(SettingsExtras.USER_STATUS, "guest"));
    }

    private void saveUserBrief(@Nullable UserBrief userBrief) {
        if (userBrief == null) {
            return;
        }
        getEditor().putString(SettingsExtras.USER_BRIEF, gson.toJson(userBrief)).commit();
    }

    private UserBrief getUserBrief() {
        try {
            String json = getPrefs().getString(SettingsExtras.USER_BRIEF, "");
            return gson.fromJson(json, UserBrief.class);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    private void saveWizardFlag(@Nullable Boolean needShowWizard) {
        if (needShowWizard == null) {
            return;
        }
        getEditor().putBoolean(SettingsExtras.WATCH_WIZARD, needShowWizard).commit();
    }

    private boolean getWizardFlag() {
        return getPrefs().getBoolean(SettingsExtras.WATCH_WIZARD, true);
    }

    private void saveTranslationType(@Nullable TranslationType type) {
        if (type == null) {
            return;
        }
        getEditor().putString(SettingsExtras.TRANSLATION_TYPE, type.getType()).commit();
    }

    private TranslationType getTranslationType() {
        return converter.convertTranslationType(getPrefs().getString(SettingsExtras.TRANSLATION_TYPE, "voiceRu"));
    }

    private void saveTranslationDubberSettings(@Nullable TranslationDubberSettings settings) {
        if (settings == null) {
            return;
        }
        getEditor().putString(SettingsExtras.TRANSLATION_DUBBER_SETTINGS, settings.toString()).commit();
    }

    private TranslationDubberSettings getTranslationDubberSettings() {
        return converter.convertTranslationDubberSettings(getPrefs().getString(SettingsExtras.TRANSLATION_DUBBER_SETTINGS, "auto"));
    }

    private void savePlayerType(@Nullable PlayerType type) {
        if (type == null) {
            return;
        }
        getEditor().putString(SettingsExtras.PLAYER_TYPE, type.toString()).commit();
    }

    private PlayerType getPlayerType() {
        return converter.convertPlayerType(getPrefs().getString(SettingsExtras.PLAYER_TYPE, "embedded"));
    }

    private SharedPreferences getPrefs() {
        return sharedPreferences;
    }

    private SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }
}
