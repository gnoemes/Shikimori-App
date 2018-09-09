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
                    .compose(errorHandler.handleErrors<List<Anime>>())
                    .compose(rxUtils.applySingleSchedulers())

    override fun loadMangaList(page: Int, limit: Int): Single<List<Manga>> =
            queryBuilder.createQueryFromFilters(null, page, limit)
                    .flatMap { repository.getMangaList(it) }
                    .compose(errorHandler.handleErrors<List<Manga>>())
                    .compose(rxUtils.applySingleSchedulers())

    override fun loadRanobeList(page: Int, limit: Int): Single<List<Manga>> =
            queryBuilder.createQueryFromFilters(null, page, limit)
                    .flatMap { repository.getMangaList(it) }
                    .compose(errorHandler.handleErrors<List<Manga>>())
                    .compose(rxUtils.applySingleSchedulers())

    override fun loadCharacterListWithFilters(filters: HashMap<String, List<FilterItem>>?, page: Int, limit: Int): Single<List<Character>> =
            queryBuilder.createQueryFromFilters(filters, page, limit)
                    .flatMap { repository.getCharacterList(it) }
                    .compose(errorHandler.handleErrors<List<Character>>())
                    .compose(rxUtils.applySingleSchedulers())

    override fun loadPersonListWithFilters(filters: HashMap<String, List<FilterItem>>?, page: Int, limit: Int): Single<List<Person>> =
            queryBuilder.createQueryFromFilters(filters, page, limit)
                    .flatMap { repository.getPersonList(it) }
                    .compose(errorHandler.handleErrors<List<Person>>())
                    .compose(rxUtils.applySingleSchedulers())

    override fun loadAnimeListWithFilters(filters: HashMap<String, List<FilterItem>>?, page: Int, limit: Int): Single<List<Anime>> =
            queryBuilder.createQueryFromFilters(filters, page, limit)
                    .flatMap { repository.getAnimeList(it) }
                    .compose(errorHandler.handleErrors<List<Anime>>())
                    .compose(rxUtils.applySingleSchedulers())

    override fun loadMangaListWithFilters(filters: HashMap<String, List<FilterItem>>?, page: Int, limit: Int): Single<List<Manga>> =
            queryBuilder.createQueryFromFilters(filters, page, limit)
                    .flatMap { repository.getMangaList(it) }
                    .compose(errorHandler.handleErrors<List<Manga>>())
                    .compose(rxUtils.applySingleSchedulers())

    override fun loadRanobeListWithFilters(filters: HashMap<String, List<FilterItem>>?, page: Int, limit: Int): Single<List<Manga>> =
            queryBuilder.createQueryFromFilters(filters, page, limit)
                    .flatMap { repository.getRanobeList(it) }
                    .compose(errorHandler.handleErrors<List<Manga>>())
                    .compose(rxUtils.applySingleSchedulers())
}