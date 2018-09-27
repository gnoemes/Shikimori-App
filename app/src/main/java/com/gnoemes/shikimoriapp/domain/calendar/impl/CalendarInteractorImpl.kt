package com.gnoemes.shikimoriapp.domain.calendar.impl

import com.gnoemes.shikimoriapp.data.repository.calendar.CalendarRepository
import com.gnoemes.shikimoriapp.data.repository.search.SearchRepository
import com.gnoemes.shikimoriapp.domain.calendar.CalendarInteractor
import com.gnoemes.shikimoriapp.domain.search.SearchQueryBuilder
import com.gnoemes.shikimoriapp.entity.calendar.domain.CalendarItem
import com.gnoemes.shikimoriapp.utils.rx.RxUtils
import com.gnoemes.shikimoriapp.utils.rx.SingleErrorHandler
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
                    .compose(singleErrorHandler.handleErrors<MutableList<CalendarItem>>())
                    .compose(rxUtils.applySingleSchedulers())

    override fun getMyCalendarData(): Single<MutableList<CalendarItem>> =
            calendarRepository.calendarData
                    .flatMap { items ->
                        val ids = items.map { it.anime.id }.toMutableList()
                        queryBuilder.createMyListQueryFromIds(ids)
                                .flatMap { searchRepository.getAnimeList(it) }
                                .map { myOngoings -> items.filter { item -> myOngoings.asSequence().map { it.id }.contains(item.anime.id) } }
                                .map { it.toMutableList() }
                    }
                    .compose(singleErrorHandler.handleErrors<MutableList<CalendarItem>>())
                    .compose(rxUtils.applySingleSchedulers())
}