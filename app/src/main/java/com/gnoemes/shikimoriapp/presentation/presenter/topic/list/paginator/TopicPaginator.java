package com.gnoemes.shikimoriapp.presentation.presenter.topic.list.paginator;

import com.gnoemes.shikimoriapp.entity.forum.domain.ForumType;
import com.gnoemes.shikimoriapp.presentation.presenter.common.Paginator;

public interface TopicPaginator extends Paginator {

    void setForumType(ForumType type);

    boolean isFirstPage();

}
