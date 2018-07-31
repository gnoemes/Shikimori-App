package com.gnoemes.shikimoriapp.presentation.view.topic.provider;

import android.support.annotation.ColorRes;

import com.gnoemes.shikimoriapp.entity.forum.domain.ForumType;
import com.gnoemes.shikimoriapp.entity.topic.domain.TopicType;

public interface TopicResourceProvider {

    String getLabelTitle(TopicType topicType);

    String getTopicName(ForumType forumType);

    @ColorRes
    int getLabelColor(TopicType topicType);
}
