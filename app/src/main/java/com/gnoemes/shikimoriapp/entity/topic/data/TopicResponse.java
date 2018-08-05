package com.gnoemes.shikimoriapp.entity.topic.data;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.app.data.LinkedContentResponse;
import com.gnoemes.shikimoriapp.entity.forum.data.ForumResponse;
import com.gnoemes.shikimoriapp.entity.user.data.UserBriefResponse;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

public class TopicResponse {
    @SerializedName("id")
    private long id;
    @SerializedName("topic_title")
    private String title;
    @SerializedName("body")
    private String description;
    @SerializedName("html_body")
    private String bodyHtml;
    @SerializedName("html_footer")
    private String footerHtml;
    @SerializedName("created_at")
    private DateTime createdDate;
    @SerializedName("comments_count")
    private long commentsCount;
    @SerializedName("forum")
    private ForumResponse forumResponse;
    @SerializedName("user")
    private UserBriefResponse userBriefResponse;
    @SerializedName("type")
    private String type;
    @SerializedName("linked_type")
    @Nullable
    private String linkedType;
    @SerializedName("linked")
    @Nullable
    private LinkedContentResponse linkedContentResponse;
    @SerializedName("viewed")
    private boolean isViewed;

    public TopicResponse(long id,
                         String title,
                         String description,
                         String bodyHtml,
                         String footerHtml,
                         DateTime createdDate,
                         long commentsCount,
                         ForumResponse forumResponse,
                         UserBriefResponse userBriefResponse,
                         String type,
                         @Nullable String linkedType,
                         @Nullable LinkedContentResponse linkedContentResponse,
                         boolean isViewed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.bodyHtml = bodyHtml;
        this.footerHtml = footerHtml;
        this.createdDate = createdDate;
        this.commentsCount = commentsCount;
        this.forumResponse = forumResponse;
        this.userBriefResponse = userBriefResponse;
        this.type = type;
        this.linkedType = linkedType;
        this.linkedContentResponse = linkedContentResponse;
        this.isViewed = isViewed;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public String getFooterHtml() {
        return footerHtml;
    }

    public DateTime getCreatedDate() {
        return createdDate;
    }

    public long getCommentsCount() {
        return commentsCount;
    }

    public ForumResponse getForumResponse() {
        return forumResponse;
    }

    public UserBriefResponse getUserBriefResponse() {
        return userBriefResponse;
    }

    public String getType() {
        return type;
    }

    @Nullable
    public String getLinkedType() {
        return linkedType;
    }

    @Nullable
    public LinkedContentResponse getLinkedContentResponse() {
        return linkedContentResponse;
    }

    public boolean isViewed() {
        return isViewed;
    }
}
