package com.gnoemes.shikimoriapp.presentation.view.search;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragmentView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface SearchView extends BaseFragmentView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showFilterDialog();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void initFilterView(@NotNull Type type, HashMap<String, List<FilterItem>> appliedFilters);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void closeFilterDialog();

    @StateStrategyType(AddToEndStrategy.class)
    void showList(List<BaseItem> animes);

    @StateStrategyType(AddToEndStrategy.class)
    void insetMore(List<BaseItem> items);

    void setSpinnerPosition(Type type);

    @StateStrategyType(AddToEndStrategy.class)
    void hideFilterButton();

    @StateStrategyType(AddToEndStrategy.class)
    void showFilterButton();

    void showEmptyListView();

    void hideEmptyListView();

    void hideList();

    void showNetworkError();

    void hideNetworkError();

    void showEmptyView();

    void hideEmptyView();

    void clearList();

    void onShowPageLoading();

    void onHidePageLoading();

    void addBackButton();

    void hideSpinner();
}
