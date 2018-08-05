package com.gnoemes.shikimoriapp.domain.history;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.data.repository.anime.AnimeRepository;
import com.gnoemes.shikimoriapp.data.repository.search.SearchRepository;
import com.gnoemes.shikimoriapp.domain.search.SearchQueryBuilder;
import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.utils.rx.RxUtils;
import com.gnoemes.shikimoriapp.utils.rx.SingleErrorHandler;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class HistoryInteractorImpl implements HistoryInteractor {

    private SearchRepository searchRepository;
    private AnimeRepository animeRepository;
    private SearchQueryBuilder queryBuilder;
    private HistorySortConverter sortConverter;
    private RxUtils rxUtils;
    private SingleErrorHandler singleErrorHandler;

    @Inject
    public HistoryInteractorImpl(SearchRepository searchRepository,
                                 AnimeRepository animeRepository,
                                 SearchQueryBuilder queryBuilder,
                                 HistorySortConverter sortConverter,
                                 RxUtils rxUtils,
                                 SingleErrorHandler singleErrorHandler) {
        this.searchRepository = searchRepository;
        this.animeRepository = animeRepository;
        this.queryBuilder = queryBuilder;
        this.sortConverter = sortConverter;
        this.rxUtils = rxUtils;
        this.singleErrorHandler = singleErrorHandler;
    }

    @Override
    public Single<List<Anime>> getLocalWatchedAnimes(int page, int limit, @Nullable String searchQuery) {
        return animeRepository.getLocalWatchedAnimeIds()
                .filter(longs -> !longs.isEmpty())
                .flatMapSingle(ids -> queryBuilder.createQueryFromIds(ids, searchQuery, page, limit)
                        .flatMap(query -> searchRepository.getAnimeList(query))
                        .map(animes -> sortConverter.sortAnimeByIds(animes, ids))
                )
                .compose((SingleErrorHandler<List<Anime>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }
}
