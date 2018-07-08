package com.gnoemes.shikimoriapp.presentation.presenter.player;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.domain.anime.series.SeriesInteractor;
import com.gnoemes.shikimoriapp.entity.main.presentation.Constants;
import com.gnoemes.shikimoriapp.entity.series.domain.PlayVideo;
import com.gnoemes.shikimoriapp.entity.series.presentation.PlayVideoNavigationData;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.view.player.embedded.EmbeddedPlayerView;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class EmbeddedPlayerPresenter extends BaseNetworkPresenter<EmbeddedPlayerView> {

    private SeriesInteractor seriesInteractor;

    private long animeId;
    private int currentEpisode;
    private long videoId;
    private int episodesSize;

    public EmbeddedPlayerPresenter(SeriesInteractor seriesInteractor) {
        this.seriesInteractor = seriesInteractor;
    }

    @Override
    public void initData() {
        super.initData();
        loadVideo();
    }

    private void loadVideo() {
        getViewState().onShowLoading();
        if (videoId != Constants.NO_ID) {
            loadVideoWithId();
        } else {
            loadVideoWithoutId();
        }
    }

    private void loadVideoWithoutId() {
        Disposable disposable = seriesInteractor.getVideoSource(animeId, currentEpisode)
                .doOnEvent((playVideo, throwable) -> getViewState().onHideLoading())
                .subscribe(this::updateVideo, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    private void loadVideoWithId() {
        Disposable disposable = seriesInteractor.getVideoSource(animeId, currentEpisode, videoId)
                .doOnEvent((playVideo, throwable) -> getViewState().onHideLoading())
                .subscribe(this::updateVideo, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    private void updateVideo(PlayVideo playVideo) {
        getViewState().playOrAddNewVideo(playVideo);
    }

    public void loadNextEpisode() {
        currentEpisode += 1;
        videoId = Constants.NO_ID;
        if (currentEpisode <= episodesSize) {
            loadVideo();
        }
    }


    public void setPlayData(PlayVideoNavigationData data) {
        this.animeId = data.getAnimeId();
        this.episodesSize = data.getEpisodesSize();
        this.currentEpisode = data.getEpisodeId();
        this.videoId = data.getVideoId();
    }

}
