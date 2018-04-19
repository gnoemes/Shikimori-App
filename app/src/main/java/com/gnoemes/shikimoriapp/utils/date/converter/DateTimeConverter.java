package com.gnoemes.shikimoriapp.utils.date.converter;

import org.joda.time.DateTime;

public interface DateTimeConverter {

    String convertCalendarDateToString(DateTime dateTime);

    String convertCalendarTimeToString(DateTime dateTime);
}
