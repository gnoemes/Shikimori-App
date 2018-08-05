package com.gnoemes.shikimoriapp.entity.anime.series.presentation;

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeType;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.BaseEpisodeItem;

import org.joda.time.DateTime;

public class EpisodeItem extends BaseEpisodeItem {

    private long id;
    private long seriesId;
    private long animeId;
    private String episodeFull;
    private float episode;
    private AnimeType type;
    private DateTime date;
    private long views;
    private boolean isWatched;


    public EpisodeItem(long id, long seriesId, long animeId, String episodeFull, float episode, AnimeType type, DateTime date, long views, boolean isWatched) {
        this.id = id;
        this.seriesId = seriesId;
        this.animeId = animeId;
        this.episodeFull = episodeFull;
        this.episode = episode;
        this.type = type;
        this.date = date;
        this.views = views;
        this.isWatched = isWatched;
    }

    public long getId() {
        return id;
    }

    public long getSeriesId() {
        return seriesId;
    }

    public long getAnimeId() {
        return animeId;
    }

    public String getEpisodeFull() {
        return episodeFull;
    }

    public float getEpisode() {
        return episode;
    }

    public AnimeType getType() {
        return type;
    }

    public DateTime getDate() {
        return date;
    }

    public long getViews() {
        return views;
    }

    public boolean isWatched() {
        return isWatched;
    }
}
