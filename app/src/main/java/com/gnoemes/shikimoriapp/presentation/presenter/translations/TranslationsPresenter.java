package com.gnoemes.shikimoriapp.presentation.presenter.translations;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.domain.anime.series.SeriesInteractor;
import com.gnoemes.shikimoriapp.domain.app.UserSettingsInteractor;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.TranslationViewModel;
import com.gnoemes.shikimoriapp.entity.app.domain.BaseException;
import com.gnoemes.shikimoriapp.entity.app.domain.NetworkException;
import com.gnoemes.shikimoriapp.entity.app.domain.UserSettings;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.TitleResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.translations.TranslationsView;
import com.gnoemes.shikimoriapp.presentation.view.translations.converter.TranslationViewModelConverter;

import java.util.List;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class TranslationsPresenter extends BaseNetworkPresenter<TranslationsView> {

    private SeriesInteractor interactor;
    private UserSettingsInteractor settingsInteractor;
    private TitleResourceProvider resourceProvider;
    private TranslationViewModelConverter converter;

    private TranslationType currentTranslation;
    private long episodeId;
    private UserSettings settings;

    public TranslationsPresenter(SeriesInteractor interactor,
                                 UserSettingsInteractor settingsInteractor,
                                 TitleResourceProvider resourceProvider,
                                 TranslationViewModelConverter converter) {
        this.interactor = interactor;
        this.settingsInteractor = settingsInteractor;
        this.resourceProvider = resourceProvider;
        this.converter = converter;
    }

    @Override
    public void initData() {
        loadTranslations();
        loadUserSettings();
        getViewState().setTitle(resourceProvider.getTranslationsTitle());
    }

    /**
     * Load translations with current type
     */
    public void loadTranslations() {
        getViewState().onShowLoading();
        getViewState().hideEmptyView();
        getViewState().hideErrorView();

        Disposable disposable = interactor.getEpisodeTranslations(currentTranslation, episodeId)
                .doOnEvent((translations, throwable) -> getViewState().onHideLoading())
                .map(converter)
                .subscribe(this::setList, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    /**
     * Load user settings
     */
    private void loadUserSettings() {
        Disposable disposable = settingsInteractor.getUserSettings()
                .doOnNext(this::setCurrentSettings)
                .subscribe();

        unsubscribeOnDestroy(disposable);
    }

    /**
     * Set actual user settings
     *
     * @param settings UserSettings
     */
    private void setCurrentSettings(UserSettings settings) {
        this.settings = settings;
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
     *
     * @param url String
     */
    public void onTranslationClicked(String url) {
        //TODO add other players
        switch (settings.getPlayerType()) {
            case BROWSER:
                getViewState().playVideoOnWeb(url);
                break;
            case EXTERNAL:

                break;

            case EMBEDDED:

                break;
        }
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
    public void setEpisodeId(long episodeId) {
        this.episodeId = episodeId;
    }

}
