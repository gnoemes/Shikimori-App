package com.gnoemes.shikimoriapp.presentation.view.bottom;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.di.base.modules.BaseFragmentModule;
import com.gnoemes.shikimoriapp.entity.main.presentation.LocalCiceroneHolder;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragmentView;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.utils.view.BackButtonListener;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.Router;

/**
 * Tab container for fragments with local routers
 */
public abstract class BottomTabContainer extends MvpAppCompatFragment implements RouterProvider,
        BackButtonListener,
        HasSupportFragmentInjector {

    /**
     * Contains ciceron instances
     */
    @Inject
    protected LocalCiceroneHolder ciceroneHolder;

    /**
     * Child fragment manager
     */
    @Inject
    @Named(BaseFragmentModule.CHILD_FRAGMENT_MANAGER)
    protected FragmentManager childFragmentManager;

    @Inject
    DispatchingAndroidInjector<Fragment> childFragmentInjector;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return childFragmentInjector;
    }

    ///////////////////////////////////////////////////////////////////////////
    // UI METHODS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (childFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            getCicerone().getRouter().replaceScreen(getContainerName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getCicerone().getNavigatorHolder().setNavigator(getNavigator());
    }

    @Override
    public void onPause() {
        getCicerone().getNavigatorHolder().removeNavigator();
        super.onPause();
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    protected abstract Navigator getNavigator();

    @Override
    public Navigator getLocalNavigator() {
        return getNavigator();
    }

    protected String getContainerName() {
        return getTag();
    }


    private Cicerone<Router> getCicerone() {
        return ciceroneHolder.getCicerone(getContainerName());
    }

    /**
     * Returns current router instance
     *
     * @return Router
     */
    @Override
    public Router getLocalRouter() {
        return getCicerone().getRouter();
    }

    @Override
    public boolean onBackPressed() {
        Fragment fragment = childFragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment != null
                && fragment instanceof BaseFragmentView) {
            ((BaseFragmentView) fragment).onBackPressed();
            return true;
        } else {
            return false;
        }
    }
}
