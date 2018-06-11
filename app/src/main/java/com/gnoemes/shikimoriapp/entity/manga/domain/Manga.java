package com.gnoemes.shikimoriapp.entity.manga.domain;

import android.support.annotation.Nullable;

import org.joda.time.DateTime;

public class Manga {


    private long id;
    private String name;
    @Nullable
    private String russianName;
    private MangaImage image;
    private String url;
    private MangaType type;
    private MangaStatus status;
    private int volume;
    private int chapters;
    private DateTime airedDate;
    @Nullable
    private DateTime releasedDate;

    public Manga(long id, String name, @Nullable String russianName,
                 MangaImage image, String url, MangaType type,
                 MangaStatus status, int volume, int chapters,
                 DateTime airedDate, @Nullable DateTime releasedDate) {
        this.id = id;
        this.name = name;
        this.russianName = russianName;
        this.image = image;
        this.url = url;
        this.type = type;
        this.status = status;
        this.volume = volume;
        this.chapters = chapters;
        this.airedDate = airedDate;
        this.releasedDate = releasedDate;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public String getRussianName() {
        return russianName;
    }

    public MangaImage getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public MangaType getType() {
        return type;
    }

    public MangaStatus getStatus() {
        return status;
    }

    public int getVolume() {
        return volume;
    }

    public int getChapters() {
        return chapters;
    }

    public DateTime getAiredDate() {
        return airedDate;
    }

    @Nullable
    public DateTime getReleasedDate() {
        return releasedDate;
    }
}
