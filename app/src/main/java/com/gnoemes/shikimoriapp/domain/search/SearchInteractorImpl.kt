package com.gnoemes.shikimoriapp.domain.search

import com.gnoemes.shikimoriapp.data.repository.search.SearchRepository
import com.gnoemes.shikimoriapp.entity.anime.domain.Anime
import com.gnoemes.shikimoriapp.entity.manga.domain.Manga
import com.gnoemes.shikimoriapp.entity.roles.domain.Character
import com.gnoemes.shikimoriapp.entity.roles.domain.Person
import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem
import com.gnoemes.shikimoriapp.utils.rx.RxUtils
import com.gnoemes.shikimoriapp.utils.rx.SingleErrorHandler
import io.reactivex.Single
import javax.inject.Inject

class SearchInteractorImpl @Inject constructor(
        private val repository: SearchRepository,
        private val queryBuilder: SearchQueryBuilder,
        private val errorHandler: SingleErrorHandler<Any>,
        private val rxUtils: RxUtils
) : SearchInteractor {

    override fun loadAnimeList(page: Int, limit: Int): Single<List<Anime>> =
            queryBuilder.createQueryFromFilters(null, page, limit)
                    .flatMap { repository.getAnimeList(it) }
                    .composeHandlerAndSchedulers()

    override fun loadMangaList(page: Int, limit: Int): Single<List<Manga>> =
            queryBuilder.createQueryFromFilters(null, page, limit)
                    .flatMap { repository.getMangaList(it) }
                    .composeHandlerAndSchedulers()

    override fun loadRanobeList(page: Int, limit: Int): Single<List<Manga>> =
            queryBuilder.createQueryFromFilters(null, page, limit)
                    .flatMap { repository.getMangaList(it) }
                    .composeHandlerAndSchedulers()

    override fun loadCharacterListWithFilters(filters: HashMap<String, MutableList<FilterItem>>?, page: Int, limit: Int): Single<List<Character>> =
            queryBuilder.createQueryFromFilters(filters, page, limit)
                    .flatMap { repository.getCharacterList(it) }
                    .composeHandlerAndSchedulers()

    override fun loadPersonListWithFilters(filters: HashMap<String, MutableList<FilterItem>>?, page: Int, limit: Int): Single<List<Person>> =
            queryBuilder.createQueryFromFilters(filters, page, limit)
                    .flatMap { repository.getPersonList(it) }
                    .composeHandlerAndSchedulers()

    override fun loadAnimeListWithFilters(filters: HashMap<String, MutableList<FilterItem>>?, page: Int, limit: Int): Single<List<Anime>> =
            queryBuilder.createQueryFromFilters(filters, page, limit)
                    .flatMap { repository.getAnimeList(it) }
                    .composeHandlerAndSchedulers()

    override fun loadMangaListWithFilters(filters: HashMap<String, MutableList<FilterItem>>?, page: Int, limit: Int): Single<List<Manga>> =
            queryBuilder.createQueryFromFilters(filters, page, limit)
                    .flatMap { repository.getMangaList(it) }
                    .composeHandlerAndSchedulers()

    override fun loadRanobeListWithFilters(filters: HashMap<String, MutableList<FilterItem>>?, page: Int, limit: Int): Single<List<Manga>> =
            queryBuilder.createQueryFromFilters(filters, page, limit)
                    .flatMap { repository.getRanobeList(it) }
                    .composeHandlerAndSchedulers()


    private fun <T> Single<T>.composeHandlerAndSchedulers(): Single<T> {
        return this.compose(errorHandler as SingleErrorHandler<T>)
                .compose(rxUtils.applySingleSchedulers())
    }
}