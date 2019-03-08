package com.gnoemes.shikimoriapp.presentation.view.search.filter

import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem

interface FilterResourceConverter {

    fun <T> convertMangaFilters(values: MutableList<String>, names: MutableList<String>, type: Array<T>): MutableList<FilterItem>

    fun <T> convertAnimeFilters(values: MutableList<String>, names: MutableList<String>, type: Array<T>): MutableList<FilterItem>
}