package com.gnoemes.shikimoriapp.data.repository.calendar.converter;

import com.gnoemes.shikimoriapp.entity.calendar.data.CalendarResponse;
import com.gnoemes.shikimoriapp.entity.calendar.domain.CalendarItem;

import java.util.List;

import io.reactivex.functions.Function;

public interface CalendarResponseConverter extends Function<List<CalendarResponse>, List<CalendarItem>> {
}
