package com.gnoemes.shikimoriapp.presentation.view.menu;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gnoemes.shikimoriapp.entity.menu.presentration.BaseMenuItem;
import com.gnoemes.shikimoriapp.entity.menu.presentration.MenuCategoryWithBadgeViewModel;
import com.gnoemes.shikimoriapp.entity.menu.presentration.MenuProfileViewModel;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragmentView;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MenuView extends BaseFragmentView {

    @StateStrategyType(AddToEndStrategy.class)
    void showList(List<BaseMenuItem> menuItems);

    @StateStrategyType(AddToEndStrategy.class)
    void updateBadge(MenuCategoryWithBadgeViewModel viewModel);

    @StateStrategyType(AddToEndStrategy.class)
    void updateUser(MenuProfileViewModel viewModel);

    @StateStrategyType(SkipStrategy.class)
    void showAuthTypeDialog();
}
