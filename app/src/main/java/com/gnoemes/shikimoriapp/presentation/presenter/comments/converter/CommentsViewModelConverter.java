package com.gnoemes.shikimoriapp.presentation.presenter.comments.converter;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.comments.domain.Comment;

import java.util.List;

public interface CommentsViewModelConverter {

    List<BaseItem> convertListFrom(List<Comment> list);

}
