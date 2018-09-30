package com.gnoemes.shikimoriapp.presentation.presenter.calendar

import com.arellomobile.mvp.InjectViewState
import com.gnoemes.shikimoriapp.domain.app.AnalyticsInteractor
import com.gnoemes.shikimoriapp.domain.calendar.CalendarInteractor
import com.gnoemes.shikimoriapp.entity.app.domain.AnalyticsEvent
import com.gnoemes.shikimoriapp.entity.app.domain.BaseException
import com.gnoemes.shikimoriapp.entity.app.domain.NetworkException
import com.gnoemes.shikimoriapp.entity.calendar.presentation.CalendarItemViewModel
import com.gnoemes.shikimoriapp.presentation.presenter.calendar.converter.CalendarViewModelConverter
import com.gnoemes.shikimoriapp.presentation.presenter.calendar.provider.CalendarResourceProvider
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter
import com.gnoemes.shikimoriapp.presentation.view.calendar.CalendarView
import com.gnoemes.shikimoriapp.utils.appendLoadingLogic

@InjectViewState
class CalendarPresenter(
        private val interactor: CalendarInteractor,
        private val resourceProvider: CalendarResourceProvider,
        private val viewModelConverter: CalendarViewModelConverter,
        private val analyticsInteractor: AnalyticsInteractor
) : BaseNetworkPresenter<CalendarView>() {

    private var isMyOngoings: Boolean = false

    override fun initData() {
        setTitle()
        loadCalendar()
    }

    private fun setTitle() {
        when (isMyOngoings) {
            true -> viewState.setTitle(resourceProvider.myOngoingsTitle)
            else -> viewState.setTitle(resourceProvider.title)
        }
    }

    fun loadCalendar() {
        analyticsInteractor.logEvent(AnalyticsEvent.CALENDAR_UPDATED)

        when (isMyOngoings) {
            true -> loadMyOngoings()
            else -> loadCalendarData()
        }
    }

    private fun loadCalendarData() {
        interactor.calendarData
                .appendLoadingLogic(viewState)
                .doOnSubscribe { viewState.hideEmptyView() }
                .map(viewModelConverter)
                .subscribe(this::setData, this::processErrors)
                .addToDisposables()
    }

    private fun loadMyOngoings() {
        interactor.myCalendarData
                .appendLoadingLogic(viewState)
                .doOnSubscribe { viewState.hideEmptyView() }
                .map(viewModelConverter)
                .subscribe(this::setData, this::processErrors)
                .addToDisposables()
    }

    private fun setData(items: MutableList<CalendarItemViewModel>) {
        if (items.isNotEmpty()) {
            viewState.showCalendarData(items)
        } else {
            viewState.hideList()
            viewState.showEmptyView()
        }
    }


    /**
     * Catch errors
     */
    override fun processErrors(throwable: Throwable) {
        val baseException = throwable as? BaseException
        when (baseException?.tag) {
            NetworkException.TAG -> processNetworkError(throwable)
            else -> super.processErrors(throwable)
        }
    }

    /**
     * Catch network errors
     */
    private fun processNetworkError(throwable: Throwable) {
        viewState.showNetworkErrorView()
        viewState.hideList()
    }

    fun onFilterClicked() {
        isMyOngoings = !isMyOngoings
        initData()
    }
}