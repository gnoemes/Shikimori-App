package com.gnoemes.shikimoriapp.entity.calendar.presentation;

import org.joda.time.DateTime;

import java.util.List;

public class CalendarItemViewModel {

    private DateTime date;

    private List<CalendarAnimeViewModel> animeViewModels;

    public CalendarItemViewModel(DateTime date, List<CalendarAnimeViewModel> animeViewModels) {
        this.date = date;
        this.animeViewModels = animeViewModels;
    }

    public DateTime getDate() {
        return date;
    }

    public List<CalendarAnimeViewModel> getAnimeViewModels() {
        return animeViewModels;
    }
}
