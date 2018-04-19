package com.gnoemes.shikimoriapp.utils.date;

import org.joda.time.DateTime;

import io.reactivex.annotations.NonNull;

/**
 * Util class for date and time
 */
public interface DateTimeUtils {

    /**
     * Returns current date
     */
    DateTime getNowDateTime();

    boolean isToday(DateTime time);

    boolean isTomorrow(DateTime time);

    boolean isYesterday(DateTime time);

    boolean isSameDay(@NonNull DateTime firstDate, DateTime secondDate);

}
