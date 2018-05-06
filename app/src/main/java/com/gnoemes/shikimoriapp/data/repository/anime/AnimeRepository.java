package com.gnoemes.shikimoriapp.data.repository.anime;

import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeDetails;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeLink;

import java.util.List;

import io.reactivex.Single;

public interface AnimeRepository {

    /**
     * Anime details
     */
    Single<AnimeDetails> getAnimeDetails(long animeId);

    /**
     * Anime Links
     */
    Single<List<AnimeLink>> getAnimeLinks(long animeId);

    /**
     * Similar anime
     */
    Single<List<Anime>> getSimilarAnimes(long animeId);

}
