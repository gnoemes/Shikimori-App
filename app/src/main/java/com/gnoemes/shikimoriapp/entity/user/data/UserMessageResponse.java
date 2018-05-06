package com.gnoemes.shikimoriapp.entity.user.data;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeResponse;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

public class UserMessageResponse {

    @SerializedName("read")
    boolean isRead;
    @SerializedName("id")
    private long id;
    @SerializedName("kind")
    private String kind;
    @SerializedName("body")
    private String text;

    @SerializedName("html_body")
    private String htmlText;

    @SerializedName("created_at")
    private DateTime createdDate;

    @Nullable
    @SerializedName("linked")
    private AnimeResponse animeResponse; //TODO is not fact to that anime link

    @SerializedName("from")
    private UserBriefResponse fromUser;

    @SerializedName("to")
    private UserBriefResponse toUser;

    public long getId() {
        return id;
    }

    public String getKind() {
        return kind;
    }

    public boolean isRead() {
        return isRead;
    }

    public String getText() {
        return text;
    }

    public String getHtmlText() {
        return htmlText;
    }

    public DateTime getCreatedDate() {
        return createdDate;
    }

    @Nullable
    public AnimeResponse getAnimeResponse() {
        return animeResponse;
    }

    public UserBriefResponse getFromUser() {
        return fromUser;
    }

    public UserBriefResponse getToUser() {
        return toUser;
    }
}
