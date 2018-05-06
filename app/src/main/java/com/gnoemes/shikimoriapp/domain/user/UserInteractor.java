package com.gnoemes.shikimoriapp.domain.user;

import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief;

import io.reactivex.Single;

public interface UserInteractor {

    Single<UserBrief> getMyUser();
}
