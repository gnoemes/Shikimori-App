package com.gnoemes.shikimoriapp.presentation.view.search;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragmentView;

import java.util.HashMap;
import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface SearchView extends BaseFragmentView {

    @StateStrategyType(SkipStrategy.class)
    void showFilterDialog(HashMap<String, List<FilterItem>> filters);

    @StateStrategyType(AddToEndStrategy.class)
    void showList(List<BaseItem> animes);

    @StateStrategyType(AddToEndStrategy.class)
    void insetMore(List<BaseItem> items);

    void hideList();

    void showNetworkError();

    void hideNetworkError();

    void showEmptyView();

    void hideEmptyView();

    void clearList();

    void onShowPageLoading();

    void onHidePageLoading();

    void addBackButton();
}
