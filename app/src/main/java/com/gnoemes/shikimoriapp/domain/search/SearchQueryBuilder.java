package com.gnoemes.shikimoriapp.domain.search;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;

public interface SearchQueryBuilder {

    Single<Map<String, String>> createQueryFromFilters(Map<String, List<FilterItem>> filters, int page, int limit);

    Single<Map<String, String>> createQueryFromIds(LinkedHashSet<Long> ids, @Nullable String searchQuery, int page, int limit);
}
