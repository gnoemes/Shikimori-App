package com.gnoemes.shikimoriapp.presentation.view.search.filter

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeType
import com.gnoemes.shikimoriapp.entity.anime.domain.Genre
import com.gnoemes.shikimoriapp.entity.anime.domain.Status
import com.gnoemes.shikimoriapp.entity.manga.domain.MangaType
import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem
import com.gnoemes.shikimoriapp.entity.search.domain.SearchConstants
import javax.inject.Inject

class FilterResourceConverterImpl @Inject constructor(
) : FilterResourceConverter {

    override fun <T> convertMangaFilters(values: MutableList<String>, names: MutableList<String>, type: Array<T>): MutableList<FilterItem> {
        return values.zip(names)
                .map { (name, value) -> FilterItem(processAction(type)!!, processValue(value, type, false), name) }
                .toMutableList()
    }

    override fun <T> convertAnimeFilters(values: MutableList<String>, names: MutableList<String>, type: Array<T>): MutableList<FilterItem> {
        return values.zip(names)
                .map { (name, value) -> FilterItem(processAction(type)!!, processValue(value, type), name) }
                .toMutableList()
    }

    private fun processAction(type: Array<*>): String? {
        return if (isGenre(type)) {
            SearchConstants.GENRE
        } else if (isAnimeType(type) || isMangaType(type)) {
            SearchConstants.TYPE
        } else if (isStatus(type)) {
            SearchConstants.STATUS
        } else if (isSeason(type)) {
            SearchConstants.SEASON
        } else if (isOrder(type)) {
            SearchConstants.ORDER
        } else if (isDuration(type)) {
            SearchConstants.DURATION
        } else {
            null
        }
    }

    private fun processValue(typeName: String, type: Array<*>, isAnime: Boolean = true): String? {
        return when {
            isGenre(type) -> {
                val genre = Genre.values().find { it.equalsName(typeName) }
                if (isAnime) genre?.animeId else genre?.mangaId
            }
            isAnimeType(type) -> AnimeType.values().find { it.equalsType(typeName) }?.toString()
            isMangaType(type) -> MangaType.values().find { it.type == typeName }?.toString()
            isStatus(type) -> Status.values().find { it.equalsStatus(typeName) }?.toString()
            isSeason(type) -> SearchConstants.SEASONS.values().find { it.equalsSeason(typeName) }?.toString()
            isOrder(type) -> SearchConstants.ORDER_BY.values().find { it.equalsType(typeName) }?.toString()
            isDuration(type) -> SearchConstants.DURATIONS.values().find { it.equalsDuration(typeName) }?.toString()
            else -> null
        }
    }

    private fun isGenre(enums: Array<*>): Boolean = enums.isArrayOf<Genre>()
    private fun isAnimeType(enums: Array<*>): Boolean = enums.isArrayOf<AnimeType>()
    private fun isMangaType(enums: Array<*>): Boolean = enums.isArrayOf<MangaType>()
    private fun isStatus(enums: Array<*>): Boolean = enums.isArrayOf<Status>()
    private fun isSeason(enums: Array<*>): Boolean = enums.isArrayOf<SearchConstants.SEASONS>()
    private fun isOrder(enums: Array<*>): Boolean = enums.isArrayOf<SearchConstants.ORDER_BY>()
    private fun isDuration(enums: Array<*>): Boolean = enums.isArrayOf<SearchConstants.DURATIONS>()
}