package com.gnoemes.shikimoriapp.data.repository.download;

import com.gnoemes.shikimoriapp.entity.video.domain.DownloadVideo;

import io.reactivex.Completable;

public interface DownloadSource {

    Completable downloadVideo(DownloadVideo video);
}
