package com.gnoemes.shikimoriapp.presentation.presenter.translations;

import android.text.TextUtils;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.domain.anime.series.SeriesInteractor;
import com.gnoemes.shikimoriapp.domain.download.DownloadInteractor;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.PlayerType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.TranslationViewModel;
import com.gnoemes.shikimoriapp.entity.app.domain.BaseException;
import com.gnoemes.shikimoriapp.entity.app.domain.ContentException;
import com.gnoemes.shikimoriapp.entity.app.domain.NetworkException;
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens;
import com.gnoemes.shikimoriapp.entity.main.domain.Constants;
import com.gnoemes.shikimoriapp.entity.series.domain.PlayVideo;
import com.gnoemes.shikimoriapp.entity.series.domain.VideoHosting;
import com.gnoemes.shikimoriapp.entity.series.presentation.PlayVideoNavigationData;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.TitleResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.translations.TranslationsView;
import com.gnoemes.shikimoriapp.presentation.view.translations.converter.TranslationViewModelConverter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class TranslationsPresenter extends BaseNetworkPresenter<TranslationsView> {

    private SeriesInteractor interactor;
    private TitleResourceProvider resourceProvider;
    private TranslationViewModelConverter converter;
    private DownloadInteractor downloadInteractor;

    private TranslationType currentTranslationType;
    private TranslationViewModel currentTranslation;
    private int episodeId;
    private long animeId;
    private long rateId;

    public TranslationsPresenter(SeriesInteractor interactor,
                                 DownloadInteractor downloadInteractor,
                                 TitleResourceProvider resourceProvider,
                                 TranslationViewModelConverter converter) {
        this.interactor = interactor;
        this.downloadInteractor = downloadInteractor;
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
        getViewState().hideEmptyView();
        getViewState().hideErrorView();
        getViewState().onShowLoading();

        Disposable disposable = interactor.getEpisodeTranslations(currentTranslationType, animeId, episodeId)
                .doOnEvent((translations, throwable) -> getViewState().onHideLoading())
                .map(converter)
                .subscribe(this::setList, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    /**
     * Sets type to ALL and call {@link #loadTranslations()}
     */
    public void onFindAll() {
        setCurrentTranslationType(TranslationType.ALL);
        loadTranslations();
    }

    /**
     * Sets type from user's choice and call {@link #loadTranslations()}
     */
    public void onTypeClicked(TranslationType type) {
        setCurrentTranslationType(type);
        loadTranslations();
    }

    /**
     * Starts video with player from user's settings
     */
    public void onTranslationClicked(TranslationViewModel translation) {
        this.currentTranslation = translation;

        List<PlayerType> players = new ArrayList<>();
        players.add(PlayerType.WEB);
        if (translation.getHosting() == VideoHosting.VK ||
                translation.getHosting() == VideoHosting.SIBNET) {
            players.add(PlayerType.EMBEDDED);
            players.add(PlayerType.EXTERNAL);
        }

        if (players.size() == 1) {
            onPlay(players.get(0));
        } else {
            getViewState().showPlayerDialog(players);
        }
    }

    public void onDownloadTranslation(TranslationViewModel translation) {
        currentTranslation = translation;
        getViewState().checkPermissions();
    }

    private void downloadTranslation() {
        if (currentTranslation.getHosting() == VideoHosting.SMOTRET_ANIME) {
            Disposable disposable = interactor.getVideo(currentTranslation.getAnimeId(), currentTranslation.getEpisodeId(), currentTranslation.getVideoId())
                    .doOnSubscribe(disposable1 -> getViewState().onShowLoading())
                    .flatMapCompletable(downloadInteractor::downloadVideo)
                    .doOnComplete(() -> getViewState().onHideLoading())
                    .doOnError(throwable -> getViewState().onHideLoading())
                    .doOnComplete(() -> getRouter().showSystemMessage("Загрузка началась.\nУспешность загрузки с этого ресурса не гарантируется"))
                    .subscribe(() -> {
                    }, this::processErrors);

            unsubscribeOnDestroy(disposable);
        } else {
            Disposable disposable = interactor.getVideoSource(currentTranslation.getAnimeId(), currentTranslation.getEpisodeId(), currentTranslation.getVideoId())
                    .doOnSubscribe(disposable1 -> getViewState().onShowLoading())
                    .flatMapCompletable(downloadInteractor::downloadVideo)
                    .doOnComplete(() -> getViewState().onHideLoading())
                    .doOnError(throwable -> getViewState().onHideLoading())
                    .doOnComplete(() -> getRouter().showSystemMessage("Загрузка началась"))
                    .subscribe(() -> {
                    }, this::processErrors);

            unsubscribeOnDestroy(disposable);
        }
    }

    public void onPlay(PlayerType type) {
        switch (type) {
            case WEB:
                onPlayWeb();
                break;
            case EMBEDDED:
                onPlayEmbedded();
                break;
            case EXTERNAL:
                onPlayExternal();
                break;
        }
        setEpisodeWatched(currentTranslation.getAnimeId(), currentTranslation.getEpisodeId());
    }

    private void onPlayExternal() {
        getViewState().onShowLoading();

        Disposable disposable = interactor.getVideoSource(currentTranslation.getAnimeId(), currentTranslation.getEpisodeId(), currentTranslation.getVideoId())
                .doOnEvent((playVideo, throwable) -> getViewState().onHideLoading())
                .subscribe(this::playExternal, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    private void playExternal(PlayVideo playVideo) {
        if (playVideo.getTracks() != null && !playVideo.getTracks().isEmpty()) {

            if (playVideo.getTracks().size() == 1) {
                onQualityForExternalChoosed(playVideo.getTracks().get(0).getUrl());
            } else {
                getViewState().showQualityDialog(playVideo.getTracks());
            }

        } else if (!TextUtils.isEmpty(playVideo.getSourceUrl())) {
            onQualityForExternalChoosed(playVideo.getSourceUrl());
        } else {
            getRouter().showSystemMessage("Произошла ошибка во время загрузки видео.");
        }
    }

    public void onQualityForExternalChoosed(String url) {
        getRouter().navigateTo(Screens.EXTERNAL_PLAYER, url);
    }

    private void onPlayEmbedded() {
        getRouter().navigateTo(Screens.EMBEDDED_PLAYER, new PlayVideoNavigationData(
                currentTranslation.getAnimeId(),
                currentTranslation.getEpisodeId(),
                currentTranslation.getVideoId(),
                currentTranslation.getEpisodesSize(),
                rateId
        ));
    }

    private void onPlayWeb() {
        getViewState().onShowLoading();

        Disposable disposable = interactor.getVideo(currentTranslation.getAnimeId(), currentTranslation.getEpisodeId(), currentTranslation.getVideoId())
                .doOnEvent((playVideo, throwable) -> getViewState().onHideLoading())
                .subscribe(this::playWeb, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    private void playWeb(PlayVideo playVideo) {
        if (!TextUtils.isEmpty(playVideo.getSourceUrl())) {
            getRouter().navigateTo(Screens.WEB_PLAYER, playVideo.getSourceUrl());
        }
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
        if (throwable instanceof BaseException) {
            BaseException exception = (BaseException) throwable;
            switch (exception.getTag()) {
                case NetworkException.TAG:
                    getViewState().showErrorView();
                    break;
                case ContentException.TAG:
                    getRouter().showSystemMessage("Произошла ошибка во время загрузки видео");
                    break;
                default:
                    super.processErrors(throwable);
            }
        } else {
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
    public void setCurrentTranslationType(TranslationType currentTranslation) {
        this.currentTranslationType = currentTranslation;
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


    public void onPermissionDenied() {
        getRouter().showSystemMessage("Для загрузки видео необходимо разрешение");
    }

    public void onNeverAskAgain() {
        getRouter().showSystemMessage("Для загрузки видео необходимо разрешение");
    }


    public void onPermissionGranted() {
        downloadTranslation();
    }
}
