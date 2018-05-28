package com.gnoemes.shikimoriapp.presentation.presenter.social;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.view.social.SocialView;

@InjectViewState
public class SocialPresenter extends BaseNetworkPresenter<SocialView> {

    @Override
    public void initData() {
        getViewState().setTitle(R.string.common_home);
    }
}
