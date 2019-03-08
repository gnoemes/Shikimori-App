package com.gnoemes.shikimoriapp.data.repository.download.converter;

import com.gnoemes.shikimoriapp.entity.anime.series.data.network.TranslationResponse;
import com.gnoemes.shikimoriapp.entity.video.domain.DownloadVideo;

import javax.inject.Inject;

public class SmotretAnimeDownloadConveterImpl implements SmotretAnimeDownloadConverter {

    @Inject
    public SmotretAnimeDownloadConveterImpl() {
    }

    @Override
    public DownloadVideo apply(TranslationResponse translationResponse) {
        String fileName = translationResponse.getEpisodeResponse().getEpisodeFull();
        String animeName = translationResponse.getSeriesResponses().getTitlesResponse().getRu();
        String link = String.format("https://smotret-anime.ru/translations/mp4/%d?format=mp4", translationResponse.getId());
        return new DownloadVideo(fileName, animeName, link);
    }
}
