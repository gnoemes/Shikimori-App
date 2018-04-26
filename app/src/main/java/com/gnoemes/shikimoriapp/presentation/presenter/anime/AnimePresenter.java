package com.gnoemes.shikimoriapp.presentation.presenter.anime;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.domain.anime.AnimeDetailsInteractor;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeGenre;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeDetailsViewModel;
import com.gnoemes.shikimoriapp.entity.main.presentation.BottomScreens;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.view.anime.AnimeView;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class AnimePresenter extends BaseNetworkPresenter<AnimeView> {

    private AnimeDetailsInteractor interactor;
    private AnimeDetailsViewModelConverter viewModelConverter;

    private long animeId;

    public AnimePresenter(@NonNull AnimeDetailsInteractor interactor,
                          @NonNull AnimeDetailsViewModelConverter viewModelConverter) {
        this.interactor = interactor;
        this.viewModelConverter = viewModelConverter;
    }


    @Override
    public void initData() {
        loadAnimeData();
        getViewState().initToolbar();
    }

    private void loadAnimeData() {
        getViewState().onShowLoading();

        Disposable disposable = interactor.loadAnimeDetails(animeId)
                .doOnEvent((animeDetails, throwable) -> getViewState().onHideLoading())
                .map(viewModelConverter)
                .subscribe(this::setData, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    private void setData(AnimeDetailsViewModel model) {
        getViewState().setImage(model.getImageUrl());
        getViewState().setName(model.getName());
        getViewState().setSecondName(model.getJpOrEngName());
        getViewState().setSeason(model.getSeason());
        getViewState().setType(model.getAnimeType());
        getViewState().setStatus(model.getAnimeStatus());
        getViewState().setGenres(model.getGenres());
        getViewState().setScore((float) model.getScore());
        getViewState().setDescription(model.getDescription());
    }

    public void setAnimeId(long animeId) {
        this.animeId = animeId;
    }

    public void onGenreClick(AnimeGenre item) {
        getRouter().navigateTo(BottomScreens.SEARCH, item);
    }

    public void onAddListClick() {
    }

    public void onRelatedClicked() {

    }

    public void onLinksClicked() {
    }

    public void onCommentsClicked() {
    }

    public void onOnlineClicked() {

    }
}
