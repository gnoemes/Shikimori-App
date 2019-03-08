package com.gnoemes.shikimoriapp.data.repository.ranobe

import com.gnoemes.shikimoriapp.data.local.db.HistoryDbSource
import com.gnoemes.shikimoriapp.data.local.db.RateSyncDbSource
import com.gnoemes.shikimoriapp.data.network.RanobeApi
import com.gnoemes.shikimoriapp.data.repository.anime.converter.FranchiseResponseConverter
import com.gnoemes.shikimoriapp.data.repository.anime.converter.LinkResponseConverter
import com.gnoemes.shikimoriapp.data.repository.manga.converter.MangaDetailsResponseConverter
import com.gnoemes.shikimoriapp.data.repository.manga.converter.MangaResponseConverter
import com.gnoemes.shikimoriapp.entity.anime.domain.FranchiseNode
import com.gnoemes.shikimoriapp.entity.anime.domain.Link
import com.gnoemes.shikimoriapp.entity.manga.domain.Manga
import com.gnoemes.shikimoriapp.entity.manga.domain.MangaDetails
import io.reactivex.Observable
import io.reactivex.Single
import org.joda.time.DateTimeComparator
import javax.inject.Inject

class RanobeRepositoryImpl @Inject constructor(
        private val api: RanobeApi,
        private val syncDbSource: RateSyncDbSource,
        private val historyDbSource: HistoryDbSource,
        private val detailsConverter: MangaDetailsResponseConverter,
        private val linkConverter: LinkResponseConverter,
        private val franchiseConverter: FranchiseResponseConverter,
        private val mangaConverter: MangaResponseConverter
) : RanobeRepository {

    override fun getDetails(id: Long): Single<MangaDetails> {
        //TODO implement sync
        return Single.error(UnsupportedOperationException())
    }

    override fun getLinks(id: Long): Single<List<Link>> = api.getLinks(id).map(linkConverter)

    override fun getSimilar(id: Long): Single<List<Manga>> = api.getSimilar(id).map(mangaConverter)

    override fun getFranchiseNodes(id: Long): Single<List<FranchiseNode>> =
            api.getFranchise(id)
                    .map(franchiseConverter)
                    .flatMap { nodes ->
                        Observable.fromIterable(nodes)
                                .toSortedList { o1, o2 -> DateTimeComparator.getInstance().compare(o1.dateTime, o2.dateTime) }
                    }
}