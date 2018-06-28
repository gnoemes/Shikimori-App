package com.gnoemes.shikimoriapp.entity.video.domain;

import android.support.annotation.Nullable;

public class Video {
    private long id;
    private String url;
    @Nullable
    private String name;
    private VideoType type;
    private String player;

    public Video(long id, String url, @Nullable String name, VideoType type, String player) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.type = type;
        this.player = player;
    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public VideoType getType() {
        return type;
    }

    public String getPlayer() {
        return player;
    }
}
