package com.gnoemes.shikimoriapp.presentation.presenter.userhistory.paginator;

import com.gnoemes.shikimoriapp.presentation.presenter.common.Paginator;

public interface UserHistoryPaginator extends Paginator {

    void setId(long id);

    boolean isFirstPage();
}
