package com.gnoemes.shikimoriapp.domain.search

import com.gnoemes.shikimoriapp.entity.main.domain.Constants
import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem
import io.reactivex.Single

interface SearchQueryBuilder {

    fun createQueryFromFilters(filters: Map<String, MutableList<FilterItem>>?, page: Int, limit: Int): Single<Map<String, String>>

    fun createQueryFromIds(ids: MutableCollection<Long>, searchQuery: String? = null, page: Int = 1, limit: Int = Constants.MAX_LIMIT): Single<Map<String, String>>

    fun createMyListQueryFromIds(ids: MutableCollection<Long>, searchQuery: String? = null, page: Int = 1, limit: Int = Constants.MAX_LIMIT): Single<Map<String, String>>
}