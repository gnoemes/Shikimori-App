package com.gnoemes.shikimoriapp.entity.topic.domain;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.app.domain.LinkedContent;
import com.gnoemes.shikimoriapp.entity.app.domain.LinkedType;
import com.gnoemes.shikimoriapp.entity.forum.domain.Forum;
import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief;

import org.joda.time.DateTime;

public class Topic {

    private long id;
    private String title;
    @Nullable
    private String description;
    @Nullable
    private String descriptionHtml;
    @Nullable
    private String footerHtml;
    private DateTime createdDate;
    private long commentsCount;
    private Forum forum;
    private UserBrief userBrief;
    private TopicType topicType;
    @Nullable
    private LinkedType type;
    @Nullable
    private LinkedContent linkedContent;
    private boolean isViewed;

    public Topic(long id, String title, @Nullable String description,
                 @Nullable String descriptionHtml, @Nullable String footerHtml, DateTime createdDate,
                 long commentsCount, Forum forum, UserBrief userBrief,
                 TopicType topicType, @Nullable LinkedType type, @Nullable LinkedContent linkedContent,
                 boolean isViewed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.descriptionHtml = descriptionHtml;
        this.footerHtml = footerHtml;
        this.createdDate = createdDate;
        this.commentsCount = commentsCount;
        this.forum = forum;
        this.userBrief = userBrief;
        this.topicType = topicType;
        this.type = type;
        this.linkedContent = linkedContent;
        this.isViewed = isViewed;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @Nullable
    public String getDescriptionHtml() {
        return descriptionHtml;
    }

    @Nullable
    public String getFooterHtml() {
        return footerHtml;
    }

    public DateTime getCreatedDate() {
        return createdDate;
    }

    public long getCommentsCount() {
        return commentsCount;
    }

    public Forum getForum() {
        return forum;
    }

    public UserBrief getUserBrief() {
        return userBrief;
    }

    public TopicType getTopicType() {
        return topicType;
    }

    @Nullable
    public LinkedType getType() {
        return type;
    }

    @Nullable
    public LinkedContent getLinkedContent() {
        return linkedContent;
    }

    public boolean isViewed() {
        return isViewed;
    }
}
