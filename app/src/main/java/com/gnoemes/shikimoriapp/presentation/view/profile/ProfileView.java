package com.gnoemes.shikimoriapp.presentation.view.profile;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.BaseProfileItem;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragmentView;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface ProfileView extends BaseFragmentView {

    @StateStrategyType(AddToEndStrategy.class)
    void setProfile(List<BaseProfileItem> items);

    @StateStrategyType(AddToEndStrategy.class)
    void updateHead(BaseProfileItem baseProfileItem);

    @StateStrategyType(AddToEndStrategy.class)
    void updateSocial(BaseProfileItem baseProfileItem);

    @StateStrategyType(AddToEndStrategy.class)
    void updateRates(BaseProfileItem baseProfileItem);

    @StateStrategyType(AddToEndStrategy.class)
    void updateOther(BaseProfileItem item);

    @StateStrategyType(AddToEndStrategy.class)
    void addExitMenu();

    @StateStrategyType(SkipStrategy.class)
    void showLogoutDialog();

}
