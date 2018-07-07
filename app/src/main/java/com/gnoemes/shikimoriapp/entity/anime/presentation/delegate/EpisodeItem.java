package com.gnoemes.shikimoriapp.entity.anime.presentation.delegate;

public class EpisodeItem extends BaseEpisodeItem {

    private long id;
    private long animeId;
    private String hostings;
    private boolean isWatched;

    public EpisodeItem(long id, long animeId, String hostings, boolean isWatched) {
        this.id = id;
        this.animeId = animeId;
        this.hostings = hostings;
        this.isWatched = isWatched;
    }

    public long getId() {
        return id;
    }

    public long getAnimeId() {
        return animeId;
    }

    public String getHostings() {
        return hostings;
    }

    public boolean isWatched() {
        return isWatched;
    }
}
