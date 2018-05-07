package com.gnoemes.shikimoriapp.domain.anime.similar;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.repository.anime.AnimeRepository;
import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.utils.rx.RxUtils;
import com.gnoemes.shikimoriapp.utils.rx.SingleErrorHandler;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class SimilarAnimeInteractorImpl implements SimilarAnimeInteractor {

    private AnimeRepository animeRepository;
    private SingleErrorHandler singleErrorHandler;
    private RxUtils rxUtils;

    @Inject
    public SimilarAnimeInteractorImpl(@NonNull AnimeRepository animeRepository,
                                      @NonNull SingleErrorHandler singleErrorHandler,
                                      @NonNull RxUtils rxUtils) {
        this.animeRepository = animeRepository;
        this.singleErrorHandler = singleErrorHandler;
        this.rxUtils = rxUtils;
    }

    @Override
    public Single<List<Anime>> getSimilarAnimes(long animeId) {
        return animeRepository.getSimilarAnimes(animeId)
                .compose((SingleErrorHandler<List<Anime>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }
}
