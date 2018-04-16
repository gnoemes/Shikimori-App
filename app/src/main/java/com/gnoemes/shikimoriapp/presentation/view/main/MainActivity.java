package com.gnoemes.shikimoriapp.presentation.view.main;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BasePresenter;
import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseActivity;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    @LayoutRes
    protected int getLayoutActivity() {
        return R.layout.activity_main;
    }

    @Override
    protected Navigator getLocalNavigator() {
        return null;
    }

    @Override
    protected NavigatorHolder getNavigatorHolder() {
        return null;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void initToolbar() {

    }

    @Override
    public void onSetTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onHideLoading() {

    }
}
