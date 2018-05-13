package com.gnoemes.shikimoriapp.entity.anime.data;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.rates.data.UserRateResponse;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Anime full info response data model
 */
public class AnimeDetailsResponse {

    @SerializedName("id")
    private long id;

    @SerializedName("topic_id")
    private long topicId;

    @SerializedName("name")
    private String name;

    @Nullable
    @SerializedName("russian")
    private String russianName;

    @SerializedName("image")
    private AnimeImageResponse image;

    @SerializedName("url")
    private String url;

    @SerializedName("kind")
    private String type;

    @Nullable
    @SerializedName("status")
    private String status;

    @SerializedName("episodes")
    private int episodes;

    @SerializedName("episodes_aired")
    private int episodesAired;

    @Nullable
    @SerializedName("aired_on")
    private DateTime airedDate;

    @Nullable
    @SerializedName("released_on")
    private DateTime releasedDate;

    @Nullable
    @SerializedName("english")
    private List<String> englishNames;

    @Nullable
    @SerializedName("japanese")
    private List<String> japaneseNames;

    @SerializedName("duration")
    private int duration;

    @SerializedName("score")
    private double score;

    @Nullable
    @SerializedName("description")
    private String description;

    @SerializedName("genres")
    private List<GenreResponse> genres;

    @SerializedName("user_rate")
    private UserRateResponse rateResponse;

    public long getId() {
        return id;
    }

    public long getTopicId() {
        return topicId;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public String getRussianName() {
        return russianName;
    }

    public AnimeImageResponse getImage() {
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

    public int getEpisodes() {
        return episodes;
    }

    public int getEpisodesAired() {
        return episodesAired;
    }

    @Nullable
    public DateTime getAiredDate() {
        return airedDate;
    }

    @Nullable
    public DateTime getReleasedDate() {
        return releasedDate;
    }

    @Nullable
    public List<String> getEnglishNames() {
        return englishNames;
    }

    @Nullable
    public List<String> getJapaneseNames() {
        return japaneseNames;
    }

    public int getDuration() {
        return duration;
    }

    public double getScore() {
        return score;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public List<GenreResponse> getGenres() {
        return genres;
    }

    public UserRateResponse getRateResponse() {
        return rateResponse;
    }
}
