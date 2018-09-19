package com.gnoemes.shikimoriapp.data.repository.user;


import com.gnoemes.shikimoriapp.data.local.preferences.UserPreferenceSource;
import com.gnoemes.shikimoriapp.data.network.UserApi;
import com.gnoemes.shikimoriapp.data.repository.club.ClubResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.user.converter.FavoritesResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.user.converter.MessageResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.user.converter.UserBanConverter;
import com.gnoemes.shikimoriapp.data.repository.user.converter.UserBriefResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.user.converter.UserHistoryResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.user.converter.UserProfileResponseConverter;
import com.gnoemes.shikimoriapp.entity.app.data.AppConfig;
import com.gnoemes.shikimoriapp.entity.app.domain.UserSettings;
import com.gnoemes.shikimoriapp.entity.club.domain.Club;
import com.gnoemes.shikimoriapp.entity.user.domain.Favorites;
import com.gnoemes.shikimoriapp.entity.user.domain.Message;
import com.gnoemes.shikimoriapp.entity.user.domain.MessageType;
import com.gnoemes.shikimoriapp.entity.user.domain.UserBan;
import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief;
import com.gnoemes.shikimoriapp.entity.user.domain.UserHistory;
import com.gnoemes.shikimoriapp.entity.user.domain.UserProfile;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class UserRepositoryImpl implements UserRepository {

    private UserApi userApi;
    private UserPreferenceSource preferenceSource;
    private UserBriefResponseConverter userBriefResponseConverter;
    private UserProfileResponseConverter profileResponseConverter;
    private UserHistoryResponseConverter historyConverter;
    private UserBanConverter banConverter;
    private FavoritesResponseConverter favoritesResponseConverter;
    private ClubResponseConverter clubResponseConverter;
    private MessageResponseConverter messageResponseConverter;

    @Inject
    public UserRepositoryImpl(UserApi userApi,
                              UserBriefResponseConverter userBriefResponseConverter,
                              UserPreferenceSource preferenceSource,
                              UserProfileResponseConverter profileResponseConverter,
                              FavoritesResponseConverter favoritesResponseConverter,
                              ClubResponseConverter clubResponseConverter,
                              UserBanConverter banConverter,
                              UserHistoryResponseConverter historyConverter,
                              MessageResponseConverter messageResponseConverter
    ) {
        this.userApi = userApi;
        this.userBriefResponseConverter = userBriefResponseConverter;
        this.preferenceSource = preferenceSource;
        this.profileResponseConverter = profileResponseConverter;
        this.favoritesResponseConverter = favoritesResponseConverter;
        this.clubResponseConverter = clubResponseConverter;
        this.banConverter = banConverter;
        this.historyConverter = historyConverter;
        this.messageResponseConverter = messageResponseConverter;
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
        return userApi.getUserBriefInfo(id)
                .map(userBriefResponseConverter);
    }

    @Override
    public Single<List<Message>> getUserMessages(MessageType type) {
        return getMyUserBrief()
                .flatMap(userBrief -> userApi.getUserMessages(userBrief.getId(), type.name().toLowerCase(), AppConfig.DEFAULT_LIMIT))
//                .flatMap(messageResponses -> Observable.fromIterable(messageResponses)
//                        .map(messageResponse -> {
//                            messageResponse.setDateCreated(DateTime.now());
//                            return messageResponse;
//                        })
//                        .toList())
                .map(messageResponses -> messageResponseConverter.convertList(messageResponses));
    }



    @Override
    public Single<UserProfile> getUserInfo(long id) {
        return userApi.getUserProfile(id)
                .map(profileResponseConverter);
    }

    @Override
    public Single<List<UserBrief>> getUserFriends(long id) {
        return userApi.getUserFriends(id)
                .map(response -> userBriefResponseConverter.convertList(response));
    }

    @Override
    public Single<Favorites> getUserFavorites(long id) {
        return userApi.getUserFavourites(id)
                .map(favoritesResponseConverter);
    }

    @Override
    public Single<List<Club>> getUserClubs(long id) {
        return userApi.getUserClubs(id)
                .map(clubResponseConverter);
    }

    @Override
    public Single<List<UserBan>> getUserBans(long id) {
        return userApi.getUserBans(id)
                .map(banConverter);
    }

    @Override
    public Single<List<UserHistory>> getUserHistory(long id, int page, int limit) {
        return userApi.getUserHistory(id, page, limit)
                .map(historyConverter);
    }

    @Override
    public Completable ignoreUser(long id) {
        return userApi.ignoreUser(id);
    }

    @Override
    public Completable unignoreUser(long id) {
        return userApi.unignoreUser(id);
    }

    @Override
    public Completable addToFriends(long id) {
        return userApi.addToFriends(id);
    }

    @Override
    public Completable deleteFriend(long id) {
        return userApi.deleteFriend(id);
    }

}
