package com.gnoemes.shikimoriapp.domain.search;

import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Single;

public interface SearchInteractor {

    /**
     * Load anime list with filters
     */
    Single<List<Anime>> loadListWithFilters(HashMap<String, List<FilterItem>> filters, int page, int limit);

    /**
     * Load anime list with default filters
     */
    Single<List<Anime>> loadList(int page, int limit);
}
