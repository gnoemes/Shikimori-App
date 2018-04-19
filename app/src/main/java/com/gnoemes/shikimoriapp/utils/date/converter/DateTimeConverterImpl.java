package com.gnoemes.shikimoriapp.utils.date.converter;

import com.gnoemes.shikimoriapp.utils.date.DateTimeUtils;
import com.gnoemes.shikimoriapp.utils.date.provider.DateTimeResourceProvider;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

public class DateTimeConverterImpl implements DateTimeConverter {

    private DateTimeResourceProvider resourceProvider;
    private DateTimeUtils dateUtils;

    @Inject
    public DateTimeConverterImpl(@NonNull DateTimeResourceProvider resourceProvider,
                                 @NonNull DateTimeUtils dateUtils) {
        this.resourceProvider = resourceProvider;
        this.dateUtils = dateUtils;
    }

    @Override
    public String convertCalendarDateToString(DateTime dateTime) {
        DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("d MMMM");
        DateTimeFormatter weekFormatter = DateTimeFormat.forPattern("EEEE");

        if (dateUtils.isToday(dateTime)) {
            return String.format(resourceProvider.getCalendarDateFormat(),
                    resourceProvider.getTodayMessage(), dateFormatter.print(dateTime));
        } else if (dateUtils.isTomorrow(dateTime)) {
            return String.format(resourceProvider.getCalendarDateFormat(),
                    resourceProvider.getTomorrowMessage(), dateFormatter.print(dateTime));
        } else {
            String date = firstToUpperCase(dateFormatter.print(dateTime));
            String week = firstToUpperCase(weekFormatter.print(dateTime));
            return String.format(resourceProvider.getCalendarDateFormat(),
                    week, date);
        }
    }

    @Override
    public String convertCalendarTimeToString(DateTime dateTime) {
        DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("HH:mm");
        return timeFormatter.print(dateTime);
    }

    private String firstToUpperCase(String print) {
        return print.substring(0, 1).toUpperCase().concat(print.substring(1));
    }
}
