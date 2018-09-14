package com.gnoemes.shikimoriapp.presentation.view.search.filter;

import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem;

import java.util.HashMap;
import java.util.List;

public interface FilterResourceProvider {

    HashMap<String, List<FilterItem>> getAnimeFilters();

    HashMap<String, List<FilterItem>> getMangaFilters();

    HashMap<String, List<FilterItem>> getRanobeFilters();
}
