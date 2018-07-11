package com.gnoemes.shikimoriapp.presentation.presenter.player;

import android.text.TextUtils;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.domain.anime.series.SeriesInteractor;
import com.gnoemes.shikimoriapp.entity.series.domain.PlayVideo;
import com.gnoemes.shikimoriapp.entity.series.presentation.PlayVideoNavigationData;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.view.player.WebPlayerView;

@InjectViewState
public class WebPlayerPresenter extends BaseNetworkPresenter<WebPlayerView> {

    private SeriesInteractor interactor;

    private PlayVideo currentVideo;

    private long animeid;
    private int currentEpisode;
    private int episodesSize;
    private long videoId;

    public WebPlayerPresenter(SeriesInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void initData() {
        loadVideo();
    }

    private void loadVideo() {
//        getViewState().onShowLoading();
//        Disposable disposable = interactor.getVideo(animeid, currentEpisode, videoId)
//                .doOnEvent((playVideo, throwable) -> getViewState().onHideLoading())
//                .map(PlayVideo::getUrl)
//                .subscribe(this::updateVideo, this::processErrors);
//
//        unsubscribeOnDestroy(disposable);
    }

    private void updateVideo(String url) {
        if (TextUtils.isEmpty(url)) {
            getViewState().showError();
        } else {
            getViewState().playVideo(url);
        }
    }

    public void setPlayData(PlayVideoNavigationData data) {
        this.animeid = data.getAnimeId();
        this.currentEpisode = data.getEpisodeId();
        this.episodesSize = data.getEpisodesSize();
        this.videoId = data.getVideoId();
    }
}
