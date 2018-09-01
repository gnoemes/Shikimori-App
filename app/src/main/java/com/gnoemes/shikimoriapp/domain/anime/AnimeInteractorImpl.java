package com.gnoemes.shikimoriapp.domain.anime;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.repository.anime.AnimeRepository;
import com.gnoemes.shikimoriapp.data.repository.screenshots.ScreenshotRepository;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeDetails;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeFranchiseNode;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeLink;
import com.gnoemes.shikimoriapp.entity.screenshots.domain.Screenshot;
import com.gnoemes.shikimoriapp.utils.rx.RxUtils;
import com.gnoemes.shikimoriapp.utils.rx.SingleErrorHandler;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Interface implementation of anime interacotr
 */
public class AnimeInteractorImpl implements AnimeInteractor {

    private AnimeRepository animeRepository;
    private ScreenshotRepository screenshotRepository;
    private SingleErrorHandler singleErrorHandler;
    private RxUtils rxUtils;

    @Inject
    public AnimeInteractorImpl(@NonNull AnimeRepository animeRepository,
                               @NonNull ScreenshotRepository screenshotRepository,
                               @NonNull SingleErrorHandler singleErrorHandler,
                               @NonNull RxUtils rxUtils) {
        this.animeRepository = animeRepository;
        this.screenshotRepository = screenshotRepository;
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


    @Override
    public Single<List<AnimeLink>> getAnimeLinks(long animeId) {
        return animeRepository.getAnimeLinks(animeId)
                .compose((SingleErrorHandler<List<AnimeLink>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }

    @Override
    public Single<List<AnimeFranchiseNode>> getFranchiseNodes(long animeId) {
        return animeRepository.getFranchiseNodes(animeId)
                .compose((SingleErrorHandler<List<AnimeFranchiseNode>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }

    @Override
    public Single<List<Screenshot>> getScreenshots(long animeId) {
        return screenshotRepository.getAnimeScreenshots(animeId)
                .compose((SingleErrorHandler<List<Screenshot>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }
}
