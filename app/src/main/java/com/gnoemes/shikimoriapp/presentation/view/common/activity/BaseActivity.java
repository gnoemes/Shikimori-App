package com.gnoemes.shikimoriapp.presentation.view.common.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BasePresenter;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.FragmentCallback;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
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
        extends MvpAppCompatActivity implements HasSupportFragmentInjector, BaseView, FragmentCallback {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @Inject
    protected Provider<Presenter> presenterProvider;
    protected Navigator localNavigator;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutActivity());
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        getNavigatorHolder().setNavigator(getLocalNavigator());
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
    protected abstract Navigator getLocalNavigator();

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
    public void setTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void onBackPressed() {
        getPresenter().onBackPressed();
    }
}
