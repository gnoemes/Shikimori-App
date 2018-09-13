package com.gnoemes.shikimoriapp.presentation.presenter.similar;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.domain.anime.similar.SimilarInteractor;
import com.gnoemes.shikimoriapp.domain.app.AnalyticsInteractor;
import com.gnoemes.shikimoriapp.entity.app.domain.BaseException;
import com.gnoemes.shikimoriapp.entity.app.domain.NetworkException;
import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.search.converter.AnimeViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.search.converter.MangaViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.similar.SimilarView;

import java.util.List;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class SimilarPresenter extends BaseNetworkPresenter<SimilarView> {

    private SimilarInteractor interactor;
    private AnimeViewModelConverter converter;
    private MangaViewModelConverter mangaViewModelConverter;
    private AnalyticsInteractor analyticsInteractor;

    private long id;
    private Type type;

    public SimilarPresenter(SimilarInteractor interactor,
                            AnimeViewModelConverter converter,
                            MangaViewModelConverter mangaViewModelConverter,
                            AnalyticsInteractor analyticsInteractor) {
        this.interactor = interactor;
        this.converter = converter;
        this.mangaViewModelConverter = mangaViewModelConverter;
        this.analyticsInteractor = analyticsInteractor;
    }

    @Override
    public void initData() {
        getViewState().setTitle(R.string.common_similar);
        loadData();
    }

    private void loadData() {
        switch (type) {
            case RANOBE:
                loadRanobe();
                break;
            case ANIME:
                loadAnimes();
                break;
            case MANGA:
                loadMangas();
                break;
        }
    }

    private void loadMangas() {
        getViewState().hideNetworkError();
        getViewState().onShowLoading();
        getViewState().hideEmptyView();

        Disposable disposable = interactor.getSimilarMangas(id)
                .doOnEvent((mangas, throwable) -> getViewState().onHideLoading())
                .map(mangas -> mangaViewModelConverter.convertListFrom(mangas))
                .subscribe(this::setData, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    private void loadRanobe() {
        getViewState().hideNetworkError();
        getViewState().onShowLoading();
        getViewState().hideEmptyView();

        Disposable disposable = interactor.getSimilarRanobe(id)
                .doOnEvent((mangas, throwable) -> getViewState().onHideLoading())
                .map(mangas -> mangaViewModelConverter.convertListFrom(mangas))
                .subscribe(this::setData, this::processErrors);

        unsubscribeOnDestroy(disposable);

    }

    private void loadAnimes() {
        getViewState().hideNetworkError();
        getViewState().onShowLoading();
        getViewState().hideEmptyView();

        Disposable disposable = interactor.getSimilarAnimes(id)
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

    public void onRefresh() {
        loadAnimes();
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
