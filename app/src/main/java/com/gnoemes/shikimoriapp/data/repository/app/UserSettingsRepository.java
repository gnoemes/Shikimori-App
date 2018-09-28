package com.gnoemes.shikimoriapp.data.repository.app;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.entity.app.domain.UserSettings;
import com.gnoemes.shikimoriapp.entity.app.domain.UserStatus;
import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface UserSettingsRepository {

    @Nullable
    UserBrief getUser();

    void setUser(UserBrief user);

    void clearUser();

    UserStatus getUserStatus();

    void setUserStatus(UserStatus status);

    Boolean isAutoStatusEnabled();

    void isAutoStatusEnabled(Boolean status);

    Boolean isRomadziNaming();

    void isRomadziNaming(Boolean enabled);

    Boolean isRememberType();

    void isRememberType(Boolean enabled);

    TranslationType getTranslationType();

    void setTranslationType(TranslationType type);

    Observable<UserSettings> getUserSettings();

    Completable saveUserSettings(UserSettings settings);

    Boolean isNotificationsEnabled();
}
