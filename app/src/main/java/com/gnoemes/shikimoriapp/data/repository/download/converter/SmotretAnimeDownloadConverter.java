package com.gnoemes.shikimoriapp.data.repository.download.converter;

import com.gnoemes.shikimoriapp.entity.anime.series.data.network.TranslationResponse;
import com.gnoemes.shikimoriapp.entity.video.domain.DownloadVideo;

import io.reactivex.functions.Function;

public interface SmotretAnimeDownloadConverter extends Function<TranslationResponse, DownloadVideo> {


}
