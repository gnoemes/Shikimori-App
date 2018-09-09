package com.gnoemes.shikimoriapp.domain.search

import com.gnoemes.shikimoriapp.entity.anime.domain.Anime
import com.gnoemes.shikimoriapp.entity.app.data.AppConfig
import com.gnoemes.shikimoriapp.entity.manga.domain.Manga
import com.gnoemes.shikimoriapp.entity.roles.domain.Character
import com.gnoemes.shikimoriapp.entity.roles.domain.Person
import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem
import io.reactivex.Single

interface SearchInteractor {

    fun loadAnimeList(page: Int, limit: Int = AppConfig.DEFAULT_LIMIT): Single<List<Anime>>

    fun loadMangaList(page: Int, limit: Int = AppConfig.DEFAULT_LIMIT): Single<List<Manga>>

    fun loadRanobeList(page: Int, limit: Int = AppConfig.DEFAULT_LIMIT): Single<List<Manga>>

    fun loadCharacterListWithFilters(filters: HashMap<String, List<FilterItem>>?, page: Int = 0, limit: Int = AppConfig.DEFAULT_LIMIT): Single<List<Character>>

    fun loadPersonListWithFilters(filters: HashMap<String, List<FilterItem>>?, page: Int = 0, limit: Int = AppConfig.DEFAULT_LIMIT): Single<List<Person>>

    fun loadAnimeListWithFilters(filters: HashMap<String, List<FilterItem>>?, page: Int, limit: Int = AppConfig.DEFAULT_LIMIT): Single<List<Anime>>

    fun loadMangaListWithFilters(filters: HashMap<String, List<FilterItem>>?, page: Int, limit: Int = AppConfig.DEFAULT_LIMIT): Single<List<Manga>>

    fun loadRanobeListWithFilters(filters: HashMap<String, List<FilterItem>>?, page: Int, limit: Int = AppConfig.DEFAULT_LIMIT): Single<List<Manga>>
}