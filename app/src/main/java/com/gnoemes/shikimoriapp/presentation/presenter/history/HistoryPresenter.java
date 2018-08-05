package com.gnoemes.shikimoriapp.presentation.presenter.history;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.domain.history.HistoryInteractor;
import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.app.domain.BaseException;
import com.gnoemes.shikimoriapp.entity.app.domain.ContentException;
import com.gnoemes.shikimoriapp.entity.app.domain.NetworkException;
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.history.converter.HistoryViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.history.HistoryView;

import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class HistoryPresenter extends BaseNetworkPresenter<HistoryView> {

    private HistoryInteractor interactor;
    private HistoryViewModelConverter converter;

    private String query;

    public HistoryPresenter(HistoryInteractor interactor,
                            HistoryViewModelConverter converter) {
        this.interactor = interactor;
        this.converter = converter;
    }

    @Override
    public void initData() {
        loadHistory();
    }

    private void loadHistory() {
        getViewState().hideNetworkErrorView();
        getViewState().onShowLoading();

        Disposable disposable = interactor.getLocalWatchedAnimes(1, 1000, query)
                .doOnEvent((animes, throwable) -> getViewState().onHideLoading())
                .subscribe(this::showList, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    private void showList(List<Anime> animes) {
        getViewState().showList(converter.convertFrom(animes));
    }


    public void onRefresh() {
        loadHistory();
    }

    public void onItemClicked(long id) {
        getRouter().navigateTo(Screens.ANIME_DETAILS, id);
    }

    public void setSearchQuery() {
        setSearchQuery(query);
        onRefresh();
    }

    public void setSearchQuery(String query) {
        this.query = query;
    }

    public void setSearchReactive(String newText) {
        this.query = newText;
    }

    /**
     * Catch errors
     */
    @Override
    protected void processErrors(Throwable throwable) {
        BaseException baseException = (BaseException) throwable;
        switch (baseException.getTag()) {
            case NetworkException.TAG:
                processNetworkError(throwable);
                break;
            case ContentException.TAG:
                //not implemented (empty page)
                getViewState().showList(Collections.emptyList());
                break;

            default:
                super.processErrors(throwable);
        }
    }

    private void processNetworkError(Throwable throwable) {
        getViewState().showNetworkErrorView();
    }

}
