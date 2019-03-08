package com.gnoemes.shikimoriapp.data.repository.user;

import com.gnoemes.shikimoriapp.entity.club.domain.Club;
import com.gnoemes.shikimoriapp.entity.user.domain.Favorites;
import com.gnoemes.shikimoriapp.entity.user.domain.Message;
import com.gnoemes.shikimoriapp.entity.user.domain.MessageType;
import com.gnoemes.shikimoriapp.entity.user.domain.UserBan;
import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief;
import com.gnoemes.shikimoriapp.entity.user.domain.UserHistory;
import com.gnoemes.shikimoriapp.entity.user.domain.UserProfile;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface UserRepository {

    Single<UserBrief> getMyUserBrief();

    Single<UserBrief> getUserBriefInfo(long id);

    Single<List<Message>> getUserMessages(MessageType type);

    Single<UserProfile> getUserInfo(long id);

    Single<List<UserBrief>> getUserFriends(long id);

    Single<Favorites> getUserFavorites(long id);

    Single<List<Club>> getUserClubs(long id);

    Single<List<UserBan>> getUserBans(long id);

    Single<List<UserHistory>> getUserHistory(long id, int page, int limit);

    Completable ignoreUser(long id);

    Completable unignoreUser(long id);

    Completable addToFriends(long id);

    Completable deleteFriend(long id);
}
