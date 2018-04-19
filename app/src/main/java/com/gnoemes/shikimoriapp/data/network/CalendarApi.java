package com.gnoemes.shikimoriapp.data.network;

import com.gnoemes.shikimoriapp.entity.calendar.data.CalendarResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Api for calendar
 */
public interface CalendarApi {

    /**
     * Get request for calendar
     */
    @GET("/api/calendar")
    Single<List<CalendarResponse>> getCalendarData();

}
