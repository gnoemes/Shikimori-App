package com.gnoemes.shikimoriapp.data.repository.anime;

import com.gnoemes.shikimoriapp.data.local.db.HistoryDbSource;
import com.gnoemes.shikimoriapp.data.local.db.RateSyncDbSource;
import com.gnoemes.shikimoriapp.data.network.AnimesApi;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeDetailsResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeListResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.FranchiseResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.LinkResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.ScreenshotResponseConverter;
import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeDetails;
import com.gnoemes.shikimoriapp.entity.anime.domain.FranchiseNode;
import com.gnoemes.shikimoriapp.entity.anime.domain.Link;
import com.gnoemes.shikimoriapp.entity.anime.series.data.db.HistoryDao;
import com.gnoemes.shikimoriapp.entity.screenshots.domain.Screenshot;

import org.joda.time.DateTimeComparator;

import java.util.LinkedHashSet;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

public class AnimeRepositoryImpl implements AnimeRepository {

    private AnimesApi animesApi;
    private RateSyncDbSource syncDbSource;
    private HistoryDbSource historyDbSource;
    private AnimeDetailsResponseConverter responseConverter;
    private AnimeListResponseConverter listResponseConverter;
    private LinkResponseConverter linkReponseConverter;
    private FranchiseResponseConverter franchiseResponseConverter;
    private ScreenshotResponseConverter screenshotResponseConverter;

    @Inject
    public AnimeRepositoryImpl(AnimesApi animesApi,
                               RateSyncDbSource syncDbSource,
                               HistoryDbSource historyDbSource,
                               AnimeDetailsResponseConverter responseConverter,
                               LinkResponseConverter linkReponseConverter,
                               AnimeListResponseConverter listResponseConverter,
                               FranchiseResponseConverter franchiseResponseConverter,
                               ScreenshotResponseConverter screenshotResponseConverter) {
        this.animesApi = animesApi;
        this.syncDbSource = syncDbSource;
        this.historyDbSource = historyDbSource;
        this.responseConverter = responseConverter;
        this.linkReponseConverter = linkReponseConverter;
        this.listResponseConverter = listResponseConverter;
        this.franchiseResponseConverter = franchiseResponseConverter;
        this.screenshotResponseConverter = screenshotResponseConverter;
    }

    @Override
    public Single<AnimeDetails> getAnimeDetails(long animeId) {
        return animesApi.getAnimeDetails(animeId)
                .flatMap(animeDetailsResponse -> animesApi.getRoles(animeId)
                        .map(rolesResponses -> responseConverter.convertDetailsWithCharacters(animeDetailsResponse, rolesResponses)))
                .flatMap(animeDetails -> Single.just(animeDetails)
                        .filter(details -> details.getAnimeRate() != null)
                        .flatMapCompletable(anime -> syncDbSource.saveRateEpisodes(
                                anime.getAnimeRate().getId(),
                                anime.getId(),
                                Integer.parseInt(anime.getAnimeRate().getEpisodes())))
                        .toSingleDefault(animeDetails));
    }

    @Override
    public Single<List<Link>> getAnimeLinks(long animeId) {
        return animesApi.getAnimeLinks(animeId)
                .map(linkReponseConverter);
    }

    @Override
    public Single<List<Anime>> getSimilarAnimes(long animeId) {
        return animesApi.getSimilarAnimes(animeId)
                .map(listResponseConverter);
    }

    @Override
    public Single<List<FranchiseNode>> getFranchiseNodes(long animeId) {
        return animesApi.getFranchise(animeId)
                .map(franchiseResponseConverter)
                .flatMap(nodes -> Observable.fromIterable(nodes)
                        .toSortedList((o1, o2) -> DateTimeComparator.getInstance().compare(o1.getDateTime(), o2.getDateTime())));
    }

    @Override
    public Single<List<Screenshot>> getScreenshots(long animeId) {
        return animesApi.getScreenshots(animeId)
                .map(screenshotResponseConverter);
    }

    @Override
    public Single<LinkedHashSet<Long>> getLocalWatchedAnimeIds() {
        return historyDbSource.getWatchedAnimes()
                .flatMap(historyDaos -> Observable.fromIterable(historyDaos)
                        .map(HistoryDao::getAnimeId)
                        .toList())
                .map(LinkedHashSet::new);
    }
}
