package com.gnoemes.shikimoriapp.presentation.presenter.topic.list.converter;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.DoubleDividerItem;
import com.gnoemes.shikimoriapp.entity.topic.domain.Topic;
import com.gnoemes.shikimoriapp.entity.topic.presentation.TopicClubItem;
import com.gnoemes.shikimoriapp.entity.topic.presentation.TopicItem;
import com.gnoemes.shikimoriapp.entity.topic.presentation.TopicLinkOnlyItem;
import com.gnoemes.shikimoriapp.entity.topic.presentation.TopicWithDescriptionItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TopicViewModelConverterImpl implements TopicViewModelConverter {

    @Inject
    public TopicViewModelConverterImpl() {
    }

    @Override
    public List<BaseItem> convertListFrom(List<Topic> list) {
        List<BaseItem> items = new ArrayList<>();

        for (Topic topic : list) {
            items.add(convertTopic(topic));
            items.add(new DoubleDividerItem());
        }

        return items;
    }

    private BaseItem convertTopic(Topic topic) {
        switch (topic.getTopicType()) {
            case NEWS_LINK_ONLY:
                return convertLinkOnly(topic);
            case REVIEW:
                return convertDefaultTopic(topic);
            case ANIME:
                return convertDescriptionItem(topic);
            case MANGA:
                return convertDescriptionItem(topic);
            case PERSON:
                return convertDescriptionItem(topic);
            case CHARACTER:
                return convertDescriptionItem(topic);
            case CONTEST:
                return convertDescriptionItem(topic);
            case CLUB_USER:
                return convertClubItem(topic);
            default:
                return convertDefaultTopic(topic);
        }
    }

    private BaseItem convertClubItem(Topic topic) {
        return new TopicClubItem(topic.getId(),
                topic.getTitle(),
                topic.getCreatedDate(),
                topic.getCommentsCount(),
                topic.getUserBrief(),
                topic.getTopicType(),
                topic.getForum(),
                topic.getLinkedContent(),
                topic.getType(),
                topic.isViewed());
    }

    private BaseItem convertDescriptionItem(Topic topic) {
        return new TopicWithDescriptionItem(topic.getId(),
                topic.getTitle(),
                topic.getDescription(),
                topic.getCreatedDate(),
                topic.getCommentsCount(),
                topic.getTopicType(),
                topic.getForum(),
                topic.getLinkedContent(),
                topic.getType(),
                topic.isViewed());
    }

    private BaseItem convertDefaultTopic(Topic topic) {
        return new TopicItem(topic.getId(),
                topic.getTitle(),
                topic.getCreatedDate(),
                topic.getCommentsCount(),
                topic.getUserBrief(),
                topic.getTopicType(),
                topic.getForum(),
                topic.getLinkedContent(),
                topic.getType(),
                topic.isViewed());
    }

    private BaseItem convertLinkOnly(Topic topic) {
        return new TopicLinkOnlyItem(topic.getId(),
                topic.getTitle(),
                topic.getCreatedDate(),
                topic.getCommentsCount(),
                topic.getTopicType(),
                topic.getForum(),
                topic.getLinkedContent(),
                topic.getType(),
                topic.isViewed());
    }
}
