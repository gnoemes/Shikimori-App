package com.gnoemes.shikimoriapp.domain.calendar.impl

import com.gnoemes.shikimoriapp.data.repository.calendar.CalendarRepository
import com.gnoemes.shikimoriapp.data.repository.search.SearchRepository
import com.gnoemes.shikimoriapp.domain.calendar.CalendarInteractor
import com.gnoemes.shikimoriapp.domain.search.SearchQueryBuilder
import com.gnoemes.shikimoriapp.entity.calendar.domain.CalendarItem
import com.gnoemes.shikimoriapp.entity.rates.domain.RateStatus
import com.gnoemes.shikimoriapp.utils.rx.RxUtils
import com.gnoemes.shikimoriapp.utils.rx.SingleErrorHandler
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class CalendarInteractorImpl @Inject constructor(
        private val calendarRepository: CalendarRepository,
        private val searchRepository: SearchRepository,
        private val queryBuilder: SearchQueryBuilder,
        private val singleErrorHandler: SingleErrorHandler<Any>,
        private val rxUtils: RxUtils
) : CalendarInteractor {

    override fun getCalendarData(): Single<MutableList<CalendarItem>> =
            calendarRepository.calendarData
                    .compose(singleErrorHandler as SingleErrorHandler<MutableList<CalendarItem>>)
                    .compose(rxUtils.applySingleSchedulers())

    override fun getMyCalendarData(): Single<MutableList<CalendarItem>> =
            calendarRepository.calendarData
                    .flatMap { items ->
                        val ids = items.asSequence().map { it.anime.id }.toMutableList()
                        Observable.concat(
                                queryBuilder.createMyListQueryFromIds(ids, RateStatus.WATCHING)
                                        .flatMap { searchRepository.getAnimeList(it) }
                                        .flatMapObservable { Observable.fromIterable(it) },
                                queryBuilder.createMyListQueryFromIds(ids, RateStatus.PLANNED)
                                        .flatMap { searchRepository.getAnimeList(it) }
                                        .flatMapObservable { Observable.fromIterable(it) })
                                .toList()
                                .map { myOngoings -> items.filter { item -> myOngoings.asSequence().map { it.id }.contains(item.anime.id) } }
                                .map { it.toMutableList() }
                    }
                    .compose(singleErrorHandler as SingleErrorHandler<MutableList<CalendarItem>>)
                    .compose(rxUtils.applySingleSchedulers())
}