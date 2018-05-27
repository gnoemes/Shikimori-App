package com.gnoemes.shikimoriapp.utils.date.converter;

import com.gnoemes.shikimoriapp.utils.date.DateTimeUtils;
import com.gnoemes.shikimoriapp.utils.date.provider.DateTimeResourceProvider;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Years;
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

    @Override
    public String convertAnimeSeasonToString(DateTime dateTime) {
        String season;
        if (dateTime == null) {
            return "xxx";
        }

        switch (dateTime.getMonthOfYear()) {
            case 1:
                season = resourceProvider.getWinterString();
                break;
            case 2:
                season = resourceProvider.getWinterString();
                break;
            case 3:
                season = resourceProvider.getSpringString();
                break;
            case 4:
                season = resourceProvider.getSpringString();
                break;
            case 5:
                season = resourceProvider.getSpringString();
                break;
            case 6:
                season = resourceProvider.getSummerString();
                break;
            case 7:
                season = resourceProvider.getSummerString();
                break;
            case 8:
                season = resourceProvider.getSummerString();
                break;
            case 9:
                season = resourceProvider.getFallString();
                break;
            case 10:
                season = resourceProvider.getFallString();
                break;
            case 11:
                season = resourceProvider.getFallString();
                break;
            case 12:
                season = resourceProvider.getWinterString();
                break;
            default:
                return "";
        }

        return season.concat(" ") + dateTime.getYear();
    }

    @Override
    public String convertCommentDateTimeToString(DateTime dateTime) {
        DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("dd MMMM yyyy");

        if (dateUtils.isToday(dateTime)) {
            return resourceProvider.getTodayMessage();
        } else if (dateUtils.isYesterday(dateTime)) {
            return resourceProvider.getYesterdayMessage();
        }

        return dateFormatter.print(dateTime);
    }

    private String firstToUpperCase(String print) {
        return print.substring(0, 1).toUpperCase().concat(print.substring(1));
    }

    @Override
    public String convertHistoryDateToString(DateTime actionDate) {
        if (dateUtils.isToday(actionDate)) {
            return resourceProvider.getTodayMessage();
        } else if (dateUtils.isYesterday(actionDate)) {
            return resourceProvider.getYesterdayMessage();
        } else if (Months.monthsBetween(dateUtils.getNowDateTime(), actionDate).getMonths() == 0) {
            int diff = Math.abs(dateUtils.getNowDateTime().toLocalDate().getWeekOfWeekyear() - actionDate.toLocalDate().getWeekOfWeekyear());
            return firstToUpperCase(resourceProvider.getWeeksAgoString(diff));
        } else if (dateUtils.isSameYear(actionDate) || Years.yearsBetween(dateUtils.getNowDateTime(), actionDate).getYears() == 0) {
            int diff = Math.abs(Months.monthsBetween(dateUtils.getNowDateTime(), actionDate).getMonths());
            return firstToUpperCase(resourceProvider.getMonthsAgoString(diff, false));
        } else {
            int diff = Math.abs(Years.yearsBetween(dateUtils.getNowDateTime(), actionDate).getYears());
            return firstToUpperCase(resourceProvider.getYearsAgoString(diff));
        }
    }

    @Override
    public String convertDateAgoToString(DateTime actionDate) {
        if (dateUtils.isSameDay(actionDate)) {
            int hours = Math.abs(Hours.hoursBetween(dateUtils.getNowDateTime(), actionDate).getHours());
            if (hours == 0) {
                int minutes = Math.abs(Minutes.minutesBetween(dateUtils.getNowDateTime(), actionDate).getMinutes());
                return resourceProvider.getMinutesAgoString(minutes);
            }
            return resourceProvider.getHoursAgoString(hours);
        } else if (Months.monthsBetween(dateUtils.getNowDateTime(), actionDate).getMonths() == 0) {
            int diff = Math.abs(Days.daysBetween(dateUtils.getNowDateTime(), actionDate).getDays());
            return resourceProvider.getDaysAgoString(diff);
        } else if (Years.yearsBetween(dateUtils.getNowDateTime(), actionDate).getYears() == 0) {
            int diff = Math.abs(Months.monthsBetween(dateUtils.getNowDateTime(), actionDate).getMonths());
            return resourceProvider.getMonthsAgoString(diff, true);
        } else {
            int diff = Math.abs(Years.yearsBetween(dateUtils.getNowDateTime(), actionDate).getYears());
            return resourceProvider.getYearsAgoString(diff);
        }
    }
}
