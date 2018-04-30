package com.gnoemes.shikimoriapp.domain.anime;

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeDetails;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeLink;

import java.util.List;

import io.reactivex.Single;

/**
 * Interface of interactor for anime
 */
public interface AnimeInteractor {

    Single<AnimeDetails> loadAnimeDetails(long animeId);

    Single<List<AnimeLink>> getAnimeLinks(long animeId);
}
