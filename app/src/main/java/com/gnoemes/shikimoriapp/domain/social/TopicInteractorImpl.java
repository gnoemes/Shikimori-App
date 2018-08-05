package com.gnoemes.shikimoriapp.domain.social;

import com.gnoemes.shikimoriapp.data.repository.social.TopicRepository;
import com.gnoemes.shikimoriapp.entity.forum.domain.Forum;
import com.gnoemes.shikimoriapp.entity.forum.domain.ForumType;
import com.gnoemes.shikimoriapp.entity.topic.domain.Topic;
import com.gnoemes.shikimoriapp.utils.rx.RxUtils;
import com.gnoemes.shikimoriapp.utils.rx.SingleErrorHandler;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class TopicInteractorImpl implements TopicInteractor {

    private TopicRepository repository;
    private SingleErrorHandler singleErrorHandler;
    private RxUtils rxUtils;

    @Inject
    public TopicInteractorImpl(TopicRepository repository,
                               SingleErrorHandler singleErrorHandler,
                               RxUtils rxUtils) {
        this.repository = repository;
        this.singleErrorHandler = singleErrorHandler;
        this.rxUtils = rxUtils;
    }

    @Override
    public Single<List<Topic>> getTopicList(int page, int limit, ForumType type) {
        return repository.getTopicList(page, limit, type)
                .compose((SingleErrorHandler<List<Topic>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }

    @Override
    public Single<Topic> getTopic(long id) {
        return repository.getTopic(id)
                .compose((SingleErrorHandler<Topic>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }

    @Override
    public Single<List<Forum>> getForums() {
        return repository.getForums()
                .compose((SingleErrorHandler<List<Forum>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }
}
