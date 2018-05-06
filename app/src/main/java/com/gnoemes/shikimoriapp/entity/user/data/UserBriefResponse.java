package com.gnoemes.shikimoriapp.entity.user.data;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

public class UserBriefResponse {

    @SerializedName("id")
    private long id;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("image")
    private UserImageResponse imageResponse;

    @SerializedName("last_online_at")
    private DateTime lastOnline;

    @Nullable
    @SerializedName("name")
    private String name;

    @Nullable
    @SerializedName("sex")
    private String sex;

    @Nullable
    @SerializedName("website")
    private String website;

    @Nullable
    @SerializedName("birth_on")
    private DateTime birthDay;

    @SerializedName("locale")
    private String locale;

    public long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public UserImageResponse getImageResponse() {
        return imageResponse;
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

    @Nullable
    public DateTime getBirthDay() {
        return birthDay;
    }

    public String getLocale() {
        return locale;
    }
}
