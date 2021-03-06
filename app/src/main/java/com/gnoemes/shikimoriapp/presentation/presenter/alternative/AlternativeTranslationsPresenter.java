package com.gnoemes.shikimoriapp.presentation.presenter.alternative;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.domain.anime.series.AlternativeSeriesInteractor;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslationType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.AlternativeTranslationViewModel;
import com.gnoemes.shikimoriapp.entity.app.domain.BaseException;
import com.gnoemes.shikimoriapp.entity.app.domain.NetworkException;
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens;
import com.gnoemes.shikimoriapp.entity.main.domain.Constants;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.view.alternative.translations.AlternativeTranslationsView;
import com.gnoemes.shikimoriapp.presentation.view.alternative.translations.converter.AlternativeTranslationViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.TitleResourceProvider;

import java.util.List;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class AlternativeTranslationsPresenter extends BaseNetworkPresenter<AlternativeTranslationsView> {

    private AlternativeSeriesInteractor seriesInteractor;
    private TitleResourceProvider resourceProvider;
    private AlternativeTranslationViewModelConverter converter;

    private AlternativeTranslationType currentTranslationType;
    private AlternativeTranslationViewModel currentTranslation;
    private long episodeId;
    private long animeId;
    private long rateId;


    public AlternativeTranslationsPresenter(AlternativeSeriesInteractor seriesInteractor,
                                            TitleResourceProvider resourceProvider,
                                            AlternativeTranslationViewModelConverter converter) {
        this.seriesInteractor = seriesInteractor;
        this.resourceProvider = resourceProvider;
        this.converter = converter;
    }

    @Override
    public void initData() {
        loadTranslations();
        getViewState().setTitle(resourceProvider.getTranslationsTitle());
    }

    public void loadTranslations() {
        getViewState().onShowLoading();
        getViewState().hideErrorView();
        getViewState().hideEmptyView();

        Disposable disposable = seriesInteractor.getEpisodeTranslations(currentTranslationType, animeId, episodeId)
                .doOnEvent((translations, throwable) -> getViewState().onHideLoading())
                .map(converter)
                .subscribe(this::showData, this::processErrors);

        unsubscribeOnDestroy(disposable);

    }

    private void showData(List<AlternativeTranslationViewModel> models) {
        if (models.isEmpty()) {
            getViewState().showEmptyView();
        } else {
            getViewState().showTranslations(models);
        }
    }


    /**
     * Show dialog with translations type
     */
    public void onSettingsClicked() {
        getViewState().showSettingsDialog();
    }


    /**
     * Sets type to ALL and call {@link #loadTranslations()}
     */
    public void onFindAll() {
        setCurrentTranslationType(AlternativeTranslationType.ALL);
        loadTranslations();
    }

    /**
     * Sets type from user's choice and call {@link #loadTranslations()}
     */
    public void onTypeClicked(AlternativeTranslationType type) {
        setCurrentTranslationType(type);
        loadTranslations();
    }

    public void onTranslationClicked(AlternativeTranslationViewModel translation) {
        this.currentTranslation = translation;
        setEpisodeWatched(animeId, episodeId);
        getRouter().navigateTo(Screens.WEB_PLAYER, translation.getUrl());
    }

    //KOTLIN GIVE ME THE POWER PLS
    private void setEpisodeWatched(long animeId, long episodeId) {
        Disposable disposable;
        if (rateId != Constants.NO_ID) {
            disposable = seriesInteractor.setEpisodeWatched(animeId, episodeId, rateId)
                    .subscribe(() -> {
                    }, this::processErrors);

        } else {
            disposable = seriesInteractor.setEpisodeWatched(animeId, episodeId)
                    .subscribe(() -> {
                    }, this::processErrors);
        }
        unsubscribeOnDestroy(disposable);
    }

    @Override
    protected void processErrors(Throwable throwable) {
        if (throwable instanceof BaseException) {
            BaseException exception = (BaseException) throwable;
            switch (exception.getTag()) {
                case NetworkException.TAG:
                    getViewState().showErrorView();
                    break;
                default:
                    super.processErrors(throwable);
            }
        } else {
            super.processErrors(throwable);
        }
    }

    public void setEpisodeId(long episodeId) {
        this.episodeId = episodeId;
    }

    public void setCurrentTranslationType(AlternativeTranslationType currentTranslationType) {
        this.currentTranslationType = currentTranslationType;
    }

    public void setAnimeId(long animeId) {
        this.animeId = animeId;
    }


    public void setRateId(long rateId) {
        this.rateId = rateId;
    }
}
