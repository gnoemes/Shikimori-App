package com.gnoemes.shikimoriapp.entity.user.domain;

import android.support.annotation.Nullable;

import org.joda.time.DateTime;

public class UserProfile {
    private long id;
    private String nickname;
    private String largeImage;
    private String smallImage;
    private DateTime lastOnlineDateTime;
    private String lastOnline;
    @Nullable
    private String name;
    @Nullable
    private String sex;
    @Nullable
    private Integer fullYears;
    private String website;
    @Nullable
    private String location;
    private boolean isBanned;
    @Nullable
    private String about;
    private String commmonInfo;
    private boolean isMe;
    private boolean isShowComments;
    private boolean isFriend;
    private boolean isIgnored;
    private UserStats userStats;

    public UserProfile(long id, String nickname, String largeImage,
                       String smallImage, DateTime lastOnlineDateTime, String lastOnline,
                       @Nullable String name, @Nullable String sex, @Nullable Integer fullYears, String website,
                       @Nullable String location, boolean isBanned, @Nullable String about,
                       String commmonInfo, boolean isMe, boolean isShowComments, boolean isFriend,
                       boolean isIgnored, UserStats userStats) {
        this.id = id;
        this.nickname = nickname;
        this.largeImage = largeImage;
        this.smallImage = smallImage;
        this.lastOnlineDateTime = lastOnlineDateTime;
        this.lastOnline = lastOnline;
        this.name = name;
        this.sex = sex;
        this.fullYears = fullYears;
        this.website = website;
        this.location = location;
        this.isBanned = isBanned;
        this.about = about;
        this.commmonInfo = commmonInfo;
        this.isMe = isMe;
        this.isShowComments = isShowComments;
        this.isFriend = isFriend;
        this.isIgnored = isIgnored;
        this.userStats = userStats;
    }

    public long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getLargeImage() {
        return largeImage;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public DateTime getLastOnlineDateTime() {
        return lastOnlineDateTime;
    }

    public String getLastOnline() {
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
    public Integer getFullYears() {
        return fullYears;
    }

    public String getWebsite() {
        return website;
    }

    @Nullable
    public String getLocation() {
        return location;
    }

    public boolean isBanned() {
        return isBanned;
    }

    @Nullable
    public String getAbout() {
        return about;
    }

    public String getCommmonInfo() {
        return commmonInfo;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    public boolean isShowComments() {
        return isShowComments;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public boolean isIgnored() {
        return isIgnored;
    }

    public UserStats getUserStats() {
        return userStats;
    }
}
