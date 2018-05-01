package com.gnoemes.shikimoriapp.data.repository.anime.series;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.local.db.EpisodeDbSource;
import com.gnoemes.shikimoriapp.data.local.db.HistoryDbSource;
import com.gnoemes.shikimoriapp.data.network.VideoApi;
import com.gnoemes.shikimoriapp.data.repository.anime.series.converters.SeriesResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.series.converters.TranslationResponseConverter;
import com.gnoemes.shikimoriapp.entity.anime.series.data.network.TranslationListResponse;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Episode;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Series;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Translation;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class SeriesRepositoryImpl implements SeriesRepository {

    private VideoApi api;
    private SeriesResponseConverter responseConverter;
    private TranslationResponseConverter translationResponseConverter;
    private EpisodeDbSource episodeDbSource;
    private HistoryDbSource historyDbSource;

    @Inject
    public SeriesRepositoryImpl(@NonNull VideoApi api,
                                @NonNull SeriesResponseConverter responseConverter,
                                @NonNull TranslationResponseConverter translationResponseConverter,
                                @NonNull EpisodeDbSource episodeDbSource,
                                @NonNull HistoryDbSource historyDbSource) {
        this.api = api;
        this.responseConverter = responseConverter;
        this.translationResponseConverter = translationResponseConverter;
        this.episodeDbSource = episodeDbSource;
        this.historyDbSource = historyDbSource;
    }

    @Override
    public Single<List<Episode>> getAnimeEpisodes(long animeId) {
        return api.getAnimeSeriesById(animeId)
                .map(responseConverter)
                .map(Series::getEpisodes)
                .flatMap(episodes -> Observable.fromIterable(episodes)
                        .flatMapSingle(episode -> historyDbSource.isEpisodeWatched(episode.getId())
                                .map(isWatched -> {
                                    episode.setWatched(isWatched);
                                    return episode;
                                }))
                        .toList())
                .flatMap(episodes -> episodeDbSource.saveEpisodes(episodes).toSingleDefault(episodes));
    }

    /**
     * Get sorted by rating translations
     */
    @Override
    public Single<List<Translation>> getTranslations(TranslationType type, long episodeId) {
        return api.getEpisodeTranslations(type.getType(), episodeId)
                .map(TranslationListResponse::getTranslationResponses)
                .map(translationResponseConverter)
                .flatMap(translations -> Observable.fromIterable(translations)
                        .toSortedList((o1, o2) -> Long.compare(o2.getPriority(), o1.getPriority())));
    }

    @Override
    public Completable setEpisodeWatched(long animeId, long episodeId) {
        return historyDbSource.episodeWatched(animeId, episodeId);
    }
}
