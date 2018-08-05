package com.gnoemes.shikimoriapp.presentation.view.topic.provider;

import android.content.Context;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.forum.domain.ForumType;
import com.gnoemes.shikimoriapp.entity.topic.domain.TopicType;

import javax.inject.Inject;

public class TopicResourceProviderImpl implements TopicResourceProvider {

    private Context context;

    @Inject
    public TopicResourceProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public String getPlaceholderMessage() {
        return context.getString(R.string.topic_list_empty);
    }

    @Override
    public String getLabelTitle(TopicType topicType) {
        switch (topicType) {
            case COSPLAY:
                return context.getString(R.string.forum_cosplay);
            case REVIEW:
                return context.getString(R.string.forum_review);
            case NEWS:
                return context.getString(R.string.forum_news);
            case COLLECTION:
                return context.getString(R.string.forum_collection);
            default:
                return null;
        }
    }

    @Override
    public String getTopicName(ForumType forumType) {
        switch (forumType) {
            case ALL:
                return context.getString(R.string.forum_all);
            case CLUBS:
                return context.getString(R.string.common_clubs);
            case GAMES:
                return context.getString(R.string.forum_games);
            case CONTESTS:
                return context.getString(R.string.forum_contests);
            case MY_CLUBS:
                return context.getString(R.string.forum_my_clubs);
            case NEWS:
                return context.getString(R.string.forum_news);
            case REVIEWS:
                return context.getString(R.string.forum_reviews);
            case VISUAL_NOVELS:
                return context.getString(R.string.forum_vn);
            case OFF_TOPIC:
                return context.getString(R.string.forum_off_topic);
            case SITE:
                return context.getString(R.string.forum_site);
            case COLLECTIONS:
                return context.getString(R.string.forum_collection);
            case COSPLAY:
                return context.getString(R.string.forum_cosplay);
            case ANIME_AND_MANGA:
                return context.getString(R.string.forum_animanga);
            default:
                return null;
        }
    }

    @Override
    public int getLabelColor(TopicType topicType) {
        switch (topicType) {
            case COSPLAY:
                return R.color.orange_light;
            case REVIEW:
                return R.color.pink_light;
            case NEWS:
                return R.color.orange;
            case COLLECTION:
                return R.color.green;
            default:
                return 0;
        }
    }
}
