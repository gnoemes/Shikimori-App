package com.gnoemes.shikimoriapp.entity.user.presentation.profile;

import android.support.annotation.Nullable;

public class ProfileHeadItem extends BaseProfileItem {

    private long id;
    private String nickname;
    private String largeImage;
    private String smallImage;
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

    public ProfileHeadItem() {
    }

    public ProfileHeadItem(long id, String nickname, String largeImage,
                           String smallImage, String lastOnline, @Nullable String name,
                           @Nullable String sex, @Nullable Integer fullYears, String website,
                           @Nullable String location, boolean isBanned, @Nullable String about,
                           String commmonInfo, boolean isMe, boolean isShowComments,
                           boolean isFriend, boolean isIgnored) {
        this.id = id;
        this.nickname = nickname;
        this.largeImage = largeImage;
        this.smallImage = smallImage;
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

    public boolean isShowComments() {
        return isShowComments;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public boolean isIgnored() {
        return isIgnored;
    }
}
