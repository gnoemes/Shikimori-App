package com.gnoemes.shikimoriapp.presentation.view.similar;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragmentView;

import java.util.List;

@StateStrategyType(AddToEndStrategy.class)
public interface SimilarView extends BaseFragmentView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showList(List<BaseItem> animes);

    void hideList();

    void showNetworkError();

    void hideNetworkError();

    void showEmptyView();

    void hideEmptyView();
}
