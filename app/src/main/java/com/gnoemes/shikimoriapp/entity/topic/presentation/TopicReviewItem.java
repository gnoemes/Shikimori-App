package com.gnoemes.shikimoriapp.entity.topic.presentation;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.app.domain.LinkedContent;
import com.gnoemes.shikimoriapp.entity.app.domain.LinkedType;
import com.gnoemes.shikimoriapp.entity.forum.domain.Forum;
import com.gnoemes.shikimoriapp.entity.topic.domain.TopicType;
import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief;

import org.joda.time.DateTime;

public class TopicReviewItem extends BaseTopicItem {

    private long id;
    private String title;
    private DateTime createdDate;
    private long commentsCount;
    private UserBrief userBrief;
    private TopicType type;
    private Forum forum;
    @Nullable
    private LinkedContent linkedContent;
    @Nullable
    private LinkedType linkedType;
    private boolean isViewed;

    public TopicReviewItem(long id, String title, DateTime createdDate,
                           long commentsCount, UserBrief userBrief, TopicType type,
                           Forum forum, @Nullable LinkedContent linkedContent,
                           @Nullable LinkedType linkedType,
                           boolean isViewed) {
        this.id = id;
        this.title = title;
        this.createdDate = createdDate;
        this.commentsCount = commentsCount;
        this.userBrief = userBrief;
        this.type = type;
        this.forum = forum;
        this.linkedContent = linkedContent;
        this.linkedType = linkedType;
        this.isViewed = isViewed;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public DateTime getCreatedDate() {
        return createdDate;
    }

    public long getCommentsCount() {
        return commentsCount;
    }

    public UserBrief getUserBrief() {
        return userBrief;
    }

    public TopicType getType() {
        return type;
    }

    public Forum getForum() {
        return forum;
    }

    @Nullable
    public LinkedContent getLinkedContent() {
        return linkedContent;
    }

    @Nullable
    public LinkedType getLinkedType() {
        return linkedType;
    }

    public boolean isViewed() {
        return isViewed;
    }
}
