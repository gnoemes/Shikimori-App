package com.gnoemes.shikimoriapp.domain.search;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.repository.search.SearchRepository;
import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem;
import com.gnoemes.shikimoriapp.utils.rx.RxUtils;
import com.gnoemes.shikimoriapp.utils.rx.SingleErrorHandler;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class SearchInteractorImpl implements SearchInteractor {

    private SearchRepository searchRepository;
    private SearchQueryBuilder queryBuilder;
    private SingleErrorHandler singleErrorHandler;
    private RxUtils rxUtils;


    @Inject
    public SearchInteractorImpl(@NonNull SearchRepository searchRepository,
                                @NonNull SearchQueryBuilder queryBuilder,
                                @NonNull SingleErrorHandler singleErrorHandler,
                                @NonNull RxUtils rxUtils) {
        this.searchRepository = searchRepository;
        this.queryBuilder = queryBuilder;
        this.singleErrorHandler = singleErrorHandler;
        this.rxUtils = rxUtils;
    }

    @Override
    public Single<List<Anime>> loadListWithFilters(HashMap<String, List<FilterItem>> filters, int page, int limit) {
        return queryBuilder.createQueryFromFilters(filters, page, limit)
                .flatMap(queryMap -> searchRepository.getAnimeList(queryMap))
                .compose((SingleErrorHandler<List<Anime>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }

    @Override
    public Single<List<Anime>> loadList(int page, int limit) {

        return queryBuilder.createQueryFromFilters(null, page, limit)
                .flatMap(queryMap -> searchRepository.getAnimeList(queryMap))
                .compose((SingleErrorHandler<List<Anime>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }
}
