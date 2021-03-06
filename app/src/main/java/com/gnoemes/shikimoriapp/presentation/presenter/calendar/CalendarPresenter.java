package com.gnoemes.shikimoriapp.presentation.presenter.calendar;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.domain.app.AnalyticsInteractor;
import com.gnoemes.shikimoriapp.domain.calendar.CalendarInteractor;
import com.gnoemes.shikimoriapp.entity.app.domain.AnalyticsEvent;
import com.gnoemes.shikimoriapp.entity.app.domain.BaseException;
import com.gnoemes.shikimoriapp.entity.app.domain.NetworkException;
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens;
import com.gnoemes.shikimoriapp.entity.calendar.presentation.CalendarItemViewModel;
import com.gnoemes.shikimoriapp.presentation.presenter.calendar.converter.CalendarViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.calendar.provider.CalendarResourceProvider;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.view.calendar.CalendarView;

import java.util.List;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class CalendarPresenter extends BaseNetworkPresenter<CalendarView> {

    private CalendarInteractor interactor;
    private CalendarResourceProvider resourceProvider;
    private CalendarViewModelConverter viewModelConverter;
    private AnalyticsInteractor analyticsInteractor;

    public CalendarPresenter(CalendarInteractor interactor,
                             CalendarResourceProvider resourceProvider,
                             CalendarViewModelConverter viewModelConverter,
                             AnalyticsInteractor analyticsInteractor) {
        this.interactor = interactor;
        this.resourceProvider = resourceProvider;
        this.viewModelConverter = viewModelConverter;
        this.analyticsInteractor = analyticsInteractor;
    }

    /**
     * Init primal data
     */
    @Override
    public void initData() {
        getViewState().setTitle(resourceProvider.getTitle());
        loadCalendarData();
    }

    /**
     * Get calendar from api
     */
    public void loadCalendarData() {
        getViewState().onShowLoading();
        getViewState().hideNetworkErrorView();
        analyticsInteractor.logEvent(AnalyticsEvent.CALENDAR_UPDATED);


        Disposable subscription = interactor.getCalendarData()
                .doOnEvent((calendarItems, throwable) -> getViewState().onHideLoading())
                .map(viewModelConverter)
                .subscribe(this::showList, this::processErrors);

        unsubscribeOnDestroy(subscription);
    }

    /**
     * Show list on fragment
     */
    private void showList(List<CalendarItemViewModel> items) {
        getViewState().showCalendarData(items);
    }

    /**
     * Catch errors
     */
    @Override
    protected void processErrors(Throwable throwable) {
        BaseException baseException = (BaseException) throwable;
        switch (baseException.getTag()) {
            case NetworkException.TAG:
                processNetworkError(throwable);
                break;
            default:
                super.processErrors(throwable);
        }
    }

    /**
     * Catch network errors
     */
    private void processNetworkError(Throwable throwable) {
        getViewState().showNetworkErrorView();
        getViewState().hideList();
    }

    public void onAnimeClicked(long id) {
        analyticsInteractor.logEvent(AnalyticsEvent.ANIME_OPENED);
        getRouter().navigateTo(Screens.ANIME_DETAILS, id);
    }
}
