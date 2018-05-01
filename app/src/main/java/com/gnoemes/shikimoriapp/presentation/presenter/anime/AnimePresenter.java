package com.gnoemes.shikimoriapp.presentation.presenter.anime;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.domain.anime.AnimeInteractor;
import com.gnoemes.shikimoriapp.domain.anime.series.SeriesInteractor;
import com.gnoemes.shikimoriapp.domain.app.UserSettingsInteractor;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeDetails;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeGenre;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeAction;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeDetailsViewModel;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeLinkViewModel;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.BaseEpisodeItem;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.EpisodeItem;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Translation;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodeOptionAction;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.PlayerType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.TranslationDubberSettings;
import com.gnoemes.shikimoriapp.entity.app.domain.BaseException;
import com.gnoemes.shikimoriapp.entity.app.domain.ContentException;
import com.gnoemes.shikimoriapp.entity.app.domain.NetworkException;
import com.gnoemes.shikimoriapp.entity.app.domain.UserSettings;
import com.gnoemes.shikimoriapp.entity.main.presentation.BottomScreens;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.converter.AnimeDetailsViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.converter.AnimeLinkViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.view.anime.AnimeView;
import com.gnoemes.shikimoriapp.presentation.view.anime.converter.EpisodeViewModelConverter;

