package com.gnoemes.shikimoriapp.presentation.presenter.player;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.domain.anime.series.SeriesInteractor;
import com.gnoemes.shikimoriapp.entity.app.domain.BaseException;
import com.gnoemes.shikimoriapp.entity.app.domain.ContentException;
import com.gnoemes.shikimoriapp.entity.main.domain.Constants;
import com.gnoemes.shikimoriapp.entity.series.domain.PlayVideo;
import com.gnoemes.shikimoriapp.entity.series.domain.VideoTrack;
import com.gnoemes.shikimoriapp.entity.series.presentation.PlayVideoNavigationData;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.player.provider.EmbeddedPlayerResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.player.embedded.EmbeddedPlayerView;

import java.util.HashSet;
import java.util.Set;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class EmbeddedPlayerPresenter extends BaseNetworkPresenter<EmbeddedPlayerView> {

    private SeriesInteractor seriesInteractor;
    private EmbeddedPlayerResourceProvider resourceProvider;

    private long animeId;
    private int currentEpisode;
    private long videoId;
    private int episodesSize;
    private long rateId;

    private int currentTrack;
    private Set<PlayVideo> videos = new HashSet<>();

    public EmbeddedPlayerPresenter(SeriesInteractor seriesInteractor,
                                   EmbeddedPlayerResourceProvider resourceProvider) {
        this.seriesInteractor = seriesInteractor;
        this.resourceProvider = resourceProvider;
    }

    @Override
    public void initData() {
        super.initData();
        loadVideo();
        updateControls();
    }

    /**
     * Enables or disables buttons
     */
    private void updateControls() {
        if (currentEpisode < episodesSize) {
            getViewState().enableNextButton();
        } else {
            getViewState().disableNextButton();
        }

        if (currentEpisode > 1) {
            getViewState().enablePrevButton();
        } else {
            getViewState().disablePrevButton();
        }
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
        videos.add(playVideo);

        if (playVideo.getTracks() != null && !playVideo.getTracks().isEmpty()) {
            VideoTrack track = playVideo.getTracks().get(currentTrack);
            if (track != null) {
                getViewState().updateInformation(playVideo, currentTrack);
                getViewState().playVideo(track, true);
                currentTrack = 0;
                setEpisodeWatched();
            }
        } else {
            getViewState().exitWithSystemMessage(resourceProvider.getNetworkError());

        }
    }

    private void updateVideo(PlayVideo playVideo, boolean isNeedReset) {
        videos.add(playVideo);

        if (playVideo.getTracks() != null && !playVideo.getTracks().isEmpty()) {
            VideoTrack track = playVideo.getTracks().get(currentTrack);
            if (track != null) {
                getViewState().updateInformation(playVideo, currentTrack);
                getViewState().playVideo(track, isNeedReset);
                currentTrack = 0;
            }
        }
    }

    public void onAlternativeSource() {
//        if (currentVideo.getHosting() == VideoHosting.SIBNET) {
//            if (currentVideo.getTracks() != null && currentVideo.getTracks().size() == 2) {
////                getViewState().playVideo(currentVideo, 1);
//            }
//        }
    }

    public void loadNextEpisode() {
        currentEpisode += 1;
        videoId = Constants.NO_ID;
        if (currentEpisode <= episodesSize) {
            loadOrUpdateVideo();
        }
        updateControls();
    }

    public void loadPrevEpisode() {
        currentEpisode -= 1;
        videoId = Constants.NO_ID;
        if (currentEpisode > 0) {
            loadOrUpdateVideo();
        }
        updateControls();
    }

    private void loadOrUpdateVideo() {
        PlayVideo video = getVideoByEpisode(currentEpisode);
        if (video == null) {
            loadVideo();
        } else {
            updateVideo(video);
        }
    }

    private void setEpisodeWatched() {
        Disposable disposable;
        if (rateId != Constants.NO_ID) {
            disposable = seriesInteractor.setEpisodeWatched(animeId, currentEpisode, rateId)
                    .subscribe(() -> {
                    }, this::processErrors);

        } else {
            disposable = seriesInteractor.setEpisodeWatched(animeId, currentEpisode)
                    .subscribe(() -> {
                    }, this::processErrors);
        }
        unsubscribeOnDestroy(disposable);
    }

    public void onResolutionChanged(int newResolution) {
        PlayVideo video = getVideoByEpisode(currentEpisode);

        if (video != null && video.getTracks() != null) {
            int i = 0;
            for (VideoTrack track : video.getTracks()) {
                if (track.getResolution() == newResolution) {
                    currentTrack = i;
                    updateVideo(video, false);
                    break;
                }
                i++;
            }
        }
    }

    private PlayVideo getVideoByEpisode(int currentEpisode) {
        for (PlayVideo playVideo : videos) {
            if (playVideo.getEpisodeId() == currentEpisode) {
                return playVideo;
            }
        }
        return null;
    }

    @Override
    protected void processErrors(Throwable throwable) {
        switch (((BaseException) throwable).getTag()) {
            case ContentException.TAG:
                onHostingError();
                break;
            default:
                super.processErrors(throwable);
        }
    }

    private void onHostingError() {
        getViewState().showSystemMessage(resourceProvider.getHostingError());
        getViewState().disableNextButton();
        getViewState().disablePrevButton();
    }

    public void onNetworkError() {
        getViewState().showSystemMessage(resourceProvider.getNetworkError());
    }

    public void setPlayData(PlayVideoNavigationData data) {
        this.animeId = data.getAnimeId();
        this.episodesSize = data.getEpisodesSize();
        this.currentEpisode = data.getEpisodeId();
        this.videoId = data.getVideoId();
        this.rateId = data.getRateId();
    }
}
