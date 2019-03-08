package com.gnoemes.shikimoriapp.domain.anime.similar;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.repository.anime.AnimeRepository;
import com.gnoemes.shikimoriapp.data.repository.manga.MangaRepository;
import com.gnoemes.shikimoriapp.data.repository.ranobe.RanobeRepository;
import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.manga.domain.Manga;
import com.gnoemes.shikimoriapp.utils.rx.RxUtils;
import com.gnoemes.shikimoriapp.utils.rx.SingleErrorHandler;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class SimilarInteractorImpl implements SimilarInteractor {

    private AnimeRepository animeRepository;
    private MangaRepository mangaRepository;
    private RanobeRepository ranobeRepository;
    private SingleErrorHandler singleErrorHandler;
    private RxUtils rxUtils;

    @Inject
    public SimilarInteractorImpl(@NonNull AnimeRepository animeRepository,
                                 @NonNull MangaRepository mangaRepository,
                                 @NonNull RanobeRepository ranobeRepository,
                                 @NonNull SingleErrorHandler singleErrorHandler,
                                 @NonNull RxUtils rxUtils) {
        this.animeRepository = animeRepository;
        this.mangaRepository = mangaRepository;
        this.ranobeRepository = ranobeRepository;
        this.singleErrorHandler = singleErrorHandler;
        this.rxUtils = rxUtils;
    }

    @Override
    public Single<List<Anime>> getSimilarAnimes(long animeId) {
        return animeRepository.getSimilarAnimes(animeId)
                .compose((SingleErrorHandler<List<Anime>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }

    @Override
    public Single<List<Manga>> getSimilarMangas(long mangaId) {
        return mangaRepository.getSimilar(mangaId)
                .compose((SingleErrorHandler<List<Manga>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }

    @Override
    public Single<List<Manga>> getSimilarRanobe(long ranobeId) {
        return ranobeRepository.getSimilar(ranobeId)
                .compose((SingleErrorHandler<List<Manga>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }
}
