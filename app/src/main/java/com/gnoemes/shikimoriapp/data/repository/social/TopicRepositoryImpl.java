package com.gnoemes.shikimoriapp.data.repository.social;

import com.gnoemes.shikimoriapp.data.network.TopicApi;
import com.gnoemes.shikimoriapp.data.repository.social.converter.ForumResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.social.converter.TopicResponseConverter;
import com.gnoemes.shikimoriapp.entity.forum.domain.Forum;
import com.gnoemes.shikimoriapp.entity.forum.domain.ForumType;
import com.gnoemes.shikimoriapp.entity.topic.domain.Topic;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class TopicRepositoryImpl implements TopicRepository {

    private TopicApi api;
    private TopicResponseConverter converter;
    private ForumResponseConverter forumResponseConverter;

    @Inject
    public TopicRepositoryImpl(TopicApi api,
                               TopicResponseConverter converter,
                               ForumResponseConverter forumResponseConverter) {
        this.api = api;
        this.converter = converter;
        this.forumResponseConverter = forumResponseConverter;
    }

    @Override
    public Single<List<Topic>> getTopicList(int page, int limit, ForumType type) {
        return api.getTopics(page, limit, type.getType())
                .map(converter);
    }

    @Override
    public Single<Topic> getTopic(long id) {
        return api.getTopic(id)
                .map(topicResponse -> converter.convertResponse(topicResponse));
    }

    @Override
    public Single<List<Forum>> getForums() {
        return api.getForums()
                .map(forumResponseConverter);
    }
}
