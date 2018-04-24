package com.gnoemes.shikimoriapp.presentation.presenter.common;

import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseView;

import ru.terrakok.cicerone.Router;

public abstract class BaseNavigationPresenter<View extends BaseView> extends BasePresenter<View> {

    private Router localRouter;

    @Override
    public Router getRouter() {
        return localRouter;
    }

    public void setLocalRouter(Router localRouter) {
        this.localRouter = localRouter;
    }

    @Override
    public void onBackPressed() {
        getRouter().exit();
    }
}
