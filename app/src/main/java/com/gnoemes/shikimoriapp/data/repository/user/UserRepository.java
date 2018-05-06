package com.gnoemes.shikimoriapp.data.repository.user;

import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief;

import io.reactivex.Single;

public interface UserRepository {

    Single<UserBrief> getMyUserBrief();

    Single<UserBrief> getUserBriefInfo(long id);
}
