package com.gnoemes.shikimoriapp.domain.manga

import com.gnoemes.shikimoriapp.entity.anime.domain.FranchiseNode
import com.gnoemes.shikimoriapp.entity.anime.domain.Link
import com.gnoemes.shikimoriapp.entity.manga.domain.Manga
import com.gnoemes.shikimoriapp.entity.manga.domain.MangaDetails
import io.reactivex.Single

interface MangaInteractor {

    fun getDetails(id: Long): Single<MangaDetails>

    fun getLinks(id: Long): Single<List<Link>>

    fun getSimilar(id: Long): Single<List<Manga>>

    fun getFranchiseNodes(id: Long): Single<List<FranchiseNode>>

}