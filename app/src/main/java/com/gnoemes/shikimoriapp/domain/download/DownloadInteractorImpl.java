package com.gnoemes.shikimoriapp.domain.download;

import com.gnoemes.shikimoriapp.data.repository.download.DownloadRepository;
import com.gnoemes.shikimoriapp.entity.series.domain.PlayVideo;
import com.gnoemes.shikimoriapp.entity.series.domain.VideoHosting;
import com.gnoemes.shikimoriapp.utils.SmotretAnimeUtils;
import com.gnoemes.shikimoriapp.utils.rx.CompletableErrorHandler;
import com.gnoemes.shikimoriapp.utils.rx.RxUtils;

import javax.inject.Inject;

import io.reactivex.Completable;

public class DownloadInteractorImpl implements DownloadInteractor {

    private DownloadRepository repository;
    private CompletableErrorHandler errorHandler;
    private RxUtils rxUtils;

    @Inject
    public DownloadInteractorImpl(DownloadRepository repository,
                                  CompletableErrorHandler errorHandler,
                                  RxUtils rxUtils) {
        this.repository = repository;
        this.errorHandler = errorHandler;
        this.rxUtils = rxUtils;
    }

    @Override
    public Completable downloadVideo(PlayVideo video) {
        if (video.getHosting() == VideoHosting.SMOTRET_ANIME) {
            return downloadFromSmotretAnime(SmotretAnimeUtils.getIdFromEmbeddedPlayer(video.getSourceUrl()));
        } else {
            return repository.downloadVideo(video)
                    .compose(errorHandler)
                    .compose(rxUtils.applyCompleteSchedulers());
        }
    }

    @Override
    public Completable downloadFromSmotretAnime(long translationId) {
        return repository.downloadFromSmotretAnime(translationId)
                .compose(errorHandler)
                .compose(rxUtils.applyCompleteSchedulers());
    }
}
