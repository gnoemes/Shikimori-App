package com.gnoemes.shikimoriapp.data.repository.user;


import com.gnoemes.shikimoriapp.data.local.preferences.UserPreferenceSource;
import com.gnoemes.shikimoriapp.data.network.UserApi;
import com.gnoemes.shikimoriapp.data.repository.user.converter.UserBriefResponseConverter;
import com.gnoemes.shikimoriapp.entity.app.domain.UserSettings;
import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief;

import javax.inject.Inject;

import io.reactivex.Single;

public class UserRepositoryImpl implements UserRepository {

    private UserApi userApi;
    private UserPreferenceSource preferenceSource;
    private UserBriefResponseConverter userBriefResponseConverter;

    @Inject
    public UserRepositoryImpl(UserApi userApi,
                              UserBriefResponseConverter userBriefResponseConverter,
                              UserPreferenceSource preferenceSource) {
        this.userApi = userApi;
        this.userBriefResponseConverter = userBriefResponseConverter;
        this.preferenceSource = preferenceSource;
    }

    @Override
    public Single<UserBrief> getMyUserBrief() {
        return preferenceSource.getUserSettingsObservable()
                .map(UserSettings::getUserBrief)
                .onErrorResumeNext(throwable -> {
                    return userApi.getCurrentUserBriefInfo()
                            .map(userBriefResponseConverter)
                            .flatMapCompletable(userBrief -> preferenceSource.saveUserSettings(
                                    new UserSettings.Builder()
                                            .setUserBrief(userBrief)
                                            .build()))
                            .toObservable();
                })
                .firstOrError();
    }

    @Override
    public Single<UserBrief> getUserBriefInfo(long id) {
        return null;
    }
}
