package com.gnoemes.shikimoriapp.presentation.presenter.history.paginator;

import com.gnoemes.shikimoriapp.presentation.presenter.common.Paginator;

public interface UserHistoryPaginator extends Paginator {

    void setId(long id);

    boolean isFirstPage();
}
