package com.gnoemes.shikimoriapp.data.repository.user.converter;

import com.gnoemes.shikimoriapp.entity.user.data.UserProfileResponse;
import com.gnoemes.shikimoriapp.entity.user.domain.UserProfile;

import io.reactivex.functions.Function;

public interface UserProfileResponseConverter extends Function<UserProfileResponse, UserProfile> {
}
