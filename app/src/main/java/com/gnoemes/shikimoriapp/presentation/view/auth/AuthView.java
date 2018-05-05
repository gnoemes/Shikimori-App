package com.gnoemes.shikimoriapp.presentation.view.auth;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragmentView;

public interface AuthView extends BaseFragmentView {
    @StateStrategyType(SkipStrategy.class)
    void onSignIn();

    @StateStrategyType(SkipStrategy.class)
    void onSignUp();
}
