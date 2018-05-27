package com.gnoemes.shikimoriapp.entity.user.data;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.List;

public class UserProfileResponse {

    @SerializedName("id")
    private long id;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("image")
    private UserImageResponse imageResponse;
    @SerializedName("last_online_at")
    private DateTime lastOnlineDateTime;
    @Nullable
    @SerializedName("name")
    private String name;
    @Nullable
    @SerializedName("sex")
    private String sex;
    @Nullable
    @SerializedName("full_years")
    private Integer fullYears;
    @SerializedName("last_online")
    private String lastOnline;
    @SerializedName("website")
    private String website;
    @Nullable
    @SerializedName("location")
    private String location;
    @SerializedName("banned")
    private boolean isBanned;
    @Nullable
    @SerializedName("about")
    private String about;
    @SerializedName("common_info")
    private List<String> commonInfo;
    @SerializedName("show_comments")
    private boolean isShowComments;
    @SerializedName("in_friends")
    private boolean isFriend;
    @SerializedName("is_ignored")
    private boolean isIgnored;
    @SerializedName("stats")
    private UserStatsResponse statsResponse;
    @SerializedName("style_id")
    private long styleId;

    public long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public UserImageResponse getImageResponse() {
        return imageResponse;
    }

    public DateTime getLastOnlineDateTime() {
        return lastOnlineDateTime;
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

    public String getLastOnline() {
        return lastOnline;
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

    public List<String> getCommonInfo() {
        return commonInfo;
    }

    public boolean isShowComments() {
        return isShowComments;
    }

    public boolean getFriend() {
        return isFriend;
    }

    public boolean isIgnored() {
        return isIgnored;
    }

    public UserStatsResponse getStatsResponse() {
        return statsResponse;
    }

    public long getStyleId() {
        return styleId;
    }
}
