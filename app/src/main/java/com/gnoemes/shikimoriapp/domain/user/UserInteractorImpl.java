package com.gnoemes.shikimoriapp.domain.user;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.repository.user.UserRepository;
import com.gnoemes.shikimoriapp.entity.club.domain.Club;
import com.gnoemes.shikimoriapp.entity.user.domain.Favorites;
import com.gnoemes.shikimoriapp.entity.user.domain.UserBan;
import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief;
import com.gnoemes.shikimoriapp.entity.user.domain.UserHistory;
import com.gnoemes.shikimoriapp.entity.user.domain.UserProfile;
import com.gnoemes.shikimoriapp.utils.rx.CompletableErrorHandler;
import com.gnoemes.shikimoriapp.utils.rx.RxUtils;
import com.gnoemes.shikimoriapp.utils.rx.SingleErrorHandler;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class UserInteractorImpl implements UserInteractor {

    private UserRepository repository;
    private SingleErrorHandler singleErrorHandler;
    private CompletableErrorHandler completableErrorHandler;
    private RxUtils rxUtils;

    @Inject
    public UserInteractorImpl(@NonNull UserRepository repository,
                              @NonNull SingleErrorHandler singleErrorHandler,
                              @NonNull CompletableErrorHandler completableErrorHandler,
                              @NonNull RxUtils rxUtils) {
        this.repository = repository;
        this.singleErrorHandler = singleErrorHandler;
        this.completableErrorHandler = completableErrorHandler;
        this.rxUtils = rxUtils;
    }

    @Override
    public Single<UserBrief> getMyUser() {
        return repository.getMyUserBrief()
                .compose((SingleErrorHandler<UserBrief>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }

    @Override
    public Single<UserProfile> getUserProfile(long id) {
        return repository.getUserInfo(id)
                .flatMap(profile -> getMyUser()
                        .map(userBrief -> {
                            profile.setMe(profile.getId() == userBrief.getId());
                            return profile;
                        }))
                .compose((SingleErrorHandler<UserProfile>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }

    @Override
    public Single<Favorites> getFavorites(long id) {
        return repository.getUserFavorites(id)
                .compose((SingleErrorHandler<Favorites>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }

    @Override
    public Single<List<UserBrief>> getUserFriends(long id) {
        return repository.getUserFriends(id)
                .compose((SingleErrorHandler<List<UserBrief>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }

    @Override
    public Single<List<Club>> getUserClubs(long id) {
        return repository.getUserClubs(id)
                .compose((SingleErrorHandler<List<Club>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }

    @Override
    public Single<List<UserHistory>> getUserHistory(long id, int page, int limit) {
        return repository.getUserHistory(id, page, limit)
                .compose((SingleErrorHandler<List<UserHistory>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }

    @Override
    public Single<List<UserBan>> getUserBans(long id) {
        return repository.getUserBans(id)
                .compose((SingleErrorHandler<List<UserBan>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }

    @Override
    public Completable ignoreUser(long id) {
        return repository.ignoreUser(id)
                .compose(completableErrorHandler)
                .compose(rxUtils.applyCompleteSchedulers());
    }

    @Override
    public Completable unignoreUser(long id) {
        return repository.unignoreUser(id)
                .compose(completableErrorHandler)
                .compose(rxUtils.applyCompleteSchedulers());
    }

    @Override
    public Completable addToFriends(long id) {
        return repository.addToFriends(id)
                .compose(completableErrorHandler)
                .compose(rxUtils.applyCompleteSchedulers());
    }

    @Override
    public Completable deleteFriend(long id) {
        return repository.deleteFriend(id)
                .compose(completableErrorHandler)
                .compose(rxUtils.applyCompleteSchedulers());
    }

}
