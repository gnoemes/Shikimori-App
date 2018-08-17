package com.gnoemes.shikimoriapp.domain.download;

import com.gnoemes.shikimoriapp.entity.series.domain.PlayVideo;

import io.reactivex.Completable;

public interface DownloadInteractor {

    Completable downloadVideo(PlayVideo video);

    Completable downloadFromSmotretAnime(long id);
}
