package com.gnoemes.shikimoriapp.data.repository.alternative;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslation;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslationType;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Episode;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface AlternativeRepository {


    Single<List<Episode>> getAnimeEpisodes(long animeId);

    Single<List<AlternativeTranslation>> getTranslations(AlternativeTranslationType type, long animeId, long episodeId);

    Single<AlternativeTranslation> getTranslation(long translationId);

    Completable setEpisodeWatched(long animeId, long episodeId);

    Single<Boolean> isEpisodeWatched(long animeId, long episodeId);

    Completable clearHistory(long animeId);
}
