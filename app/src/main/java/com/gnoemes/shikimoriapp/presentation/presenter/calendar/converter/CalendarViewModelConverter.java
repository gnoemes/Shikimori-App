package com.gnoemes.shikimoriapp.presentation.presenter.calendar.converter;

import com.gnoemes.shikimoriapp.entity.calendar.domain.CalendarItem;
import com.gnoemes.shikimoriapp.entity.calendar.presentation.CalendarItemViewModel;

import java.util.List;

import io.reactivex.functions.Function;

public interface CalendarViewModelConverter extends Function<List<CalendarItem>, List<CalendarItemViewModel>> {
}
