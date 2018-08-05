package com.gnoemes.shikimoriapp.domain.history;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;

import java.util.List;

import io.reactivex.Single;

public interface HistoryInteractor {

    Single<List<Anime>> getLocalWatchedAnimes(int page, int limit, @Nullable String searchQuery);
}
