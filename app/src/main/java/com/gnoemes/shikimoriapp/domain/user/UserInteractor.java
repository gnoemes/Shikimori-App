package com.gnoemes.shikimoriapp.domain.user;

import com.gnoemes.shikimoriapp.entity.club.domain.Club;
import com.gnoemes.shikimoriapp.entity.user.domain.Favorites;
import com.gnoemes.shikimoriapp.entity.user.domain.UserBan;
import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief;
import com.gnoemes.shikimoriapp.entity.user.domain.UserHistory;
import com.gnoemes.shikimoriapp.entity.user.domain.UserProfile;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface UserInteractor {

    Single<UserBrief> getMyUser();

    Single<UserProfile> getUserProfile(long id);

    Single<Favorites> getFavorites(long id);

    Single<List<UserBrief>> getUserFriends(long id);

    Single<List<Club>> getUserClubs(long id);

    Single<List<UserHistory>> getUserHistory(long id, int page, int limit);

    Single<List<UserBan>> getUserBans(long id);

    Completable ignoreUser(long id);

    Completable unignoreUser(long id);

    Completable addToFriends(long id);

    Completable deleteFriend(long id);
}
