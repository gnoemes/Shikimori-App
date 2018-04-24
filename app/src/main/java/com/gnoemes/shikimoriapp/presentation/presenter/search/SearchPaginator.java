package com.gnoemes.shikimoriapp.presentation.presenter.search;

import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem;
import com.gnoemes.shikimoriapp.presentation.presenter.common.Paginator;

import java.util.HashMap;
import java.util.List;

public interface SearchPaginator extends Paginator {

    void setFilters(HashMap<String, List<FilterItem>> filters);

    boolean isFirstPage();
}
