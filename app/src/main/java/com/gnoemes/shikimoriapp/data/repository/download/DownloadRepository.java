package com.gnoemes.shikimoriapp.data.repository.download;

import com.gnoemes.shikimoriapp.entity.series.domain.PlayVideo;

import io.reactivex.Completable;

public interface DownloadRepository {

    Completable downloadVideo(PlayVideo video);

    Completable downloadFromSmotretAnime(long translationId);
}
