package com.gnoemes.shikimoriapp.domain.comments;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.repository.comments.CommentsRepository;
import com.gnoemes.shikimoriapp.entity.comments.domain.Comment;
import com.gnoemes.shikimoriapp.utils.rx.RxUtils;
import com.gnoemes.shikimoriapp.utils.rx.SingleErrorHandler;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class CommentsInteractorImpl implements CommentsInteractor {

    private CommentsRepository repository;
    private SingleErrorHandler singleErrorHandler;
    private RxUtils rxUtils;

    @Inject
    public CommentsInteractorImpl(@NonNull CommentsRepository repository,
                                  @NonNull SingleErrorHandler singleErrorHandler,
                                  @NonNull RxUtils rxUtils) {
        this.repository = repository;
        this.singleErrorHandler = singleErrorHandler;
        this.rxUtils = rxUtils;
    }

    @Override
    public Single<List<Comment>> getComments(long id, int page, int limit) {
        return repository.getComments(id, page, limit)
                .compose((SingleErrorHandler<List<Comment>>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }
}
