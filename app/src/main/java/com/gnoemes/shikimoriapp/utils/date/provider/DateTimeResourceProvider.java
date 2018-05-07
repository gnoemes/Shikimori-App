package com.gnoemes.shikimoriapp.utils.date.provider;

public interface DateTimeResourceProvider {

    /**
     * Returns "Today" string
     */
    String getTodayMessage();

    /**
     * Returns "Tomorrow" string
     */
    String getTomorrowMessage();

    /**
     * Returns format string
     * <p>
     * example: Today, 18 april
     */
    String getCalendarDateFormat();

    String getFallString();

    String getWinterString();

    String getSpringString();

    String getSummerString();

    String getYesterdayMessage();
}
