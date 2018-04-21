package com.gnoemes.shikimoriapp.presentation.view.calendar;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gnoemes.shikimoriapp.entity.calendar.presentation.CalendarItemViewModel;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragmentView;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface CalendarView extends BaseFragmentView {

    /**
     * Show calendar data list
     */
    @StateStrategyType(AddToEndStrategy.class)
    void showCalendarData(List<CalendarItemViewModel> items);

    /**
     * Hide list
     */
    void hideList();

    /**
     * Show net error view
     */
    void showNetworkErrorView();

    /**
     * Hide net error view
     */
    void hideNetworkErrorView();


}
