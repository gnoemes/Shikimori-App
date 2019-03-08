package com.gnoemes.shikimoriapp.data.repository.download;

import com.gnoemes.shikimoriapp.data.network.AlternativeApi;
import com.gnoemes.shikimoriapp.data.repository.download.converter.PlayVideoToDownloadConverter;
import com.gnoemes.shikimoriapp.data.repository.download.converter.SmotretAnimeDownloadConverter;
import com.gnoemes.shikimoriapp.entity.anime.series.data.network.TranslationResponseData;
import com.gnoemes.shikimoriapp.entity.series.domain.PlayVideo;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class DownloadRepositoryImpl implements DownloadRepository {

    private AlternativeApi api;
    private DownloadSource source;
    private SmotretAnimeDownloadConverter smotretAnimeDownloadConverter;
    private PlayVideoToDownloadConverter downloadConverter;

    @Inject
    public DownloadRepositoryImpl(AlternativeApi api,
                                  DownloadSource source,
                                  SmotretAnimeDownloadConverter smotretAnimeDownloadConverter,
                                  PlayVideoToDownloadConverter downloadConverter) {
        this.api = api;
        this.source = source;
        this.smotretAnimeDownloadConverter = smotretAnimeDownloadConverter;
        this.downloadConverter = downloadConverter;
    }

    @Override
    public Completable downloadVideo(PlayVideo video) {
        return Single.fromCallable(() -> video)
                .map(downloadConverter)
                .flatMapCompletable(source::downloadVideo);
    }

    @Override
    public Completable downloadFromSmotretAnime(long translationId) {
        return api.getTranslation(translationId)
                .map(TranslationResponseData::getResponse)
                .map(smotretAnimeDownloadConverter)
                .flatMapCompletable(source::downloadVideo);
    }
}
