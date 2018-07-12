package com.gnoemes.shikimoriapp.entity.series.domain;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;

import java.util.List;

public class Episode {

    private int id;
    private long animeId;
    private List<TranslationType> types;
    private List<VideoHosting> videoHostings;
    private String rawHostings;
    private boolean isWatched;

    public Episode(int id,
                   long animeId,
                   List<TranslationType> types,
                   List<VideoHosting> videoHostings,
                   String rawHostings,
                   boolean isWatched) {
        this.id = id;
        this.animeId = animeId;
        this.types = types;
        this.videoHostings = videoHostings;
        this.rawHostings = rawHostings;
        this.isWatched = isWatched;
    }

    public int getId() {
        return id;
    }

    public long getAnimeId() {
        return animeId;
    }

    public List<TranslationType> getTypes() {
        return types;
    }

    public List<VideoHosting> getVideoHostings() {
        return videoHostings;
    }

    public String getRawHostings() {
        return rawHostings;
    }

    public boolean isWatched() {
        return isWatched;
    }

    public void setWatched(boolean watched) {
        isWatched = watched;
    }
}
