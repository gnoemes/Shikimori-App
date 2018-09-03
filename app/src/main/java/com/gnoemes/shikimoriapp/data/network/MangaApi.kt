package com.gnoemes.shikimoriapp.data.network

import com.gnoemes.shikimoriapp.entity.anime.data.FranchiseResponse
import com.gnoemes.shikimoriapp.entity.anime.data.LinkResponse
import com.gnoemes.shikimoriapp.entity.manga.data.MangaDetailsResponse
import com.gnoemes.shikimoriapp.entity.manga.data.MangaResponse
import com.gnoemes.shikimoriapp.entity.related.data.RelatedResponse
import com.gnoemes.shikimoriapp.entity.roles.data.RolesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface MangaApi {

    @GET("/api/mangas")
    fun getList(@QueryMap(encoded = true) filter: Map<String, String>): Single<List<MangaResponse>>

    @GET("/api/mangas/{id}")
    fun getDetails(@Path("id") id: Long): Single<MangaDetailsResponse>

    @GET("/api/mangas/{id}/roles")
    fun getRoles(@Path("id") id: Long): Single<List<RolesResponse>>

    @GET("/api/mangas/{id}/similar")
    fun getSimilar(@Path("id") id: Long): Single<List<MangaResponse>>

    @GET("/api/mangas/{id}/related")
    fun getRelated(@Path("id") id: Long): Single<List<RelatedResponse>>

    @GET("/api/mangas/{id}/franchise")
    fun getFranchise(@Path("id") id: Long): Single<FranchiseResponse>

    @GET("/api/mangas/{id}/external_links")
    fun getLinks(@Path("id") id: Long): Single<List<LinkResponse>>
}