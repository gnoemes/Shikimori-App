package com.gnoemes.shikimoriapp.presentation.view.common.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.inputmethod.InputMethodManager;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.di.base.modules.BaseActivityModule;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BasePresenter;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.ActivityCallback;
import com.gnoemes.shikimoriapp.utils.view.BackButtonListener;
import com.gnoemes.shikimoriapp.utils.view.ThemeHelper;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;

/**
 * Base mvp activity with auto dagger injection
 *
 * @param <Presenter>
 * @param <View>
 */
public abstract class BaseActivity<Presenter extends BasePresenter, View extends BaseView>
        extends MvpAppCompatActivity implements HasSupportFragmentInjector, BaseView, ActivityCallback {

    @Inject
    protected Provider<Presenter> presenterProvider;
    protected Navigator localNavigator;

    @Inject
    @Named(BaseActivityModule.ACTIVITY_FRAGMENT_MANAGER)
    protected FragmentManager fragmentManager;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        setTheme(ThemeHelper.applyTheme(getBaseContext()));
        super.onCreate(savedInstanceState);
        setContentView(getLayoutActivity());
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        getNavigatorHolder().setNavigator(getNavigator());
    }

    @Override
    protected void onPause() {
        getNavigatorHolder().removeNavigator();
        super.onPause();
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Return current layout id from child activity
     *
     * @return R.layout.
     */
    @LayoutRes
    protected abstract int getLayoutActivity();

    /**
     * Returns current navigator from child activity
     */
    protected abstract Navigator getNavigator();

    /**
     * Returns current navigator holder
     */
    protected abstract NavigatorHolder getNavigatorHolder();

    /**
     * Returns current presenter
     */
    protected abstract Presenter getPresenter();

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void hideSoftInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void onShowError(String error) {

    }

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onHideLoading() {

    }

    @Override
    public void onBackPressed() {
        Fragment fragment = fragmentManager.findFragmentById(R.id.activity_container);
        if (fragment != null
                && fragment instanceof BackButtonListener
                && ((BackButtonListener) fragment).onBackPressed()) {
            return;
        } else {
            getPresenter().onBackPressed();
        }
    }
}

