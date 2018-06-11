package com.gnoemes.shikimoriapp.data.repository.related;

import com.gnoemes.shikimoriapp.entity.related.domain.Related;

import java.util.List;

import io.reactivex.Single;

public interface RelatedRepository {

    Single<List<Related>> getRelatedAnime(long animeId);

    Single<List<Related>> getRelatedManga(long mangaId);
}
