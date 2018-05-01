package com.gnoemes.shikimoriapp.domain.app;

import com.gnoemes.shikimoriapp.entity.app.domain.UserSettings;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface UserSettingsInteractor {

    Observable<UserSettings> getUserSettings();

    Completable saveUserSettings(UserSettings settings);
}
