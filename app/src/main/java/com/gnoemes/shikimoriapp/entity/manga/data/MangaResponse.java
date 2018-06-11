package com.gnoemes.shikimoriapp.entity.manga.data;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

public class MangaResponse {

    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @Nullable
    @SerializedName("russian")
    private String russianName;
    @SerializedName("image")
    private MangaImageResponse image;
    @SerializedName("url")
    private String url;
    @SerializedName("kind")
    private String type;
    @SerializedName("status")
    @Nullable
    private String status;
    @SerializedName("volumes")
    private int volume;
    @SerializedName("chapters")
    private int chapters;
    @SerializedName("aired_on")
    private DateTime airedDate;
    @Nullable
    @SerializedName("released_on")
    private DateTime releasedDate;

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

    public MangaImageResponse getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }

    @Nullable
    public String getStatus() {
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
