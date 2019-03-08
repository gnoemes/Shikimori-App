package com.gnoemes.shikimoriapp.presentation.view.main;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseView;

/**
 * View for MainActivity
 */
@StateStrategyType(AddToEndStrategy.class)
public interface MainView extends BaseView {

    /**
     * Init bottom navigation
     */
    @StateStrategyType(AddToEndSingleStrategy.class)
    void initBottomNavigation();

    @StateStrategyType(SkipStrategy.class)
    void clearMenuBackStack();

    @StateStrategyType(SkipStrategy.class)
    void clearSocialBackStack();

    @StateStrategyType(SkipStrategy.class)
    void clearSearchBackStack();

    @StateStrategyType(SkipStrategy.class)
    void clearCalendarBackStack();

    @StateStrategyType(SkipStrategy.class)
    void clearFavoriteBackStack();

}
