package com.gnoemes.shikimoriapp.data.repository.comments;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.network.CommentsApi;
import com.gnoemes.shikimoriapp.data.repository.comments.converter.CommentsResponseConverter;
import com.gnoemes.shikimoriapp.entity.comments.domain.Comment;
import com.gnoemes.shikimoriapp.entity.comments.domain.CommentableType;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class CommentsRepositoryImpl implements CommentsRepository {

    private CommentsApi api;
    private CommentsResponseConverter converter;

    @Inject
    public CommentsRepositoryImpl(@NonNull CommentsApi api,
                                  @NonNull CommentsResponseConverter converter) {
        this.api = api;
        this.converter = converter;
    }

    @Override
    public Single<List<Comment>> getComments(long commentableId, int page, int limit) {
        return api.getComments(commentableId, CommentableType.TOPIC.getType(), page, limit)
                .map(converter);
    }
}
