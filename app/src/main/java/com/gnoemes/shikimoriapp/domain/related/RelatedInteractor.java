package com.gnoemes.shikimoriapp.domain.related;

import com.gnoemes.shikimoriapp.entity.related.domain.Related;

import java.util.List;

import io.reactivex.Single;

public interface RelatedInteractor {

    Single<List<Related>> getRelatedAnime(long animeId);

    Single<List<Related>> getRelatedManga(long mangaId);

    Single<List<Related>> getRelatedRanobe(long ranbodeId);
}
