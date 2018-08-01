package com.gnoemes.shikimoriapp.presentation.view.userhistory;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragmentView;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface UserHistoryView extends BaseFragmentView {

    @StateStrategyType(AddToEndStrategy.class)
    void showList(List<BaseItem> items);

    @StateStrategyType(AddToEndStrategy.class)
    void insertMore(List<BaseItem> items);

    @StateStrategyType(SkipStrategy.class)
    void onShowPageLoading();

    @StateStrategyType(SkipStrategy.class)
    void onHidePageLoading();

    @StateStrategyType(SkipStrategy.class)
    void clearList();
}
