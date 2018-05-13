package com.gnoemes.shikimoriapp.presentation.presenter.fav;

import com.gnoemes.shikimoriapp.entity.rates.domain.RateStatus;
import com.gnoemes.shikimoriapp.presentation.presenter.common.Paginator;

public interface FavoritePaginator extends Paginator {

    void setStatus(RateStatus status);

    void setId(long id);

    boolean isFirstPage();
}
