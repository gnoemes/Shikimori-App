package com.gnoemes.shikimoriapp.domain.anime;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.repository.anime.repository.AnimeRepository;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeDetails;
import com.gnoemes.shikimoriapp.utils.rx.RxUtils;
import com.gnoemes.shikimoriapp.utils.rx.SingleErrorHandler;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Interface implementation of anime interacotr
 */
public class AnimeDetailsInteractorImpl implements AnimeDetailsInteractor {

    private AnimeRepository animeRepository;
    private SingleErrorHandler singleErrorHandler;
    private RxUtils rxUtils;

    @Inject
    public AnimeDetailsInteractorImpl(@NonNull AnimeRepository animeRepository,
                                      @NonNull SingleErrorHandler singleErrorHandler,
                                      @NonNull RxUtils rxUtils) {
        this.animeRepository = animeRepository;
        this.singleErrorHandler = singleErrorHandler;
        this.rxUtils = rxUtils;
    }

    /**
     * Load full anime info by id
     */
    @Override
    public Single<AnimeDetails> loadAnimeDetails(long animeId) {
        return animeRepository.getAnimeDetails(animeId)
                .compose((SingleErrorHandler<AnimeDetails>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }
}
