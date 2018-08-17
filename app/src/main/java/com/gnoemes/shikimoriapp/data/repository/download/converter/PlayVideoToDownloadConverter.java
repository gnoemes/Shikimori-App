package com.gnoemes.shikimoriapp.data.repository.download.converter;

import com.gnoemes.shikimoriapp.entity.series.domain.PlayVideo;
import com.gnoemes.shikimoriapp.entity.video.domain.DownloadVideo;

import io.reactivex.functions.Function;

public interface PlayVideoToDownloadConverter extends Function<PlayVideo, DownloadVideo> {
}
