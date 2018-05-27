package com.gnoemes.shikimoriapp.data.repository.comments.converter;

import com.gnoemes.shikimoriapp.entity.comments.data.CommentResponse;
import com.gnoemes.shikimoriapp.entity.comments.domain.Comment;

import java.util.List;

import io.reactivex.functions.Function;

public interface CommentsResponseConverter extends Function<List<CommentResponse>, List<Comment>> {
    Comment convertResponse(CommentResponse response);
}
