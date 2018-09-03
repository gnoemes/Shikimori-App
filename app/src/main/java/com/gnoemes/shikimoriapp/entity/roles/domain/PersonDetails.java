package com.gnoemes.shikimoriapp.entity.roles.domain;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.common.domain.Image;

import org.joda.time.DateTime;

import java.util.List;

public class PersonDetails {

    private long id;
    private String name;
    @Nullable
    private String russianName;
    @Nullable
    private String japaneseName;
    private Image image;
    private String url;
    @Nullable
    private String jobTitle;
    @Nullable
    private DateTime birthDay;
    @Nullable
    private List<Character> characters;
    @Nullable
    private List<Work> works;
    private List<List<String>> roles;
    private long topicId;
    private PersonType type;
    private PersonFavoriteType favoriteType;

    public PersonDetails(long id, String name, @Nullable String russianName,
                         @Nullable String japaneseName, Image image,
                         String url, @Nullable String jobTitle, @Nullable DateTime birthDay,
                         @Nullable List<Character> characters, @Nullable List<Work> works,
                         List<List<String>> roles, long topicId, PersonType type,
                         PersonFavoriteType favoriteType) {
        this.id = id;
        this.name = name;
        this.russianName = russianName;
        this.japaneseName = japaneseName;
        this.image = image;
        this.url = url;
        this.jobTitle = jobTitle;
        this.birthDay = birthDay;
        this.characters = characters;
        this.works = works;
        this.roles = roles;
        this.topicId = topicId;
        this.type = type;
        this.favoriteType = favoriteType;
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

    @Nullable
    public String getJapaneseName() {
        return japaneseName;
    }

    public Image getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    @Nullable
    public String getJobTitle() {
        return jobTitle;
    }

    @Nullable
    public DateTime getBirthDay() {
        return birthDay;
    }

    @Nullable
    public List<Character> getCharacters() {
        return characters;
    }

    @Nullable
    public List<Work> getWorks() {
        return works;
    }

    public List<List<String>> getRoles() {
        return roles;
    }

    public long getTopicId() {
        return topicId;
    }

    public PersonType getType() {
        return type;
    }

    public PersonFavoriteType getFavoriteType() {
        return favoriteType;
    }
}
