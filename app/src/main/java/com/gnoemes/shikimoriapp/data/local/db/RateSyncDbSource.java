package com.gnoemes.shikimoriapp.data.local.db;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface RateSyncDbSource {

    Completable saveRateEpisodes(long rateId, long animeId, int episodes);

    Single<Integer> getRateEpisodes(long animeId);

    Completable clearHistory(long animeId);
}
