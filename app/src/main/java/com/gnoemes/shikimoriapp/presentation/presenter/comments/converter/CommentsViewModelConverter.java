package com.gnoemes.shikimoriapp.presentation.presenter.comments.converter;

import com.gnoemes.shikimoriapp.entity.comments.domain.Comment;
import com.gnoemes.shikimoriapp.entity.comments.presentation.BaseCommentItem;

import java.util.List;

public interface CommentsViewModelConverter {

    List<BaseCommentItem> convertListFrom(List<Comment> list);

}
