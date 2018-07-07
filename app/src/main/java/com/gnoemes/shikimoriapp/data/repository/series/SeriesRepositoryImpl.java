package com.gnoemes.shikimoriapp.data.repository.series;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.local.db.EpisodeDbSource;
import com.gnoemes.shikimoriapp.data.local.db.HistoryDbSource;
import com.gnoemes.shikimoriapp.data.local.db.RateSyncDbSource;
import com.gnoemes.shikimoriapp.data.network.VideoApi;
import com.gnoemes.shikimoriapp.data.repository.series.converters.SeriesResponseConverter;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.PlayEpisode;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Translation;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.entity.series.domain.Series;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class SeriesRepositoryImpl implements SeriesRepository {

    private VideoApi api;
    private SeriesResponseConverter responseConverter;
    private EpisodeDbSource episodeDbSource;
    private HistoryDbSource historyDbSource;
    private RateSyncDbSource syncDbSource;

    @Inject
    public SeriesRepositoryImpl(@NonNull VideoApi api,
                                @NonNull SeriesResponseConverter responseConverter,
                                @NonNull EpisodeDbSource episodeDbSource,
                                @NonNull HistoryDbSource historyDbSource,
                                @NonNull RateSyncDbSource syncDbSource) {
        this.api = api;
        this.responseConverter = responseConverter;
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
    public Single<Series> getAnimeSeries(long animeId) {
        return api.getAnimeVideoInfo(animeId)
                .map(document -> responseConverter.apply(animeId, document))
                .flatMap(series -> episodeDbSource.saveEpisodes(series.getEpisodes()).toSingleDefault(series.getEpisodes())
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
                                .toList())
                        .map(episodes -> new Series(series.getErrorMessage(), series.isHasError(), episodes)));
    }

    /**
     * Get sorted by rating translations
     */
    @Override
    public Single<List<Translation>> getTranslations(TranslationType type, long episodeId) {
//        return api.getEpisodeTranslations(type.getType(), episodeId)
//                .map(TranslationListResponse::getTranslationResponses)
//                .map(translationResponseConverter)
//                .flatMap(translations -> Observable.fromIterable(translations)
//                        .toSortedList((o1, o2) -> Long.compare(o2.getPriority(), o1.getPriority())));
        return null;
    }

    @Override
    public Single<Translation> getTranslation(long translationId) {
//        return api.getTranslation(translationId)
//                .map(TranslationResponseData::getResponse)
//                .map(response -> translationResponseConverter.convertResponse(response));
        return null;
    }

    @Override
    public Single<List<PlayEpisode>> getTranslationVideoRawData(long translationId) {
//        return api.getPlayerHTMLPage(translationId)
//                .map(ResponseBody::string)
//                .map(playEpisodeConverter);
        return null;
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
