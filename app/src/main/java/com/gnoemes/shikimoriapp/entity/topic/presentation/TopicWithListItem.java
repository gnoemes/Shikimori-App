package com.gnoemes.shikimoriapp.entity.topic.presentation;

import com.gnoemes.shikimoriapp.entity.forum.domain.Forum;
import com.gnoemes.shikimoriapp.entity.topic.domain.TopicType;

import org.joda.time.DateTime;

import java.util.List;

public class TopicWithListItem extends BaseTopicItem {

    private long id;
    private String title;
    private DateTime createdDate;
    private long commentsCount;
    private TopicType type;
    private Forum forum;
    private List<String> imageUrls;
    private boolean isViewed;

    public TopicWithListItem(long id, String title, DateTime createdDate,
                             long commentsCount, TopicType type, Forum forum,
                             List<String> imageUrls, boolean isViewed) {
        this.id = id;
        this.title = title;
        this.createdDate = createdDate;
        this.commentsCount = commentsCount;
        this.type = type;
        this.forum = forum;
        this.imageUrls = imageUrls;
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

    public TopicType getType() {
        return type;
    }

    public Forum getForum() {
        return forum;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public boolean isViewed() {
        return isViewed;
    }
}
