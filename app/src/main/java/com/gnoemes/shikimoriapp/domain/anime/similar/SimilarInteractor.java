package com.gnoemes.shikimoriapp.domain.anime.similar;

import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.manga.domain.Manga;

import java.util.List;

import io.reactivex.Single;

public interface SimilarInteractor {

    Single<List<Anime>> getSimilarAnimes(long animeId);

    Single<List<Manga>> getSimilarMangas(long mangaId);

    Single<List<Manga>> getSimilarRanobe(long ranobeId);
}
