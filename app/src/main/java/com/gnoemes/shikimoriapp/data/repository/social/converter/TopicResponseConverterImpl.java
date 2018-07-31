package com.gnoemes.shikimoriapp.data.repository.social.converter;

import android.text.TextUtils;

import com.gnoemes.shikimoriapp.data.repository.app.converter.LinkedContentResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.user.converter.UserBriefResponseConverter;
import com.gnoemes.shikimoriapp.entity.app.domain.LinkedType;
import com.gnoemes.shikimoriapp.entity.topic.data.TopicResponse;
import com.gnoemes.shikimoriapp.entity.topic.domain.Topic;
import com.gnoemes.shikimoriapp.entity.topic.domain.TopicType;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TopicResponseConverterImpl implements TopicResponseConverter {

    private ForumResponseConverter forumResponseConverter;
    private UserBriefResponseConverter userBriefResponseConverter;
    private LinkedContentResponseConverter linkedContentResponseConverter;

    @Inject
    public TopicResponseConverterImpl(ForumResponseConverter forumResponseConverter,
                                      UserBriefResponseConverter userBriefResponseConverter,
                                      LinkedContentResponseConverter linkedContentResponseConverter) {
        this.forumResponseConverter = forumResponseConverter;
        this.userBriefResponseConverter = userBriefResponseConverter;
        this.linkedContentResponseConverter = linkedContentResponseConverter;
    }

    @Override
    public List<Topic> apply(List<TopicResponse> responses) {
        List<Topic> items = new ArrayList<>();

        for (TopicResponse response : responses) {
            items.add(convertResponse(response));
        }

        return items;
    }

    @Override
    public Topic convertResponse(TopicResponse response) {
        return new Topic(response.getId(),
                response.getTitle(),
                response.getDescription(),
                response.getBodyHtml(),
                response.getFooterHtml(),
                response.getCreatedDate(),
                response.getCommentsCount(),
                forumResponseConverter.convertResponse(response.getForumResponse()),
                userBriefResponseConverter.apply(response.getUserBriefResponse()),
                convertTopicType(response.getType(), response.getDescription(), response.getBodyHtml()),
                convertLinkedType(response.getLinkedType()),
                linkedContentResponseConverter.convertResponse(response.getLinkedContentResponse()),
                response.isViewed()
        );
    }

    //HTML if body contains only BB codes (they are not include in html)
    private TopicType convertTopicType(String type, String description, String html) {
        if (type == null) {
            return TopicType.DEFAULT;
        }

        for (TopicType topicType : TopicType.values()) {
            if (topicType.isEqualType(type)) {
                if ((topicType == TopicType.NEWS || topicType == TopicType.NEWS_LINK_ONLY)
                        && TextUtils.isEmpty(description) && TextUtils.isEmpty(html)) {
                    return TopicType.NEWS_LINK_ONLY;
                }

                return topicType;
            }
        }

        return TopicType.DEFAULT;
    }

    private LinkedType convertLinkedType(String type) {
        if (type == null) {
            return null;
        }

        for (LinkedType linkedType : LinkedType.values()) {
            if (linkedType.isEqualType(type)) {
                return linkedType;
            }
        }

        return LinkedType.UNKNOWN;
    }
}
