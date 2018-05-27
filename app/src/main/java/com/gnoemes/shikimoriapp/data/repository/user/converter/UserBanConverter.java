package com.gnoemes.shikimoriapp.data.repository.user.converter;

import com.gnoemes.shikimoriapp.entity.user.data.UserBanResponse;
import com.gnoemes.shikimoriapp.entity.user.domain.UserBan;

import java.util.List;

import io.reactivex.functions.Function;

public interface UserBanConverter extends Function<List<UserBanResponse>, List<UserBan>> {
}
