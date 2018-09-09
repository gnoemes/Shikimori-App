package com.gnoemes.shikimoriapp.data.repository.search

import com.gnoemes.shikimoriapp.data.network.AnimesApi
import com.gnoemes.shikimoriapp.data.network.MangaApi
import com.gnoemes.shikimoriapp.data.network.RanobeApi
import com.gnoemes.shikimoriapp.data.network.RolesApi
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeListResponseConverter
import com.gnoemes.shikimoriapp.data.repository.manga.converter.MangaResponseConverter
import com.gnoemes.shikimoriapp.data.repository.roles.converter.CharacterResponseConverter
import com.gnoemes.shikimoriapp.data.repository.roles.converter.PersonResponseConverter
import com.gnoemes.shikimoriapp.entity.anime.domain.Anime
import com.gnoemes.shikimoriapp.entity.manga.domain.Manga
import com.gnoemes.shikimoriapp.entity.roles.domain.Character
import com.gnoemes.shikimoriapp.entity.roles.domain.Person
import io.reactivex.Single
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
        private val animesApi: AnimesApi,
        private val mangaApi: MangaApi,
        private val ranobeApi: RanobeApi,
        private val rolesApi: RolesApi,
        private val animeResponseConverter: AnimeListResponseConverter,
        private val mangaResponseConverter: MangaResponseConverter,
        private val characterResponseConverter: CharacterResponseConverter,
        private val personResponseConverter: PersonResponseConverter
) : SearchRepository {

    override fun getAnimeList(queryMap: Map<String, String>): Single<List<Anime>> =
            animesApi.getAnimeList(queryMap)
                    .map(animeResponseConverter)

    override fun getMangaList(queryMap: Map<String, String>): Single<List<Manga>> =
            mangaApi.getList(queryMap)
                    .map(mangaResponseConverter)

    override fun getRanobeList(queryMap: Map<String, String>): Single<List<Manga>> =
            ranobeApi.getList(queryMap)
                    .map(mangaResponseConverter)

    override fun getCharacterList(queryMap: Map<String, String>): Single<List<Character>> =
            rolesApi.getCharacterList(queryMap)
                    .map(characterResponseConverter)

    override fun getPersonList(queryMap: Map<String, String>): Single<List<Person>> =
            rolesApi.getPersonList(queryMap)
                    .map(personResponseConverter)
}