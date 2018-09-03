package com.gnoemes.shikimoriapp.presentation.presenter.main;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.entity.main.presentation.BottomScreens;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BasePresenter;
import com.gnoemes.shikimoriapp.presentation.view.main.MainView;

import io.reactivex.annotations.NonNull;
import ru.terrakok.cicerone.Router;

/**
 * Presenter for MainActivity
 */
@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {

    /**
     * Router for navigation
     */
    @NonNull
    private Router router;

    public MainPresenter(@NonNull Router router) {
        this.router = router;
    }

    /**
     * Init screen at first launch
     */
    @Override
    public void initData() {
        getRouter().replaceScreen(BottomScreens.FAVORITE);
    }

    @Override
    public void onBackPressed() {
        getRouter().exit();
    }

    @Override
    @NonNull
    public Router getRouter() {
        return router;
    }

    public void onFavoriteSelected() {
        getRouter().replaceScreen(BottomScreens.FAVORITE);
    }

    public void onCalendarSelected() {
        getRouter().replaceScreen(BottomScreens.CALENDAR);
    }

    public void onSearchSelected() {
        getRouter().replaceScreen(BottomScreens.SEARCH);
    }

    public void onSocialSelected() {
        getRouter().replaceScreen(BottomScreens.SOCIAL);
    }

    public void onMenuSelected() {
        getViewState().clearMenuBackStack();
        getRouter().replaceScreen(BottomScreens.MENU);
    }

    public void onFavoriteReSelected() {
        getViewState().clearFavoriteBackStack();
    }

    public void onCalendarReSelected() {
        getViewState().clearCalendarBackStack();
    }

    public void onSearchReSelected() {
        getViewState().clearSearchBackStack();
    }

    public void onSocialReSelected() {
        getViewState().clearSocialBackStack();
    }

    public void onMenuReSelected() {
        getViewState().clearMenuBackStack();
    }
}
