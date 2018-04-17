package com.gnoemes.shikimoriapp.presentation.presenter.common;

import com.arellomobile.mvp.MvpPresenter;
import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseView;

import io.reactivex.annotations.NonNull;
import ru.terrakok.cicerone.Router;

public abstract class BasePresenter<View extends BaseView> extends MvpPresenter<View> {

    /**
     * Triggered on action back pressed
     */
    public abstract void onBackPressed();

    /**
     * Returns current router
     *
     * @return Router local router
     */
    @NonNull
    public abstract Router getRouter();

    /**
     * Init primal data
     */
    public abstract void initData();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().initToolbar();
        initData();
    }
}
