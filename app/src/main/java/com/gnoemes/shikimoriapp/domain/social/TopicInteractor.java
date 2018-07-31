package com.gnoemes.shikimoriapp.domain.social;

import com.gnoemes.shikimoriapp.entity.forum.domain.Forum;
import com.gnoemes.shikimoriapp.entity.forum.domain.ForumType;
import com.gnoemes.shikimoriapp.entity.topic.domain.Topic;

import java.util.List;

import io.reactivex.Single;

public interface TopicInteractor {

    Single<List<Topic>> getTopicList(int page, int limit, ForumType type);

    Single<Topic> getTopic(long id);

    Single<List<Forum>> getForums();
}
