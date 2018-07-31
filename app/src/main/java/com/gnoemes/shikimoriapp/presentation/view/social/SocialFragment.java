package com.gnoemes.shikimoriapp.presentation.view.social;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.presentation.presenter.social.SocialPageAdapter;
import com.gnoemes.shikimoriapp.presentation.presenter.social.SocialPresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.social.provider.SocialResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;

import javax.inject.Inject;

import butterknife.BindView;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.Router;

public class SocialFragment extends BaseFragment<SocialPresenter, SocialView> implements SocialView,
        RouterProvider {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

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

    @Inject
    SocialResourceProvider resourceProvider;

    public static SocialFragment newInstance() {
        Bundle args = new Bundle();
        SocialFragment fragment = new SocialFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        SocialPageAdapter socialAdapter = new SocialPageAdapter(getChildFragmentManager(), resourceProvider);
        viewPager.setAdapter(socialAdapter);
        tabLayout.setupWithViewPager(viewPager);
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

    @Override
    public Router getLocalRouter() {
        return ((RouterProvider) getParentFragment()).getLocalRouter();
    }

    @Override
    public Navigator getLocalNavigator() {
        return ((RouterProvider) getParentFragment()).getLocalNavigator();
    }
}
