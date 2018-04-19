package com.gnoemes.shikimoriapp.presentation.presenter.calendar.converter;

import com.gnoemes.shikimoriapp.entity.calendar.domain.CalendarItem;
import com.gnoemes.shikimoriapp.entity.calendar.presentation.CalendarAnimeViewModel;
import com.gnoemes.shikimoriapp.entity.calendar.presentation.CalendarItemViewModel;
import com.gnoemes.shikimoriapp.utils.date.DateTimeUtils;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

public class CalendarViewModelConverterImpl implements CalendarViewModelConverter {

    private DateTimeUtils dateTimeUtils;

    @Inject
    public CalendarViewModelConverterImpl(@NonNull DateTimeUtils dateTimeUtils) {
        this.dateTimeUtils = dateTimeUtils;
    }

    @Override
    public List<CalendarItemViewModel> apply(List<CalendarItem> calendarItems) {
        List<CalendarItemViewModel> list = new ArrayList<>();
        List<CalendarAnimeViewModel> animeList = new ArrayList<>();

        DateTime groupDate = calendarItems.get(0) == null ? null : calendarItems.get(0).getNextEpisodeDate();

        for (CalendarItem item : calendarItems) {
            animeList.add(convertAnimeViewModel(item));

            if (!dateTimeUtils.isSameDay(groupDate, item.getNextEpisodeDate())) {
                list.add(new CalendarItemViewModel(item.getNextEpisodeDate(), animeList));
                animeList = new ArrayList<>();
            }

            groupDate = item.getNextEpisodeDate();
        }

        return list;
    }

    private CalendarAnimeViewModel convertAnimeViewModel(CalendarItem item) {
        return new CalendarAnimeViewModel(
                item.getAnime().getId(),
                item.getAnime().getName(),
                item.getAnime().getRussianName(),
                item.getAnime().getAnimeImage().getImageOriginalUrl(),
                item.getAnime().getAnimeImage().getImagePreviewUrl(),
                item.getAnime().getAnimeImage().getImageX96Url(),
                item.getAnime().getAnimeImage().getImageX48Url(),
                item.getAnime().getUrl(),
                item.getAnime().getType(),
                item.getAnime().getStatus(),
                item.getAnime().getEpisodes(),
                item.getAnime().getEpisodesAired(),
                item.getAnime().getAiredDate(),
                item.getAnime().getReleasedDate(),
                item.getNextEpisode(),
                item.getNextEpisodeDate());
    }
}
