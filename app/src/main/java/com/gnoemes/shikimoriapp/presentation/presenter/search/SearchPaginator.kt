package com.gnoemes.shikimoriapp.presentation.presenter.search

import com.gnoemes.shikimoriapp.entity.app.domain.Type
import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem
import com.gnoemes.shikimoriapp.presentation.presenter.common.Paginator

interface SearchPaginator : Paginator {
    fun setFilters(filters: HashMap<String, MutableList<FilterItem>>)
    fun setType(type: Type)
    fun isFirstPage(): Boolean
}