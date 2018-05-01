package com.gnoemes.shikimoriapp.data.local.db;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface HistoryDbSource {

    Completable episodeWatched(long animeId, long episodeId);

    Single<Boolean> isEpisodeWatched(long episodeId);
}
