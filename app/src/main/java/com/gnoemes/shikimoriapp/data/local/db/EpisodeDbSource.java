package com.gnoemes.shikimoriapp.data.local.db;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.Episode;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface EpisodeDbSource {
    Completable saveEpisodes(List<Episode> episodes);

    Single<List<Episode>> getEpisodes(long animeId);

    Completable setEpisodeWatched(long episodeId);
}
