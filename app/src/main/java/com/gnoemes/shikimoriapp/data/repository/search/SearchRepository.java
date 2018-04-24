package com.gnoemes.shikimoriapp.data.repository.search;

import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;

public interface SearchRepository {

    Single<List<Anime>> getAnimeList(Map<String, String> queryMap);

}
