package com.gnoemes.shikimoriapp.presentation.view.fav;

import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gnoemes.shikimoriapp.entity.rates.presentation.BaseAnimeRateItem;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragmentView;

import java.util.List;

@StateStrategyType(AddToEndStrategy.class)
public interface FavoriteView extends BaseFragmentView {

    @StateStrategyType(AddToEndStrategy.class)
    void showList(List<BaseAnimeRateItem> items);

    @StateStrategyType(AddToEndStrategy.class)
    void insertMore(List<BaseAnimeRateItem> items);

    @StateStrategyType(SkipStrategy.class)
    void onShowPageLoading();

    @StateStrategyType(SkipStrategy.class)
    void onHidePageLoading();

    @StateStrategyType(SkipStrategy.class)
    void clearList();
}
