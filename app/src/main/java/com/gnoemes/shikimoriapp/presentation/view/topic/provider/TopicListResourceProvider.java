package com.gnoemes.shikimoriapp.presentation.view.topic.provider;

import android.support.annotation.ColorRes;

import com.gnoemes.shikimoriapp.entity.forum.domain.ForumType;
import com.gnoemes.shikimoriapp.entity.topic.domain.TopicType;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.AdapterResourceProvider;

public interface TopicListResourceProvider extends AdapterResourceProvider {

    String getLabelTitle(TopicType topicType);

    String getTopicName(ForumType forumType);

    @ColorRes
    int getLabelColor(TopicType topicType);
}
