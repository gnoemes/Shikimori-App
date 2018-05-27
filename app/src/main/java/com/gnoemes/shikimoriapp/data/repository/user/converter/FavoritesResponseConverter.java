package com.gnoemes.shikimoriapp.data.repository.user.converter;

import com.gnoemes.shikimoriapp.entity.user.data.FavoritesResponse;
import com.gnoemes.shikimoriapp.entity.user.domain.Favorites;

import io.reactivex.functions.Function;

public interface FavoritesResponseConverter extends Function<FavoritesResponse, Favorites> {
}
