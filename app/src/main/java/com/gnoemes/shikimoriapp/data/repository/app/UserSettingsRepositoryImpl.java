package com.gnoemes.shikimoriapp.data.repository.app;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.local.preferences.UserPreferenceSource;
import com.gnoemes.shikimoriapp.entity.app.domain.UserSettings;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class UserSettingsRepositoryImpl implements UserSettingsRepository {

    private UserPreferenceSource source;

    @Inject
    public UserSettingsRepositoryImpl(@NonNull UserPreferenceSource source) {
        this.source = source;
    }

    @Override
    public Observable<UserSettings> getUserSettings() {
        return source.getUserSettingsObservable();
    }

    @Override
    public Completable saveUserSettings(UserSettings settings) {
        return source.saveUserSettings(settings);
    }
}
