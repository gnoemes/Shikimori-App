package com.gnoemes.shikimoriapp.data.local.db;

import com.gnoemes.shikimoriapp.entity.anime.series.data.db.HistoryDao;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface HistoryDbSource {

    Completable episodeWatched(long animeId, long episodeId);

    Single<Boolean> isEpisodeWatched(long animeId, long episodeId);

    Completable clearHistory(long animeId);

    Single<Integer> getWatchedEpisodesCount(long animeId);

    Single<List<HistoryDao>> getWatchedAnimes();
}
