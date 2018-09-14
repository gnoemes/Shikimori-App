package com.gnoemes.shikimoriapp.presentation.view.search.filter

import android.content.Context
import com.gnoemes.shikimoriapp.R
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeType
import com.gnoemes.shikimoriapp.entity.anime.domain.Genre
import com.gnoemes.shikimoriapp.entity.anime.domain.Status
import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem
import com.gnoemes.shikimoriapp.entity.search.domain.SearchConstants
import javax.inject.Inject

class FilterResourceProviderImpl @Inject constructor(
        private val context: Context,
        private val converter: FilterResourceConverter
) : FilterResourceProvider {

    override fun getAnimeFilters(): HashMap<String, MutableList<FilterItem>> {
        val filters = HashMap<String, MutableList<FilterItem>>()

        filters[getGenreString()] = converter.convertAnimeFilters(getList(R.array.genres), getList(R.array.genres_names), Genre.values())
        filters[getStatusString()] = converter.convertAnimeFilters(getList(R.array.statuses), getList(R.array.statuses_names), Status.values())
        filters[getTypeString()] = converter.convertAnimeFilters(getList(R.array.anime_types), getList(R.array.anime_types_name), AnimeType.values())
        filters[getDurationString()] = converter.convertAnimeFilters(getList(R.array.duration), getList(R.array.duration_names), SearchConstants.DURATIONS.values())

        return filters
    }

    override fun getMangaFilters(): HashMap<String, MutableList<FilterItem>> {
        val filters = HashMap<String, MutableList<FilterItem>>()

        filters[getGenreString()] = converter.convertMangaFilters(getList(R.array.genres), getList(R.array.genres_names), Genre.values())
        filters[getStatusString()] = converter.convertMangaFilters(getList(R.array.statuses), getList(R.array.statuses_names), Status.values())
        filters[getTypeString()] = converter.convertMangaFilters(getList(R.array.manga_types), getList(R.array.manga_types_name), AnimeType.values())

        return filters
    }

    override fun getRanobeFilters(): HashMap<String, MutableList<FilterItem>> {
        val filters = HashMap<String, MutableList<FilterItem>>()

        filters[getGenreString()] = converter.convertMangaFilters(getList(R.array.genres), getList(R.array.genres_names), Genre.values())
        filters[getStatusString()] = converter.convertMangaFilters(getList(R.array.statuses), getList(R.array.statuses_names), Status.values())

        return filters
    }

    private fun getDurationString(): String = context.getString(R.string.common_duration)
    private fun getStatusString(): String = context.getString(R.string.common_status)
    private fun getTypeString(): String = context.getString(R.string.common_type)
    private fun getGenreString(): String = context.getString(R.string.common_genre)


    private fun getList(arrayRes: Int): MutableList<String> {
        return context.resources.getStringArray(arrayRes).toMutableList()
    }
}