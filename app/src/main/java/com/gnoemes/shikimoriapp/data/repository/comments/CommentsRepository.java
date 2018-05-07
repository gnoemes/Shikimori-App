package com.gnoemes.shikimoriapp.data.repository.comments;

import com.gnoemes.shikimoriapp.entity.comments.domain.Comment;

import java.util.List;

import io.reactivex.Single;

public interface CommentsRepository {


    Single<List<Comment>> getComments(long commentableId, int page, int limit);
}
