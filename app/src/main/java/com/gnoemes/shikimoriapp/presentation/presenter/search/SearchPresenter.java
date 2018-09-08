package com.gnoemes.shikimoriapp.presentation.presenter.search;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.domain.app.AnalyticsInteractor;
import com.gnoemes.shikimoriapp.domain.search.SearchInteractor;
import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.anime.domain.Genre;
import com.gnoemes.shikimoriapp.entity.app.domain.AnalyticsEvent;
import com.gnoemes.shikimoriapp.entity.app.domain.BaseException;
import com.gnoemes.shikimoriapp.entity.app.domain.HttpStatusCode;
import com.gnoemes.shikimoriapp.entity.app.domain.NetworkException;
import com.gnoemes.shikimoriapp.entity.app.domain.ServiceCodeException;
import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens;
import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem;
import com.gnoemes.shikimoriapp.entity.search.domain.SearchConstants;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.common.ViewController;
import com.gnoemes.shikimoriapp.presentation.presenter.search.converter.AnimeViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.TitleResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.search.SearchView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@InjectViewState
public class SearchPresenter extends BaseNetworkPresenter<SearchView> {

    private TitleResourceProvider resourceProvider;
    private SearchInteractor interactor;
    private AnimeViewModelConverter converter;
    private AnalyticsInteractor analyticsInteractor;

    private SearchPaginator paginator;
    private HashMap<String, List<FilterItem>> filters = new HashMap<>();

    private String reactiveQuery;
    private Genre genre;
    /**
     * Pagination callback interface for view
     */
    private ViewController<Anime> viewController = new ViewController<Anime>() {
        @Override
        public void showEmptyError(boolean show, Throwable throwable) {
            if (show) {
                processErrors(throwable);
            } else {
                getViewState().clearList();
                getViewState().hideEmptyView();
                getViewState().hideNetworkError();
            }
        }

        @Override
        public void showEmptyView(boolean show) {
            if (show) {
                getViewState().showEmptyView();
            } else {
                getViewState().hideEmptyView();
            }
        }

        @Override
        public void showList(boolean show, List<Anime> list) {
            if (show) {
                if (paginator.isFirstPage()) {
                    getViewState().showList(converter.convertListFrom(list));
                } else {
                    getViewState().insetMore(converter.convertListFrom(list));
                }
            } else {
                getViewState().clearList();
            }
        }

        @Override
        public void showRefreshProgress(boolean show) {
            if (show) {
                getViewState().onShowLoading();
            } else {
                getViewState().onHideLoading();
            }
        }

        @Override
        public void showPageProgress(boolean show) {
            if (show) {
                getViewState().onShowPageLoading();
            } else {
                getViewState().onHidePageLoading();
            }

        }

        @Override
        public void showEmptyProgress(boolean show) {
            if (show) {
                getViewState().onShowLoading();
            } else {
                getViewState().onHideLoading();
            }
        }

        @Override
        public void showError(Throwable throwable) {
            processErrors(throwable);
        }
    };

    public SearchPresenter(@NonNull TitleResourceProvider resourceProvider,
                           @NonNull SearchInteractor interactor,
                           @NonNull AnimeViewModelConverter converter,
                           @NonNull AnalyticsInteractor analyticsInteractor) {
        this.resourceProvider = resourceProvider;
        this.interactor = interactor;
        this.converter = converter;
        this.analyticsInteractor = analyticsInteractor;
    }

    @Override
    public void initData() {
        getViewState().setTitle(resourceProvider.getSearchTitle());
        getViewState().hideEmptyView();
        getViewState().hideNetworkError();
        paginator = new SearchPaginatorImpl(interactor, viewController);

        if (genre == null) {
            paginator.refresh();
        } else {
            getViewState().addBackButton();
            onGenreSearch();
        }

    }

    public void onFiltersSelected(HashMap<String, List<FilterItem>> filters) {
        this.filters = filters;
        paginator.setFilters(filters);
        paginator.restart();
    }

    public void onFilterPressed() {
        analyticsInteractor.logEvent(AnalyticsEvent.FILTER_OPENED);
        getViewState().showFilterDialog(filters);
    }

    @Override
    protected void processErrors(Throwable throwable) {
        BaseException baseException = (BaseException) throwable;
        switch (baseException.getTag()) {
            case ServiceCodeException.TAG:
                processServiceException(throwable);
                break;
            case NetworkException.TAG:
                getViewState().showNetworkError();
                getViewState().clearList();
                break;
            default:
                super.processErrors(throwable);
        }
    }

    private void processServiceException(Throwable throwable) {
        ServiceCodeException exception = (ServiceCodeException) throwable;
        switch (exception.getServiceCode()) {
            case HttpStatusCode.UNPROCESSABLE_ENTITY:
                getViewState().showEmptyView();
                getViewState().clearList();
                break;
        }
    }

    public HashMap<String, List<FilterItem>> getFilters() {
        return filters;
    }

    public void onRefresh() {
        paginator.setFilters(filters);
        paginator.refresh();
    }

    public void onItemClicked(Type type, long id) {
        analyticsInteractor.logEvent(AnalyticsEvent.ANIME_OPENED);
        getRouter().navigateTo(Screens.ANIME_DETAILS, id);
    }

    public void loadNextPage() {
        paginator.setFilters(filters);
        paginator.loadNewPage();
    }

    public void setSearchQuery(String searhQuery) {
        analyticsInteractor.logEvent(AnalyticsEvent.MANUAL_SEARCH);
        filters.put(SearchConstants.SEARCH, Collections.singletonList(
                new FilterItem(SearchConstants.SEARCH, searhQuery, null)));
    }

    public void setSearchQuery() {
        setSearchQuery(reactiveQuery);
        onRefresh();
    }

    public void setSearchReactive(String reactiveQuery) {
        this.reactiveQuery = reactiveQuery;
    }

    private void onGenreSearch() {
        List<FilterItem> items = new ArrayList<>();
        items.add(new FilterItem(SearchConstants.GENRE, genre.getId(), genre.getRussianName()));
        filters.put(SearchConstants.GENRE, items);
        onRefresh();
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (paginator != null) {
            paginator.release();
        }
    }
}
