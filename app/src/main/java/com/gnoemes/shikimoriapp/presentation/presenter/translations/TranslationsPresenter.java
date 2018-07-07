package com.gnoemes.shikimoriapp.presentation.presenter.translations;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.domain.anime.series.SeriesInteractor;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.TranslationViewModel;
import com.gnoemes.shikimoriapp.entity.app.domain.BaseException;
import com.gnoemes.shikimoriapp.entity.app.domain.NetworkException;
import com.gnoemes.shikimoriapp.entity.main.presentation.Constants;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.TitleResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.translations.TranslationsView;
import com.gnoemes.shikimoriapp.presentation.view.translations.converter.TranslationViewModelConverter;

import java.util.List;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class TranslationsPresenter extends BaseNetworkPresenter<TranslationsView> {

    private SeriesInteractor interactor;
    private TitleResourceProvider resourceProvider;
    private TranslationViewModelConverter converter;

    private TranslationType currentTranslation;
    private int episodeId;
    private long animeId;
    private long rateId;

    public TranslationsPresenter(SeriesInteractor interactor,
                                 TitleResourceProvider resourceProvider,
                                 TranslationViewModelConverter converter) {
        this.interactor = interactor;
        this.resourceProvider = resourceProvider;
        this.converter = converter;
    }

    @Override
    public void initData() {
        loadTranslations();
        getViewState().setTitle(resourceProvider.getTranslationsTitle());
    }

    /**
     * Load translations with current type
     */
    public void loadTranslations() {
        getViewState().onShowLoading();
        getViewState().hideEmptyView();
        getViewState().hideErrorView();

        Disposable disposable = interactor.getEpisodeTranslations(currentTranslation, animeId, episodeId)
                .doOnEvent((translations, throwable) -> getViewState().onHideLoading())
                .map(converter)
                .subscribe(this::setList, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    /**
     * Sets type to ALL and call {@link #loadTranslations()}
     */
    public void onFindAll() {
        setCurrentTranslation(TranslationType.ALL);
        loadTranslations();
    }

    /**
     * Sets type from user's choice and call {@link #loadTranslations()}
     */
    public void onTypeClicked(TranslationType type) {
        setCurrentTranslation(type);
        loadTranslations();
    }

    /**
     * Starts video with player from user's settings
     */
    public void onTranslationClicked(TranslationViewModel translation) {

    }

    //KOTLIN GIVE ME THE POWER PLS
    private void setEpisodeWatched(long animeId, long episodeId) {
        Disposable disposable;
        if (rateId != Constants.NO_ID) {
            disposable = interactor.setEpisodeWatched(animeId, episodeId, rateId)
                    .subscribe(() -> {
                    }, this::processErrors);

        } else {
            disposable = interactor.setEpisodeWatched(animeId, episodeId)
                    .subscribe(() -> {
                    }, this::processErrors);
        }
        unsubscribeOnDestroy(disposable);
    }

    /**
     * Show dialog with translations type
     */
    public void onSettingsClicked() {
        getViewState().showSettingsDialog();
    }

    /**
     * Processing errors -_-
     */
    @Override
    protected void processErrors(Throwable throwable) {
        BaseException exception = (BaseException) throwable;
        switch (exception.getTag()) {
            case NetworkException.TAG:
                getViewState().showErrorView();
                break;
            default:
                super.processErrors(throwable);
        }
    }

    /**
     * Sets list to adapter or shows empty view
     */
    private void setList(List<TranslationViewModel> translations) {
        if (translations.isEmpty()) {
            getViewState().showEmptyView();
        } else {
            getViewState().showTranslations(translations);
        }
    }

    /**
     * Sets current type
     *
     * @param currentTranslation {@link TranslationType}
     */
    public void setCurrentTranslation(TranslationType currentTranslation) {
        this.currentTranslation = currentTranslation;
    }

    /**
     * Sets episodeId
     *
     * @param episodeId long
     */
    public void setEpisodeId(int episodeId) {
        this.episodeId = episodeId;
    }

    public long getAnimeId() {
        return animeId;
    }

    public void setAnimeId(long animeId) {
        this.animeId = animeId;
    }

    public long getRateId() {
        return rateId;
    }

    public void setRateId(long rateId) {
        this.rateId = rateId;
    }
}
