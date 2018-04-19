package com.gnoemes.shikimoriapp.entity.calendar.domain;

import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;

import org.joda.time.DateTime;

public class CalendarItem {

    /**
     * Next episode
     */
    private int nextEpisode;

    /**
     * Date of next episode
     */
    private DateTime nextEpisodeDate;

    /**
     * Date of end next episode (date of next episode + duration)
     */
    private DateTime nextEpisodeEndDate;

    /**
     * Anime in that date
     */
    private Anime anime;


    public CalendarItem(int nextEpisode,
                        DateTime nextEpisodeDate,
                        DateTime nextEpisodeEndDate,
                        Anime anime) {
        this.nextEpisode = nextEpisode;
        this.nextEpisodeDate = nextEpisodeDate;
        this.nextEpisodeEndDate = nextEpisodeEndDate;
        this.anime = anime;
    }

    public int getNextEpisode() {
        return nextEpisode;
    }

    public DateTime getNextEpisodeDate() {
        return nextEpisodeDate;
    }

    public DateTime getNextEpisodeEndDate() {
        return nextEpisodeEndDate;
    }

    public Anime getAnime() {
        return anime;
    }
}
