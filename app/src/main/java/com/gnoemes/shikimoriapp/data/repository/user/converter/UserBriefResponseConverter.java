package com.gnoemes.shikimoriapp.data.repository.user.converter;

import com.gnoemes.shikimoriapp.entity.user.data.UserBriefResponse;
import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief;

import io.reactivex.functions.Function;

public interface UserBriefResponseConverter extends Function<UserBriefResponse, UserBrief> {

    UserBrief apply(UserBriefResponse response);
}
