package com.gnoemes.shikimoriapp.presentation.presenter.fav.converter;

import com.gnoemes.shikimoriapp.entity.user.domain.UserProfile;

import java.util.List;

import io.reactivex.functions.Function;

public interface RateStatusCountConverter extends Function<UserProfile, List<String>> {
}
