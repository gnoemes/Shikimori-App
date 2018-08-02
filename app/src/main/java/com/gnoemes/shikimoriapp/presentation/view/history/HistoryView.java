package com.gnoemes.shikimoriapp.presentation.view.history;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragmentView;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface HistoryView extends BaseFragmentView {

    @StateStrategyType(AddToEndStrategy.class)
    void showList(List<BaseItem> animes);

    void showNetworkErrorView();

    void hideNetworkErrorView();

}
