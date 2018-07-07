package com.gnoemes.shikimoriapp.presentation.view.player;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseNetworkView;

@StateStrategyType(SkipStrategy.class)
public interface WebPlayerView extends BaseNetworkView {

    void playVideo(String url);

    void showError();
}
