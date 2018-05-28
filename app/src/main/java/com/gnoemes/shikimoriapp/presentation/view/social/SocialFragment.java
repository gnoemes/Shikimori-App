package com.gnoemes.shikimoriapp.presentation.view.social;

import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.presentation.presenter.social.SocialPresenter;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;

public class SocialFragment extends BaseFragment<SocialPresenter, SocialView> implements SocialView {

    @InjectPresenter
    SocialPresenter presenter;

    @ProvidePresenter
    SocialPresenter providePresenter() {
        presenter = presenterProvider.get();

        if (getParentFragment() != null) {
            presenter.setLocalRouter(((RouterProvider) getParentFragment()).getLocalRouter());
        }

        return presenter;
    }

    public static SocialFragment newInstance() {
        Bundle args = new Bundle();
        SocialFragment fragment = new SocialFragment();
        fragment.setArguments(args);
        return fragment;
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected SocialPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_social;
    }

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

}
