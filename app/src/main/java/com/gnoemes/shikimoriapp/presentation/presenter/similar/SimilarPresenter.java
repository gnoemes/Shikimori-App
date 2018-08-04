package com.gnoemes.shikimoriapp.presentation.presenter.similar;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.domain.anime.similar.SimilarAnimeInteractor;
import com.gnoemes.shikimoriapp.domain.app.AnalyticsInteractor;
import com.gnoemes.shikimoriapp.entity.app.domain.AnalyticsEvent;
import com.gnoemes.shikimoriapp.entity.app.domain.BaseException;
import com.gnoemes.shikimoriapp.entity.app.domain.NetworkException;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.search.converter.AnimeViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.similar.SimilarView;

import java.util.List;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class SimilarPresenter extends BaseNetworkPresenter<SimilarView> {

    private SimilarAnimeInteractor animeInteractor;
    private AnimeViewModelConverter converter;
    private AnalyticsInteractor analyticsInteractor;

    private long animeId;

    public SimilarPresenter(SimilarAnimeInteractor animeInteractor,
                            AnimeViewModelConverter converter,
                            AnalyticsInteractor analyticsInteractor) {
        this.animeInteractor = animeInteractor;
        this.converter = converter;
        this.analyticsInteractor = analyticsInteractor;
    }

    @Override
    public void initData() {
        getViewState().setTitle(R.string.common_similar);
        loadAnimes();
    }

    private void loadAnimes() {
        getViewState().hideNetworkError();
        getViewState().onShowLoading();
        getViewState().hideEmptyView();

        Disposable disposable = animeInteractor.getSimilarAnimes(animeId)
                .doOnEvent((animes, throwable) -> getViewState().onHideLoading())
                .map(animes -> converter.convertListFrom(animes))
                .subscribe(this::setData, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    @Override
    protected void processErrors(Throwable throwable) {
        BaseException exception = (BaseException) throwable;
        switch (exception.getTag()) {
            case NetworkException.TAG:
                getViewState().hideList();
                getViewState().showNetworkError();
                break;
            default:
                super.processErrors(throwable);
        }
    }

    private void setData(List<BaseItem> searchItems) {
        if (searchItems.isEmpty()) {
            getViewState().hideList();
            getViewState().showEmptyView();
        } else {
            getViewState().showList(searchItems);
        }
    }


    public void onAnimeClicked(long id) {
        analyticsInteractor.logEvent(AnalyticsEvent.ANIME_OPENED);
        getRouter().navigateTo(Screens.ANIME_DETAILS, id);
    }

    public void onRefresh() {
        loadAnimes();
    }

    public void setAnimeId(long animeId) {
        this.animeId = animeId;
    }
}
