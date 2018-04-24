package com.gnoemes.shikimoriapp.domain.search;

import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;

public interface SearchQueryBuilder {

    Single<Map<String, String>> createQueryFromFilters(HashMap<String, List<FilterItem>> filters, int page, int limit);

    Single<Map<String, String>> createSearchQuery(String search, int page, int limit);
}