import java.util.List;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class AnimePresenter extends BaseNetworkPresenter<AnimeView> {

    private AnimeInteractor animeInteractor;
    private AnimeDetailsViewModelConverter viewModelConverter;
    private AnimeLinkViewModelConverter linkViewModelConverter;
    private SeriesInteractor seriesInteractor;
    private EpisodeViewModelConverter episodeViewModelConverter;
    private UserSettingsInteractor settingsInteractor;

    private long animeId;
    private AnimeDetails currentAnime;
    private EpisodeItem selectedEpisode;
    private UserSettings userSettings;

    public AnimePresenter(@NonNull AnimeInteractor animeInteractor,
                          @NonNull SeriesInteractor seriesInteractor,
                          @NonNull UserSettingsInteractor settingsInteractor,
                          @NonNull AnimeDetailsViewModelConverter viewModelConverter,
                          @NonNull EpisodeViewModelConverter episodeViewModelConverter,
                          @NonNull AnimeLinkViewModelConverter linkViewModelConverter) {
        this.animeInteractor = animeInteractor;
        this.seriesInteractor = seriesInteractor;
        this.settingsInteractor = settingsInteractor;
        this.viewModelConverter = viewModelConverter;
        this.episodeViewModelConverter = episodeViewModelConverter;
        this.linkViewModelConverter = linkViewModelConverter;
    }


    @Override
    public void initData() {
        loadAnimeData();
        loadEpisodes();
        loadUserSettings();
    }

    /**
     * Subscribe to user settings (on changes settings update)
     */
    private void loadUserSettings() {
        Disposable disposable = settingsInteractor.getUserSettings()
                .doOnNext(this::setCurrentSettings)
                .subscribe();

        unsubscribeOnDestroy(disposable);
    }

    /**
     * Load episodes list
     */
    private void loadEpisodes() {
        getViewState().onShowRefresh();
        getViewState().hideErrorView();

        Disposable disposable = seriesInteractor.getEpisodes(animeId)
                .doOnEvent((episodes, throwable) -> getViewState().onHideRefresh())
                .map(episodeViewModelConverter)
                .subscribe(this::setEpisodes, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    /**
     * Load anime detail data
     */
    private void loadAnimeData() {
        getViewState().onShowLoading();

        Disposable disposable = animeInteractor.loadAnimeDetails(animeId)
                .doOnEvent((animeDetails, throwable) -> getViewState().onHideLoading())
                .map(anime -> currentAnime = anime)
                .map(viewModelConverter)
                .subscribe(this::setAnimeData, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    /**
     * Process errors
     */
    @Override
    protected void processErrors(Throwable throwable) {
        BaseException exception = (BaseException) throwable;
        switch (exception.getTag()) {
            case NetworkException.TAG:
                getViewState().showErrorView();
                break;
            case ContentException.TAG:
                //TODO show message and navigate to translations page
                getRouter().showSystemMessage("Не найдено");
                break;
            default:
                super.processErrors(throwable);
        }
    }

    /**
     * Action on episode clicked
     *
     * @param episode EpisodeItem
     */
    public void onEpisodeClicked(EpisodeItem episode) {
        selectedEpisode = episode;
        if (userSettings.getNeedShowWizard()) {
            getViewState().showSettingsWizard();
        } else {
            switch (userSettings.getDubberSettings()) {
                case AUTO:
                    onAutoLoadTranslation();
                    break;
                case MANUAL:
                    //TODO translations page
                    break;
            }
        }

    }

    /**
     * Load most rated translation with selected type
     */
    private void onAutoLoadTranslation() {
        Disposable disposable = seriesInteractor
                .getAutoTranslation(userSettings.getTranslationType(), selectedEpisode.getId())
                .subscribe(this::onPlayTranslation, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    /**
     * Start the video depending on the type of player
     */
    private void onPlayTranslation(Translation translation) {
        //TODO add other players
        switch (userSettings.getPlayerType()) {
            case EMBEDDED:

                break;
            case EXTERNAL:

                break;
            case BROWSER:
                getViewState().playVideoOnWeb(translation.getEmbedUrl());
                break;
        }
    }

    /**
     * Refresh episodes page
     */
    public void onEpisodesRefresh() {
        loadEpisodes();
    }

    /**
     * Callback from anime details page
     */
    public void onAction(AnimeAction action, @Nullable Object data) {
        switch (action) {
            case WATCH_ONLINE:
                onOnlineClicked();
                break;
            case RATE:
                onRateClicked();
                break;
            case GENRE:
                onGenreClick((AnimeGenre) data);
                break;
            case LINKS:
                onLinksClicked();
                break;
            case RELATED:
                onRelatedClicked();
                break;
            case COMMENTS:
                onCommentsClicked();
                break;
            case ADD_TO_LIST:
                onAddListClick();
                break;
        }
    }

    /**
     * Set anime data to details page
     */
    private void setAnimeData(AnimeDetailsViewModel model) {
        getViewState().setAnimeData(model);
    }

    /**
     * Set episodes list
     */
    private void setEpisodes(List<BaseEpisodeItem> episodes) {
        getViewState().showEpisodeList(episodes);

    }

    private void setCurrentSettings(UserSettings settings) {
        this.userSettings = settings;
    }

    /**
     * Route to search page and search animes with clicked genre
     */
    private void onGenreClick(AnimeGenre item) {
        getRouter().navigateTo(BottomScreens.SEARCH, item);
    }

    /**
     * Add to user's list
     */
    private void onAddListClick() {
        //TODO user lists
    }

    /**
     * Route to related/similar anime list
     */
    private void onRelatedClicked() {
        //TODO screen with related animes
    }

    /**
     * Load external links
     */
    private void onLinksClicked() {
        Disposable disposable = animeInteractor.getAnimeLinks(animeId)
                .map(linkViewModelConverter)
                .subscribe(this::showLinks, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    /**
     * Show links dialog
     */
    private void showLinks(List<AnimeLinkViewModel> animeLinkViewModels) {
        getViewState().showLinksDialog(animeLinkViewModels);
    }

    /**
     * Navigate to comments page
     */
    private void onCommentsClicked() {
        //TODO add comments page in viewPager
    }

    /**
     * Navigate to episodes list
     */
    private void onOnlineClicked() {
        //TODO ENUM navigation
        getViewState().setPage(1);
    }


    private void onRateClicked() {
        //TODO check authorization and rate if user exist
    }

    /**
     * Set current anime id
     */
    public void setAnimeId(long animeId) {
        this.animeId = animeId;
    }

    /**
     * Button callback from episodes page
     */
    public void onEpisodeOptionAction(EpisodeOptionAction action, EpisodeItem item) {
        switch (action) {
            case WATCH_ONLINE:
                onEpisodeClicked(item);
                break;
        }
    }

    /**
     * Wizard callback with user selected settings
     */
    public void onSettingsSelected(TranslationType type, TranslationDubberSettings chooseSettings, PlayerType playerType) {
        UserSettings settings = new UserSettings.Builder()
                .setIsNeedShowWizard(false)
                .setDubberSettings(chooseSettings)
                .setPlayerType(playerType)
                .setTranslationType(type)
                .build();

        Disposable disposable = settingsInteractor.saveUserSettings(settings)
                .doOnComplete(() -> {
                    if (selectedEpisode != null) {
                        onEpisodeClicked(selectedEpisode);
                    }
                })
                .subscribe();

        unsubscribeOnDestroy(disposable);
    }

    /**
     * Open link in browser
     */
    public void onLinkPressed(AnimeLinkViewModel animeLinkViewModel) {
        //TODO open browser activity
    }
}
