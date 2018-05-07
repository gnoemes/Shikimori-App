package com.gnoemes.shikimoriapp.domain.comments;

import com.gnoemes.shikimoriapp.entity.comments.domain.Comment;

import java.util.List;

import io.reactivex.Single;

public interface CommentsInteractor {

    Single<List<Comment>> getComments(long id, int page, int limit);
}
