package com.gnoemes.shikimoriapp.presentation.presenter.alternative;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.domain.anime.AnimeInteractor;
import com.gnoemes.shikimoriapp.domain.anime.series.AlternativeSeriesInteractor;
import com.gnoemes.shikimoriapp.domain.app.AnalyticsInteractor;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeDetails;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslationType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.AlternativeTranslationNavigationData;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodeItem;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodeOptionAction;
import com.gnoemes.shikimoriapp.entity.app.domain.AnalyticsEvent;
import com.gnoemes.shikimoriapp.entity.app.domain.BaseException;
import com.gnoemes.shikimoriapp.entity.app.domain.HttpStatusCode;
import com.gnoemes.shikimoriapp.entity.app.domain.NetworkException;
import com.gnoemes.shikimoriapp.entity.app.domain.ServiceCodeException;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens;
import com.gnoemes.shikimoriapp.entity.main.domain.Constants;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.view.alternative.episodes.AlternativeEpisodesView;
import com.gnoemes.shikimoriapp.presentation.view.alternative.episodes.converter.AlternativeEpisodeViewModelConverter;

import java.util.Arrays;
import java.util.List;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class AlternativeEpisodesPresenter extends BaseNetworkPresenter<AlternativeEpisodesView> {

    private AnimeInteractor animeInteractor;
    private AlternativeSeriesInteractor seriesInteractor;
    private AlternativeEpisodeViewModelConverter viewModelConverter;
    private AnalyticsInteractor analyticsInteractor;


    private boolean isEpisodesReversed = false;
    private long animeId;
    private long rateId = Constants.NO_ID;
    private EpisodeItem selectedEpisode;

    public AlternativeEpisodesPresenter(AnimeInteractor animeInteractor,
                                        AlternativeSeriesInteractor seriesInteractor,
                                        AlternativeEpisodeViewModelConverter viewModelConverter,
                                        AnalyticsInteractor analyticsInteractor) {
        this.animeInteractor = animeInteractor;
        this.seriesInteractor = seriesInteractor;
        this.viewModelConverter = viewModelConverter;
        this.analyticsInteractor = analyticsInteractor;
    }


    @Override
    public void initData() {
        getViewState().setTitle(R.string.episodes_alternative);
        loadEpisodes();
    }

    /**
     * Load episodes list
     */
    private void loadEpisodes() {
        getViewState().onShowLoading();
        getViewState().hideErrorView();

        Disposable disposable = animeInteractor.loadAnimeDetails(animeId)
                .map(this::setAnime)
                .flatMap(animeDetails -> seriesInteractor.getEpisodes(animeId))
                .doOnEvent((episodes, throwable) -> getViewState().onHideLoading())
                .map(viewModelConverter)
                .subscribe(this::setEpisodes, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    private AnimeDetails setAnime(AnimeDetails animeDetails) {
        if (animeDetails.getAnimeRate() != null) {
            rateId = animeDetails.getAnimeRate().getId();
        }
        return animeDetails;
    }

    private void setEpisodes(List<BaseItem> episodes) {
        getViewState().showList(isEpisodesReversed, episodes);
    }

    /**
     * Refresh episodes page
     */
    public void onEpisodesRefresh() {
        loadEpisodes();
    }


    /**
     * Action on episode clicked
     *
     * @param episode EpisodeItem
     */
    public void onEpisodeClicked(EpisodeItem episode) {
        selectedEpisode = episode;
        List<AlternativeTranslationType> types = Arrays.asList(AlternativeTranslationType.values());
        getViewState().showTranslationDialog(types);
    }

    public void onTranslationChoosed(AlternativeTranslationType type) {
        getRouter().navigateTo(Screens.ALTERNATIVE_TRANSLATIONS, new AlternativeTranslationNavigationData(animeId, selectedEpisode.getId(), rateId, type));
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
            case REVERSE_LIST:
                analyticsInteractor.logEvent(AnalyticsEvent.REVERSE_EPISODES);
                onReverseEpisodes();
                break;
        }
    }

    private void onReverseEpisodes() {
        isEpisodesReversed = !isEpisodesReversed;
        getViewState().reverseEpisodes();
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

    public void setAnimeId(long animeId) {
        this.animeId = animeId;
    }
}
