package com.gnoemes.shikimoriapp.data.repository.anime.series;

import com.gnoemes.shikimoriapp.data.local.db.EpisodeDbSource;
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
    private EpisodeDbSource dbSource;

    @Inject
    public SeriesRepositoryImpl(VideoApi api,
                                SeriesResponseConverter responseConverter,
                                TranslationResponseConverter translationResponseConverter,
                                EpisodeDbSource dbSource) {
        this.api = api;
        this.responseConverter = responseConverter;
        this.translationResponseConverter = translationResponseConverter;
        this.dbSource = dbSource;
    }

    @Override
    public Single<List<Episode>> getAnimeEpisodes(long animeId) {
//        return dbSource.getEpisodes(animeId)
//                .onErrorResumeNext(throwable -> api.getAnimeSeriesById(animeId)
//                        .map(responseConverter)
//                        .map(Series::getEpisodes)
//                        .flatMap(episodes -> dbSource.saveEpisodes(episodes).toSingleDefault(episodes)));
        return api.getAnimeSeriesById(animeId)
                .map(responseConverter)
                .map(Series::getEpisodes);
//                .flatMap(episodes -> dbSource.saveEpisodes(episodes).toSingleDefault(episodes));
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
    public Completable setEpisodeWatched(long episodeId) {
        return null;
    }
}
