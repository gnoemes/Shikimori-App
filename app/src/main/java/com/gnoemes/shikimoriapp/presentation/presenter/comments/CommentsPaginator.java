package com.gnoemes.shikimoriapp.presentation.presenter.comments;

import com.gnoemes.shikimoriapp.presentation.presenter.common.Paginator;

public interface CommentsPaginator extends Paginator {
    void setId(long id);

    boolean isFirstPage();

}
