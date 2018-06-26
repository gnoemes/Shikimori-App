package com.gnoemes.shikimoriapp.data.repository.anime.series;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.local.db.EpisodeDbSource;
import com.gnoemes.shikimoriapp.data.local.db.HistoryDbSource;
import com.gnoemes.shikimoriapp.data.local.db.RateSyncDbSource;
import com.gnoemes.shikimoriapp.data.network.VideoApi;
import com.gnoemes.shikimoriapp.data.repository.anime.series.converters.PlayEpisodeConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.series.converters.SeriesResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.series.converters.TranslationResponseConverter;
import com.gnoemes.shikimoriapp.entity.anime.series.data.network.TranslationListResponse;
import com.gnoemes.shikimoriapp.entity.anime.series.data.network.TranslationResponseData;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Episode;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.PlayEpisode;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Series;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Translation;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.ResponseBody;

public class SeriesRepositoryImpl implements SeriesRepository {

    private VideoApi api;
    private SeriesResponseConverter responseConverter;
    private TranslationResponseConverter translationResponseConverter;
    private PlayEpisodeConverter playEpisodeConverter;
    private EpisodeDbSource episodeDbSource;
    private HistoryDbSource historyDbSource;
    private RateSyncDbSource syncDbSource;

    @Inject
    public SeriesRepositoryImpl(@NonNull VideoApi api,
                                @NonNull SeriesResponseConverter responseConverter,
                                @NonNull TranslationResponseConverter translationResponseConverter,
                                @NonNull PlayEpisodeConverter playEpisodeConverter,
                                @NonNull EpisodeDbSource episodeDbSource,
                                @NonNull HistoryDbSource historyDbSource,
                                @NonNull RateSyncDbSource syncDbSource) {
        this.api = api;
        this.responseConverter = responseConverter;
        this.translationResponseConverter = translationResponseConverter;
        this.playEpisodeConverter = playEpisodeConverter;
        this.episodeDbSource = episodeDbSource;
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
                .flatMap(episodes -> episodeDbSource.saveEpisodes(episodes).toSingleDefault(episodes))
                .flatMap(episodes -> syncDbSource.getRateEpisodes(animeId)
                        .flatMap(count -> Observable.fromIterable(episodes)
                                .take(count)
                                .flatMapCompletable(episode -> historyDbSource.episodeWatched(animeId, episode.getId()))
                                .toSingleDefault(episodes)))
                .flatMap(episodes -> Observable.fromIterable(episodes)
                        .flatMapSingle(episode -> historyDbSource.isEpisodeWatched(episode.getId())
                                .map(isWatched -> {
                                    episode.setWatched(isWatched);
                                    return episode;
                                }))
                        .toList());
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
    public Single<Translation> getTranslation(long translationId) {
        return api.getTranslation(translationId)
                .map(TranslationResponseData::getResponse)
                .map(response -> translationResponseConverter.convertResponse(response));
    }

    @Override
    public Single<List<PlayEpisode>> getTranslationVideoRawData(long translationId) {
        return api.getPlayerHTMLPage(translationId)
                .map(ResponseBody::string)
                .map(playEpisodeConverter);
    }

    @Override
    public Completable setEpisodeWatched(long animeId, long episodeId) {
        return historyDbSource.episodeWatched(animeId, episodeId);
    }

    @Override
    public Single<Boolean> isEpisodeWatched(long episodeId) {
        return historyDbSource.isEpisodeWatched(episodeId);
    }


    @Override
    public Completable clearHistory(long animeId) {
        return historyDbSource.clearHistory(animeId)
                .andThen(syncDbSource.clearHistory(animeId));
    }
}
