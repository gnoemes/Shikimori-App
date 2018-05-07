package com.gnoemes.shikimoriapp.domain.anime.similar;

import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;

import java.util.List;

import io.reactivex.Single;

public interface SimilarAnimeInteractor {

    Single<List<Anime>> getSimilarAnimes(long animeId);
}
