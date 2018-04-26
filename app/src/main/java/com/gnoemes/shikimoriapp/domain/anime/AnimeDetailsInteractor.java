package com.gnoemes.shikimoriapp.domain.anime;

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeDetails;

import io.reactivex.Single;

/**
 * Interface of interactor for anime
 */
public interface AnimeDetailsInteractor {

    Single<AnimeDetails> loadAnimeDetails(long animeId);
}
