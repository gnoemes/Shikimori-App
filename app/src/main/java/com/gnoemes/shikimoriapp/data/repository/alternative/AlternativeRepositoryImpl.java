package com.gnoemes.shikimoriapp.data.repository.alternative;

import com.gnoemes.shikimoriapp.data.local.db.HistoryDbSource;
import com.gnoemes.shikimoriapp.data.local.db.RateSyncDbSource;
import com.gnoemes.shikimoriapp.data.network.AlternativeApi;
import com.gnoemes.shikimoriapp.data.repository.alternative.converters.SeriesResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.alternative.converters.TranslationResponseConverter;
import com.gnoemes.shikimoriapp.entity.anime.series.data.network.TranslationListResponse;
import com.gnoemes.shikimoriapp.entity.anime.series.data.network.TranslationResponseData;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslation;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslationType;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Episode;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Series;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class AlternativeRepositoryImpl implements AlternativeRepository {

    private AlternativeApi api;
    private SeriesResponseConverter responseConverter;
    private TranslationResponseConverter translationResponseConverter;
    private HistoryDbSource historyDbSource;
    private RateSyncDbSource syncDbSource;

    @Inject
    public AlternativeRepositoryImpl(AlternativeApi api,
                                     SeriesResponseConverter seriesResponseConverter,
                                     TranslationResponseConverter translationResponseConverter,
                                     HistoryDbSource historyDbSource,
                                     RateSyncDbSource syncDbSource) {
        this.api = api;
        this.responseConverter = seriesResponseConverter;
        this.translationResponseConverter = translationResponseConverter;
        this.historyDbSource = historyDbSource;
        this.syncDbSource = syncDbSource;
    }

    /**
     * Get anime episodes
     * <p>
     * ~~Some Magic~~
     */
    @Override
    public Single<List<Episode>> getAnimeEpisodes(long animeId) {
        return api.getAnimeSeriesById(animeId)
                .map(responseConverter)
                .map(Series::getEpisodes)
                .flatMap(episodes -> syncDbSource.getRateEpisodes(animeId)
                        .flatMap(count -> Observable.fromIterable(episodes)
                                .take(count)
                                .flatMapCompletable(episode -> historyDbSource.episodeWatched(animeId, episode.getId()))
                                .toSingleDefault(episodes)))
                .flatMap(episodes -> Observable.fromIterable(episodes)
                        .flatMapSingle(episode -> historyDbSource.isEpisodeWatched(animeId, episode.getId())
                                .map(isWatched -> {
                                    episode.setWatched(isWatched);
                                    return episode;
                                }))
                        .toList());
    }

    @Override
    public Single<List<AlternativeTranslation>> getTranslations(AlternativeTranslationType type, long animeId, long episodeId) {
        return api.getEpisodeTranslations(type.getType(), episodeId)
                .map(TranslationListResponse::getTranslationResponses)
                .map(translationResponseConverter)
                .flatMap(translations -> Observable.fromIterable(translations)
                        .toSortedList((o1, o2) -> Long.compare(o2.getPriority(), o1.getPriority())));
    }

    @Override
    public Single<AlternativeTranslation> getTranslation(long translationId) {
        return api.getTranslation(translationId)
                .map(TranslationResponseData::getResponse)
                .map(response -> translationResponseConverter.convertResponse(response));
    }

    @Override
    public Completable setEpisodeWatched(long animeId, long episodeId) {
        return historyDbSource.episodeWatched(animeId, episodeId);
    }

    @Override
    public Single<Boolean> isEpisodeWatched(long animeId, long episodeId) {
        return historyDbSource.isEpisodeWatched(animeId, episodeId);
    }


    @Override
    public Completable clearHistory(long animeId) {
        return historyDbSource.clearHistory(animeId)
                .andThen(syncDbSource.clearHistory(animeId));
    }

}
