package com.gnoemes.shikimoriapp.data.repository.anime.repository;

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeDetails;

import io.reactivex.Single;

public interface AnimeRepository {

    /**
     * Anime details
     */
    Single<AnimeDetails> getAnimeDetails(long animeId);
}
