package com.gnoemes.shikimoriapp.utils.date.converter;

import org.joda.time.DateTime;

public interface DateTimeConverter {

    String convertCalendarDateToString(DateTime dateTime);

    String convertCalendarTimeToString(DateTime dateTime);

    String convertAnimeSeasonToString(DateTime dateTime);

    String convertCommentDateTimeToString(DateTime dateTime);

    String convertToFullHumanDateString(DateTime dateTime);

    String convertHistoryDateToString(DateTime actionDate);

    String convertDateAgoToString(DateTime actionDate);
}
