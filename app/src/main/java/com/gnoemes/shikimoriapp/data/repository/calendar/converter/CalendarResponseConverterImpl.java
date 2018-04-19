package com.gnoemes.shikimoriapp.data.repository.calendar.converter;

import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeResponseConverter;
import com.gnoemes.shikimoriapp.entity.calendar.data.CalendarResponse;
import com.gnoemes.shikimoriapp.entity.calendar.domain.CalendarItem;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CalendarResponseConverterImpl implements CalendarResponseConverter {

    private AnimeResponseConverter animeResponseConverter;

    @Inject
    public CalendarResponseConverterImpl(AnimeResponseConverter animeResponseConverter) {
        this.animeResponseConverter = animeResponseConverter;
    }

    @Override
    public List<CalendarItem> apply(List<CalendarResponse> calendarResponses) {
        List<CalendarItem> calendarItems = new ArrayList<>();

        for (CalendarResponse response : calendarResponses) {
            calendarItems.add(new CalendarItem(response.getNextEpisode(),
                    response.getNextEpisodeDate(),
                    convertDuration(response.getNextEpisodeDate(), response.getDuration()),
                    animeResponseConverter.convertFrom(response.getAnime())));
        }

        return calendarItems;
    }


    private DateTime convertDuration(DateTime date, String duration) {
        if (!duration.contains("/")) {
            return new DateTime(date).plusSeconds((int) Double.parseDouble(duration));
        } else {
            return new DateTime(date).plusMinutes((int) Double.parseDouble(duration.substring(0, duration.indexOf("/"))));
        }
    }
}
