package com.gnoemes.shikimoriapp.data.repository.series;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.local.db.EpisodeDbSource;
import com.gnoemes.shikimoriapp.data.local.db.HistoryDbSource;
import com.gnoemes.shikimoriapp.data.local.db.RateSyncDbSource;
import com.gnoemes.shikimoriapp.data.network.VideoApi;
import com.gnoemes.shikimoriapp.data.repository.series.converters.PlayVideoResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.series.converters.SeriesResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.series.converters.TranslationResponseConverter;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Translation;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.entity.series.domain.PlayVideo;
import com.gnoemes.shikimoriapp.entity.series.domain.Series;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class SeriesRepositoryImpl implements SeriesRepository {

    private VideoApi api;
    private SeriesResponseConverter responseConverter;
    private TranslationResponseConverter translationResponseConverter;
    private PlayVideoResponseConverter playVideoResponseConverter;
    private EpisodeDbSource episodeDbSource;
    private HistoryDbSource historyDbSource;
    private RateSyncDbSource syncDbSource;

    @Inject
    public SeriesRepositoryImpl(@NonNull VideoApi api,
                                @NonNull SeriesResponseConverter responseConverter,
                                @NonNull TranslationResponseConverter translationResponseConverter,
                                @NonNull PlayVideoResponseConverter playVideoResponseConverter,
                                @NonNull EpisodeDbSource episodeDbSource,
                                @NonNull HistoryDbSource historyDbSource,
                                @NonNull RateSyncDbSource syncDbSource) {
        this.api = api;
        this.responseConverter = responseConverter;
        this.translationResponseConverter = translationResponseConverter;
        this.playVideoResponseConverter = playVideoResponseConverter;
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
                                .flatMapSingle(episode -> historyDbSource.isEpisodeWatched(animeId, episode.getId())
                                        .map(isWatched -> {
                                            episode.setWatched(isWatched);
                                            return episode;
                                        }))
                                .toList())
                        .map(episodes -> new Series(series.getErrorMessage(), series.isHasError(), episodes, series.getEpisodesSize())));
    }

    /**
     * Get translations
     */
    @Override
    public Single<List<Translation>> getTranslations(TranslationType type, long animeId, int episodeId) {
        return api.getAnimeVideoInfo(animeId, episodeId)
                .map(document -> translationResponseConverter.convert(animeId, episodeId, document))
                .flatMap(translations -> Observable.fromIterable(translations)
                        .filter(translation -> translation.getType() == type || type == TranslationType.ALL)
                        .toList());
    }

    @Override
    public Single<PlayVideo> getVideo(long animeId, int episodeId, long videoId) {
        return api.getAnimeVideoInfo(animeId, episodeId, videoId)
                .map(document -> playVideoResponseConverter.apply(document, animeId, episodeId));
    }

    @Override
    public Single<PlayVideo> getVideo(long animeId, int episodeId) {
        return api.getAnimeVideoInfo(animeId, episodeId)
                .map(document -> playVideoResponseConverter.apply(document, animeId, episodeId));
    }

    @Override
    public Single<PlayVideo> getVideoSource(long animeId, int episodeId, long videoId) {
        return api.getAnimeVideoInfo(animeId, episodeId, videoId)
                .map(document -> playVideoResponseConverter.apply(document, animeId, episodeId))
                .flatMap(playVideo -> api.getVideoSource(playVideo.getUrl())
                        .map(document -> playVideoResponseConverter
                                .convertDependsOnHosting(
                                        playVideo.getAnimeId(),
                                        playVideo.getEpisodeId(),
                                        playVideo.getHosting(),
                                        playVideo.getTitle(),
                                        document)));
    }

    @Override
    public Single<PlayVideo> getVideoSource(long animeId, int episodeId) {
        return api.getAnimeVideoInfo(animeId, episodeId)
                .map(document -> playVideoResponseConverter.apply(document, animeId, episodeId))
                .flatMap(playVideo -> api.getVideoSource(playVideo.getUrl())
                        .map(document -> playVideoResponseConverter
                                .convertDependsOnHosting(
                                        playVideo.getAnimeId(),
                                        playVideo.getEpisodeId(),
                                        playVideo.getHosting(),
                                        playVideo.getTitle(),
                                        document)));
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
