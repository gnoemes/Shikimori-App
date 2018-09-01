package com.gnoemes.shikimoriapp.data.repository.app;

import com.gnoemes.shikimoriapp.entity.app.domain.UserSettings;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Deprecated
public interface UserSettingsRepository {

    Observable<UserSettings> getUserSettings();

    Completable saveUserSettings(UserSettings settings);
}
