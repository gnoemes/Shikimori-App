package com.gnoemes.shikimoriapp.entity.rates.domain;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief;

import java.io.Serializable;

public class AnimeRate implements Serializable {

    private long id;
    private int score;
    private RateStatus status;
    @Nullable
    private String text;
    private int episodes;
    @Nullable
    private Integer chapters;
    @Nullable
    private Integer volumes;
    private int rewatches;
    private UserBrief userBrief;
    private Anime anime;

    public AnimeRate(long id, int score, RateStatus status, @Nullable String text,
                     int episodes, @Nullable Integer chapters, @Nullable Integer volumes,
                     int rewatches, UserBrief userBrief, Anime anime) {
        this.id = id;
        this.score = score;
        this.status = status;
        this.text = text;
        this.episodes = episodes;
        this.chapters = chapters;
        this.volumes = volumes;
        this.rewatches = rewatches;
        this.userBrief = userBrief;
        this.anime = anime;
    }

    public long getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public RateStatus getStatus() {
        return status;
    }

    @Nullable
    public String getText() {
        return text;
    }

    public int getEpisodes() {
        return episodes;
    }

    @Nullable
    public Integer getChapters() {
        return chapters;
    }

    @Nullable
    public Integer getVolumes() {
        return volumes;
    }

    public int getRewatches() {
        return rewatches;
    }

    public UserBrief getUserBrief() {
        return userBrief;
    }

    public Anime getAnime() {
        return anime;
    }
}
