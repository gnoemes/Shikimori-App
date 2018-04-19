package com.gnoemes.shikimoriapp.domain.calendar.impl;

import com.gnoemes.shikimoriapp.data.repository.calendar.CalendarRepository;
import com.gnoemes.shikimoriapp.domain.calendar.CalendarInteractor;
import com.gnoemes.shikimoriapp.entity.calendar.domain.CalendarItem;
import com.gnoemes.shikimoriapp.utils.rx.RxUtils;
import com.gnoemes.shikimoriapp.utils.rx.SingleErrorHandler;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

public class CalendarInteractorImpl implements CalendarInteractor {

    private CalendarRepository calendarRepository;
    private SingleErrorHandler singleErrorHandler;
    private RxUtils rxUtils;

    @Inject
    public CalendarInteractorImpl(@NonNull CalendarRepository calendarRepository,
                                  @NonNull SingleErrorHandler singleErrorHandler,
                                  @NonNull RxUtils rxUtils) {
        this.calendarRepository = calendarRepository;
        this.singleErrorHandler = singleErrorHandler;
        this.rxUtils = rxUtils;
    }

    @Override
    public Single<List<CalendarItem>> getCalendarData() {
        return calendarRepository.getCalendarData()
                .compose((SingleErrorHandler<List<CalendarItem>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }
}
