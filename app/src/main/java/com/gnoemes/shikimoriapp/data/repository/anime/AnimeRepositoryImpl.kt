package com.gnoemes.shikimoriapp.data.repository.anime

import com.gnoemes.shikimoriapp.data.local.db.HistoryDbSource
import com.gnoemes.shikimoriapp.data.local.db.RateSyncDbSource
import com.gnoemes.shikimoriapp.data.network.AnimesApi
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeDetailsResponseConverter
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeFranchiseResponseConverter
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeLinkResponseConverter
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeListResponseConverter
import com.gnoemes.shikimoriapp.entity.anime.domain.Anime
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeDetails
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeFranchiseNode
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeLink
import io.reactivex.Observable
import io.reactivex.Single
import org.joda.time.DateTimeComparator
import java.util.*
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
        val api: AnimesApi,
        val syncDbSource: RateSyncDbSource,
        val historyDbSource: HistoryDbSource,
        val responseConverter: AnimeDetailsResponseConverter,
        val listResponseConverter: AnimeListResponseConverter,
        val linkResponseConverter: AnimeLinkResponseConverter,
        val franchiseResponseConverter: AnimeFranchiseResponseConverter
) : AnimeRepository {


    override fun getAnimeDetails(animeId: Long): Single<AnimeDetails> =
            api.getAnimeDetails(animeId)
                    .flatMap { details ->
                        api.getRoles(animeId)
                                .map { responseConverter.convertDetailsWithCharacters(details, it) }
                    }
                    .flatMap { details ->
                        Single.just(details)
                                .filter { it.userRate != null }
                                .flatMapCompletable { anime ->
                                    syncDbSource.saveRateEpisodes(
                                            anime.userRate!!.id,
                                            anime.id,
                                            anime.userRate.episodes!!.toInt())
                                }
                                .toSingleDefault(details)
                    }


    override fun getAnimeLinks(animeId: Long): Single<MutableList<AnimeLink>> =
            api.getAnimeLinks(animeId)
                    .map(linkResponseConverter)

    override fun getSimilarAnimes(animeId: Long): Single<MutableList<Anime>> =
            api.getSimilarAnimes(animeId)
                    .map(listResponseConverter)

    override fun getFranchiseNodes(animeId: Long): Single<MutableList<AnimeFranchiseNode>> =
            api.getFranchise(animeId)
                    .map(franchiseResponseConverter)
                    .flatMap { nodes ->
                        Observable.fromIterable<AnimeFranchiseNode>(nodes)
                                .toSortedList { o1, o2 -> DateTimeComparator.getInstance().compare(o1.dateTime, o2.dateTime) }
                    }

    override fun getLocalWatchedAnimeIds(): Single<LinkedHashSet<Long>> =
            historyDbSource.watchedAnimes
                    .flatMap { historyDaos ->
                        Observable.fromIterable(historyDaos)
                                .map { it.animeId }
                                .toList()
                    }
                    .map { LinkedHashSet(it) }
}