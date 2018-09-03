package com.gnoemes.shikimoriapp.entity.anime.domain;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.common.domain.Image;
import com.gnoemes.shikimoriapp.entity.rates.domain.UserRate;
import com.gnoemes.shikimoriapp.entity.roles.domain.Character;
import com.gnoemes.shikimoriapp.entity.video.domain.Video;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Domain model of full anime info
 */
public class AnimeDetails {

    private long id;
    private long topicId;
    private String name;
    @Nullable
    private String secondName;
    private Image image;
    private String url;
    private AnimeType type;
    @Nullable
    private AnimeStatus status;
    private int episodes;
    private int episodesAired;
    @Nullable
    private DateTime airedDate;
    @Nullable
    private DateTime releasedDate;
    @Nullable
    private List<String> englishNames;
    @Nullable
    private List<String> japaneseNames;
    private int duration;
    private double score;
    @Nullable
    private String description;
    private List<AnimeGenre> animeGenres;
    @Nullable
    private UserRate animeRate;
    @Nullable
    private List<Video> videos;
    private List<Character> characters;

    public AnimeDetails(long id, long topicId, String name, @Nullable String secondName, Image image,
                        String url, AnimeType type, @Nullable AnimeStatus status, int episodes,
                        int episodesAired, @Nullable DateTime airedDate, @Nullable DateTime releasedDate,
                        @Nullable List<String> englishNames, @Nullable List<String> japaneseNames, int duration,
                        double score, @Nullable String description, List<AnimeGenre> animeGenres,
                        @Nullable UserRate animeRate, @Nullable List<Video> videos, List<Character> characters) {
        this.id = id;
        this.topicId = topicId;
        this.name = name;
        this.secondName = secondName;
        this.image = image;
        this.url = url;
        this.type = type;
        this.status = status;
        this.episodes = episodes;
        this.episodesAired = episodesAired;
        this.airedDate = airedDate;
        this.releasedDate = releasedDate;
        this.englishNames = englishNames;
        this.japaneseNames = japaneseNames;
        this.duration = duration;
        this.score = score;
        this.description = description;
        this.animeGenres = animeGenres;
        this.animeRate = animeRate;
        this.videos = videos;
        this.characters = characters;
    }

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
    public String getSecondName() {
        return secondName;
    }

    public Image getImage() {
        return image;
    }
    public String getUrl() {
        return url;
    }

    public AnimeType getType() {
        return type;
    }

    @Nullable
    public AnimeStatus getStatus() {
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

    public List<AnimeGenre> getAnimeGenres() {
        return animeGenres;
    }

    @Nullable
    public UserRate getAnimeRate() {
        return animeRate;
    }

    @Nullable
    public List<Video> getVideos() {
        return videos;
    }

    public List<Character> getCharacters() {
        return characters;
    }
}
