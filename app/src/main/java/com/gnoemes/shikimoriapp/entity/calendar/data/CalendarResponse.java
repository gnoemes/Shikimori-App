package com.gnoemes.shikimoriapp.entity.calendar.data;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeResponse;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

public class CalendarResponse {

    @SerializedName("anime")
    private AnimeResponse anime;
    @SerializedName("next_episode")
    private int nextEpisode;
    @SerializedName("next_episode_at")
    private DateTime nextEpisodeDate;
    @SerializedName("duration")
    private String duration;

    public int getNextEpisode() {
        return nextEpisode;
    }

    public DateTime getNextEpisodeDate() {
        return nextEpisodeDate;
    }

    public String getDuration() {
        return duration;
    }

    public AnimeResponse getAnime() {
        return anime;
    }
}
