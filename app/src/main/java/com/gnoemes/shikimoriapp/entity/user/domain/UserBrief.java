package com.gnoemes.shikimoriapp.entity.user.domain;

import android.support.annotation.Nullable;

import org.joda.time.DateTime;

public class UserBrief {

    private long id;
    private String nickname;
    private String avatarUrl;
    private DateTime lastOnline;
    @Nullable
    private String name;
    @Nullable
    private String sex;
    @Nullable
    private String website;

    public UserBrief(long id, String nickname, String avatarUrl,
                     DateTime lastOnline, @Nullable String name, @Nullable String sex,
                     @Nullable String website) {
        this.id = id;
        this.nickname = nickname;
        this.avatarUrl = avatarUrl;
        this.lastOnline = lastOnline;
        this.name = name;
        this.sex = sex;
        this.website = website;
    }

    public long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public DateTime getLastOnline() {
        return lastOnline;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public String getSex() {
        return sex;
    }

    @Nullable
    public String getWebsite() {
        return website;
    }
}
