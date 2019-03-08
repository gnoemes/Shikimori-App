package com.gnoemes.shikimoriapp.entity.anime.domain;

import android.support.annotation.Nullable;

import org.joda.time.DateTime;

public class FranchiseNode {
    private long id;
    private DateTime dateTime;
    private String name;
    private String imageUrl;
    private String url;
    @Nullable
    private Integer year;
    private String type;

    public FranchiseNode(long id, DateTime dateTime, String name, String imageUrl, String url, @Nullable Integer year, String type) {
        this.id = id;
        this.dateTime = dateTime;
        this.name = name;
        this.imageUrl = imageUrl;
        this.url = url;
        this.year = year;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }

    @Nullable
    public Integer getYear() {
        return year;
    }

    public String getType() {
        return type;
    }
}
