package com.gnoemes.shikimoriapp.presentation.presenter.anime;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.BuildConfig;
import com.gnoemes.shikimoriapp.domain.anime.AnimeInteractor;
import com.gnoemes.shikimoriapp.domain.anime.series.SeriesInteractor;
import com.gnoemes.shikimoriapp.domain.app.AnalyticsInteractor;
import com.gnoemes.shikimoriapp.domain.app.UserSettingsInteractor;
import com.gnoemes.shikimoriapp.domain.comments.CommentsInteractor;
import com.gnoemes.shikimoriapp.domain.rates.UserRatesInteractor;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeDetails;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeFranchiseNode;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeGenre;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeAction;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeDetailsPage;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeDetailsViewModel;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeLinkViewModel;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.BaseEpisodeItem;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.EpisodeItem;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Translation;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodeOptionAction;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.PlayerType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.TranslationDubberSettings;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.TranslationNavigationData;
import com.gnoemes.shikimoriapp.entity.app.domain.AnalyticsEvent;
import com.gnoemes.shikimoriapp.entity.app.domain.BaseException;
import com.gnoemes.shikimoriapp.entity.app.domain.ContentException;
import com.gnoemes.shikimoriapp.entity.app.domain.HttpStatusCode;
import com.gnoemes.shikimoriapp.entity.app.domain.NetworkException;
import com.gnoemes.shikimoriapp.entity.app.domain.ServiceCodeException;
import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.app.domain.UserSettings;
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens;
import com.gnoemes.shikimoriapp.entity.comments.domain.Comment;
import com.gnoemes.shikimoriapp.entity.main.presentation.BottomScreens;
import com.gnoemes.shikimoriapp.entity.main.presentation.Constants;
import com.gnoemes.shikimoriapp.entity.rates.domain.UserRate;
import com.gnoemes.shikimoriapp.entity.related.domain.RelatedNavigationData;
import com.gnoemes.shikimoriapp.entity.screenshots.domain.ScreenshotNavigationData;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.converter.AnimeDetailsViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.converter.AnimeLinkViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.provider.AnimeDetailsResourceProvider;
import com.gnoemes.shikimoriapp.presentation.presenter.comments.CommentsPaginator;
import com.gnoemes.shikimoriapp.presentation.presenter.comments.CommentsPaginatorImpl;
import com.gnoemes.shikimoriapp.presentation.presenter.comments.converter.CommentsViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.common.ViewController;
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
    private CommentsInteractor commentsInteractor;
    private EpisodeViewModelConverter episodeViewModelConverter;
    private UserSettingsInteractor settingsInteractor;
    private CommentsViewModelConverter commentsConverter;
    private UserRatesInteractor ratesInteractor;
    private AnimeDetailsResourceProvider resourceProvider;
    private AnalyticsInteractor analyticsInteractor;

    private CommentsPaginator paginator;

    private boolean first = true;
    private long animeId;
    private long rateId = Constants.NO_ID;
    private AnimeDetails currentAnime;
    private EpisodeItem selectedEpisode;
    private UserSettings userSettings;
    private ViewController<Comment> viewController = new ViewController<Comment>() {
        @Override
        public void showEmptyError(boolean show, Throwable throwable) {
            if (show) {
                processErrors(throwable);
            }
        }

        @Override
        public void showEmptyView(boolean show) {
            //not implemented
        }

        @Override
        public void showList(boolean show, List<Comment> list) {
            if (show) {
                if (paginator.isFirstPage()) {
                    getViewState().showComments(commentsConverter.convertListFrom(list));
                } else {
                    getViewState().insetMoreComments(commentsConverter.convertListFrom(list));
                }
            }
        }

        @Override
        public void showRefreshProgress(boolean show) {
            //not implemented
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
            //not implemented
        }

        @Override
        public void showError(Throwable throwable) {
            processErrors(throwable);
        }
    };

    public AnimePresenter(@NonNull AnimeInteractor animeInteractor,
                          @NonNull SeriesInteractor seriesInteractor,
                          @NonNull UserSettingsInteractor settingsInteractor,
                          @NonNull CommentsInteractor commentsInteractor,
                          @NonNull UserRatesInteractor ratesInteractor,
                          @NonNull AnimeDetailsViewModelConverter viewModelConverter,
                          @NonNull EpisodeViewModelConverter episodeViewModelConverter,
                          @NonNull AnimeLinkViewModelConverter linkViewModelConverter,
                          @NonNull CommentsViewModelConverter commentsConverter,
                          @NonNull AnimeDetailsResourceProvider resourceProvider,
                          @NonNull AnalyticsInteractor analyticsInteractor) {
        this.animeInteractor = animeInteractor;
        this.seriesInteractor = seriesInteractor;
        this.settingsInteractor = settingsInteractor;
        this.commentsInteractor = commentsInteractor;
        this.ratesInteractor = ratesInteractor;
        this.viewModelConverter = viewModelConverter;
        this.episodeViewModelConverter = episodeViewModelConverter;
        this.linkViewModelConverter = linkViewModelConverter;
        this.commentsConverter = commentsConverter;
        this.resourceProvider = resourceProvider;
        this.analyticsInteractor = analyticsInteractor;
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

    @Override
    public void initData() {
        loadAnimeData();
        getViewState().onShowRefresh();
        loadUserSettings();
        getViewState().setPage(AnimeDetailsPage.INFO.getPage());
        if (!first) {
            loadEpisodes();
        }
    }

    private AnimeDetails setCurrentAnime(AnimeDetails animeDetails) {
        this.currentAnime = animeDetails;
        if (currentAnime.getAnimeRate() != null) {
            this.rateId = currentAnime.getAnimeRate().getId();
        }
        return animeDetails;
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
                if (selectedEpisode != null) {
                    onManualSearchTranslation();
                }
                break;
            case ServiceCodeException.TAG:
                if (((ServiceCodeException) throwable).getServiceCode() == HttpStatusCode.NOT_FOUND) {
                    //not implemented
                    //404 returns on increment episode on unexisting rate
                } else {
                    super.processErrors(throwable);
                }
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
        long prevId = selectedEpisode == null ? Constants.NO_ID : selectedEpisode.getId();
        selectedEpisode = episode;
        if (userSettings.getNeedShowWizard() && prevId != episode.getId()) {
            getViewState().showSettingsWizard(true);
        } else {
            switch (userSettings.getDubberSettings()) {
                case AUTO:
                    onAutoLoadTranslation();
                    break;
                case MANUAL:
                    onManualSearchTranslation();
                    break;
            }
            setEpisodeWatched(episode.getAnimeId(), episode.getId());
        }

    }

    //KOTLIN GIVE ME THE POWER PLS
    private void setEpisodeWatched(long animeId, long episodeId) {
        Disposable disposable;
        if (rateId != Constants.NO_ID) {
            disposable = seriesInteractor.setEpisodeWatched(animeId, episodeId, rateId)
                    .doOnComplete(this::loadEpisodes)
                    .subscribe(() -> {
                    }, this::processErrors);

        } else {
            disposable = seriesInteractor.setEpisodeWatched(animeId, episodeId)
                    .doOnComplete(this::loadEpisodes)
                    .subscribe(() -> {
                    }, this::processErrors);
        }
        unsubscribeOnDestroy(disposable);
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
     * Navigate to translations page with current data
     */
    private void onManualSearchTranslation() {
        analyticsInteractor.logEvent(AnalyticsEvent.TRANSLATIONS_OPENED);
        getRouter().navigateTo(Screens.TRANSLATIONS, new TranslationNavigationData(
                selectedEpisode.getId(),
                userSettings.getTranslationType()));
    }

    /**
     * Wizard callback with user selected settings
     */
    public void onSettingsSelected(boolean loadEpisode, TranslationType type, TranslationDubberSettings chooseSettings, PlayerType playerType, boolean alwaysShow) {
        UserSettings settings = new UserSettings.Builder()
                .setIsNeedShowWizard(alwaysShow)
                .setDubberSettings(chooseSettings)
                .setPlayerType(playerType)
                .setTranslationType(type)
                .build();

        Disposable disposable = settingsInteractor.saveUserSettings(settings)
                .doOnComplete(() -> {
                    if (loadEpisode) {
                        onEpisodeClicked(selectedEpisode);
                        getRouter().showSystemMessage(resourceProvider.getSuccessMessage());
                    }
                })
                .subscribe();

        unsubscribeOnDestroy(disposable);
    }

    /**
     * Start the video depending on the type of player
     */
    private void onPlayTranslation(Translation translation) {
        String authors = TextUtils.isEmpty(translation.getAuthors()) ? "Неизвестный автор" : translation.getAuthors();
        getRouter().showSystemMessage("Выбрано: " + authors);
        //TODO add other players
        switch (userSettings.getPlayerType()) {
            case EMBEDDED:
                analyticsInteractor.logEvent(AnalyticsEvent.EMBEDDED_PLAYER_OPENED);
                getRouter().navigateTo(Screens.EMBEDDED_PLAYER, translation.getId());
                break;
            case EXTERNAL:

                break;
            case BROWSER:
                analyticsInteractor.logEvent(AnalyticsEvent.WEB_PLAYER_OPENED);
                getRouter().navigateTo(Screens.WEB_PLAYER, translation.getEmbedUrl());
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
            case SIMILAR:
                onSimilarClicked();
                break;
            case COMMENTS:
                onCommentsClicked();
                break;
            case ADD_TO_LIST:
                onAddListClick((UserRate) data);
                break;
            case CHRONOLOGY:
                onChronologyClicked();
                break;
            case RELATED:
                onRelatedClicked();
                break;
            case CLEAR_HISTORY:
                onClearHistoryClicked();
                break;
            case OPEN_IN_BROWSER:
                onOpenBrowserClicked();
                break;
            case SHOW_PLAY_SETTINGS:
                getViewState().showSettingsWizard(data != null && (boolean) data);
                break;
            case VIDEO:
                onVideoClicked((String) data);
                break;
        }
    }

    private void onVideoClicked(String data) {
        getRouter().navigateTo(Screens.WEB, data);
    }

    private void onRelatedClicked() {
        getRouter().navigateTo(Screens.RELATED, new RelatedNavigationData(animeId, Type.ANIME));
    }

    private void onChronologyClicked() {
        getViewState().onShowLoading();
        Disposable disposable = animeInteractor.getFranchiseNodes(animeId)
                .doOnEvent((animeLinkViewModels, throwable) -> getViewState().onHideLoading())
                .subscribe(this::showChronologyDialog, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    private void showChronologyDialog(List<AnimeFranchiseNode> nodes) {
        getViewState().showChronologyDialog(nodes);
    }


    /**
     * Route to search page and search animes with clicked genre
     */
    private void onGenreClick(AnimeGenre item) {
        analyticsInteractor.logEvent(AnalyticsEvent.GENRE_SEARCH);
        getRouter().navigateTo(BottomScreens.SEARCH, item);
    }

    /**
     * Add to user's list
     *
     * @param data UserRate
     */
    private void onAddListClick(UserRate data) {
        analyticsInteractor.logEvent(AnalyticsEvent.RATE_OPENED);
        getViewState().showRatesDialog(data);
    }

    /**
     * Route to related/similar anime list
     */
    private void onSimilarClicked() {
        analyticsInteractor.logEvent(AnalyticsEvent.SIMILAR_CLICKED);
        getRouter().navigateTo(Screens.SIMILAR, animeId);
    }

    /**
     * Load external links
     */
    private void onLinksClicked() {
        getViewState().onShowLoading();
        Disposable disposable = animeInteractor.getAnimeLinks(animeId)
                .doOnEvent((animeLinkViewModels, throwable) -> getViewState().onHideLoading())
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
     * Load anime detail data
     */
    private void loadAnimeData() {
        getViewState().onShowLoading();

        Disposable disposable = animeInteractor.loadAnimeDetails(animeId)
                .doOnEvent((animeDetails, throwable) -> getViewState().onHideLoading())
                .map(this::setCurrentAnime)
                .map(viewModelConverter)
                .doOnSuccess(viewModel -> initPaginator())
                .subscribe(this::setAnimeData, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    /**
     * Navigate to comments page
     */
    private void onCommentsClicked() {
        getViewState().setPage(AnimeDetailsPage.COMMENTS.getPage());
    }


    private void onRateClicked() {
        //TODO check authorization and rate if user exist
        //UPDATED: Can user rate?
    }


    /**
     * Button callback from episodes page
     */
    public void onEpisodeOptionAction(EpisodeOptionAction action, EpisodeItem item) {
        switch (action) {
            case WATCH_ONLINE:
                analyticsInteractor.logEvent(AnalyticsEvent.WATCH_ONLINE_MASTER_CLICKED);
                onEpisodeClicked(item);
                break;
        }
    }

    /**
     * Open link in browser
     */
    public void onLinkPressed(AnimeLinkViewModel animeLinkViewModel) {
        getRouter().navigateTo(Screens.WEB, animeLinkViewModel.getUrl());
    }

    /**
     * Set anime data to details page
     */
    private void setAnimeData(AnimeDetailsViewModel model) {
        getViewState().setAnimeData(model);
        if (first) {
            loadEpisodes();
        }
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
     * Set current anime id
     */
    public void setAnimeId(long animeId) {
        this.animeId = animeId;
    }

    private void onOpenBrowserClicked() {
        getRouter().navigateTo(Screens.WEB, BuildConfig.ShikimoriBaseUrl + currentAnime.getUrl());
    }

    /**
     * Navigate to episodes list
     */
    private void onOnlineClicked() {
        analyticsInteractor.logEvent(AnalyticsEvent.WATCH_ONLINE_CLICKED);
        getViewState().setPage(AnimeDetailsPage.SERIES.getPage());
    }

    private void initPaginator() {
        paginator = new CommentsPaginatorImpl(commentsInteractor, viewController);
        paginator.setId(currentAnime.getTopicId());
        paginator.refresh();
    }

    public void loadNextPage() {
        paginator.loadNewPage();
    }

    public void onSaveRate(UserRate rate) {
        if (rate.getId() == Constants.NO_ID) {
            if (userSettings.getUserBrief() != null) {
                createRate(rate);
            } else {
                getRouter().showSystemMessage(resourceProvider.getNeedAuthMessage());
            }
        } else {
            updateRate(rate);
        }
    }


    private void updateRate(UserRate rate) {
        Disposable disposable = ratesInteractor.updateRate(rate)
                .doOnComplete(this::onClearHistory)
                .subscribe(this::onUpdateSuccess, this::processErrors);
        unsubscribeOnDestroy(disposable);
    }

    private void onUpdateSuccess() {
        loadAnimeData();
    }

    private void createRate(UserRate rate) {
        Disposable disposable = ratesInteractor.createRate(currentAnime.getId(), Type.ANIME, rate, userSettings.getUserBrief().getId())
                .subscribe(this::onCreateSuccess, this::processErrors);
        unsubscribeOnDestroy(disposable);
    }

    private void onCreateSuccess() {
        loadAnimeData();
        getRouter().showSystemMessage(String.format(resourceProvider.getCreateMessage(), currentAnime.getRussianName()));
    }

    public void onDeleteRate(long id) {
        Disposable disposable = ratesInteractor.deleteRate(id)
                .subscribe(this::onDeleteSuccess, this::processErrors);
        unsubscribeOnDestroy(disposable);
    }

    private void onDeleteSuccess() {
        loadAnimeData();
        getRouter().showSystemMessage(String.format(resourceProvider.getDeleteMessage(), currentAnime.getRussianName()));
    }

    public void onUserClicked(long id) {
        getRouter().navigateTo(Screens.PROFILE, id);
    }

    private void onClearHistoryClicked() {
        getViewState().showClearHistoryDialog();
    }

    public void onClearHistory() {
        Disposable disposable = seriesInteractor.clearHistory(animeId)
                .subscribe(this::loadAnimeData, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    public void onAnimeClicked(long id) {
        getRouter().navigateTo(Screens.ANIME_DETAILS, id);
    }

    public void onBackgroundImageClicked() {
        getRouter().navigateTo(Screens.SCREENSHOTS, new ScreenshotNavigationData(animeId, currentAnime.getRussianName(), currentAnime.getAnimeImage().getImageOriginalUrl()));
    }
}
