package com.gnoemes.shikimoriapp.domain.ranobe

import com.gnoemes.shikimoriapp.data.repository.ranobe.RanobeRepository
import com.gnoemes.shikimoriapp.entity.anime.domain.FranchiseNode
import com.gnoemes.shikimoriapp.entity.anime.domain.Link
import com.gnoemes.shikimoriapp.entity.manga.domain.Manga
import com.gnoemes.shikimoriapp.entity.manga.domain.MangaDetails
import com.gnoemes.shikimoriapp.utils.rx.RxUtils
import com.gnoemes.shikimoriapp.utils.rx.SingleErrorHandler
import io.reactivex.Single
import javax.inject.Inject

class RanobeInteractorImpl @Inject constructor(
        private val repository: RanobeRepository,
        private val singleErrorHandler: SingleErrorHandler<Any>,
        private val rxUtils: RxUtils
) : RanobeInteractor {

    override fun getDetails(id: Long): Single<MangaDetails> = repository.getDetails(id).composeHandlerAndSchedulers()

    override fun getLinks(id: Long): Single<List<Link>> = repository.getLinks(id).composeHandlerAndSchedulers()

    override fun getSimilar(id: Long): Single<List<Manga>> = repository.getSimilar(id).composeHandlerAndSchedulers()

    override fun getFranchiseNodes(id: Long): Single<List<FranchiseNode>> = repository.getFranchiseNodes(id).composeHandlerAndSchedulers()

    private fun <T> Single<T>.composeHandlerAndSchedulers(): Single<T> {
        return this.compose(singleErrorHandler.handleErrors())
                .compose(rxUtils.applySingleSchedulers())
    }
}