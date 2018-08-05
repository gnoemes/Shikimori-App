package com.gnoemes.shikimoriapp.presentation.view.topic.list;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragmentView;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface TopicListView extends BaseFragmentView {
    @StateStrategyType(AddToEndStrategy.class)
    void showList(List<BaseItem> baseItems);

    @StateStrategyType(AddToEndStrategy.class)
    void insetMore(List<BaseItem> baseItems);

    @StateStrategyType(SkipStrategy.class)
    void onShowPageLoading();

    @StateStrategyType(SkipStrategy.class)
    void onHidePageLoading();

    void clearList();

    void showNetworkError();

    void hideNetworkError();

}
