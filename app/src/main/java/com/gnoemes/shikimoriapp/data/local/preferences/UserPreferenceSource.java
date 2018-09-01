package com.gnoemes.shikimoriapp.data.local.preferences;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.entity.app.domain.UserSettings;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Deprecated
public interface UserPreferenceSource {

    Observable<UserSettings> getUserSettingsObservable();

    Completable saveUserSettings(@NonNull UserSettings userSettings);
}
