package com.gnoemes.shikimoriapp.utils.date;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import javax.inject.Inject;

public class DateTimeUtilsImpl implements DateTimeUtils {

    @Inject
    public DateTimeUtilsImpl() {
    }

    @Override
    public DateTime getNowDateTime() {
        return DateTime.now();
    }

    @Override
    public boolean isToday(DateTime date) {
        return getNowDateTime().toLocalDate().equals(new LocalDate(date));
    }

    @Override
    public boolean isTomorrow(DateTime date) {
        return getNowDateTime().toLocalDate().plusDays(1).equals(new LocalDate(date));
    }

    @Override
    public boolean isYesterday(DateTime date) {
        return getNowDateTime().toLocalDate().minusDays(1).equals(new LocalDate(date));
    }

    @Override
    public boolean isSameDay(DateTime firstDate, DateTime secondDate) {
        return firstDate != null &&
                secondDate != null &&
                firstDate.dayOfYear().get() == secondDate.dayOfYear().get();
    }
}
