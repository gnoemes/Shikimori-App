package com.gnoemes.shikimoriapp.presentation.presenter.main;

import com.arellomobile.mvp.InjectViewState;
import com.crashlytics.android.Crashlytics;
import com.gnoemes.shikimoriapp.domain.notifications.JobSchedulingInteractor;
import com.gnoemes.shikimoriapp.entity.app.domain.NotificationAction;
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens;
import com.gnoemes.shikimoriapp.entity.main.presentation.BottomScreens;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BasePresenter;
import com.gnoemes.shikimoriapp.presentation.view.main.MainView;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;

/**
 * Presenter for MainActivity
 */
@InjectViewState
//TODO normal notification action parsing
public class MainPresenter extends BasePresenter<MainView> {

    /**
     * Router for navigation
     */
    @NonNull
    private Router router;

    private NotificationAction data;

    private JobSchedulingInteractor schedulingInteractor;

    public MainPresenter(@NonNull Router router,
                         JobSchedulingInteractor schedulingInteractor) {
        this.router = router;
        this.schedulingInteractor = schedulingInteractor;
    }

    /**
     * Init screen at first launch
     */
    @Override
    public void initData() {
        getRouter().replaceScreen(BottomScreens.FAVORITE);
        scheduleAnimeNotifications();
    }

    private void scheduleAnimeNotifications() {
        Disposable disposable = schedulingInteractor.planAnimeEpisodesNotifications()
                .subscribe(() -> {
                }, Crashlytics::logException);
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

    public void onNotificationAction(NotificationAction data) {
        this.data = data;
        processAction(data);
    }

    private void processAction(NotificationAction data) {
        if (data != null) {
            switch (data.getType()) {
                case ANIME:
                    getRouter().navigateTo(Screens.ANIME_DETAILS, data.getId());
                    break;
            }
        }
    }

    //TODO remove
    public void onReady() {
        processAction(data);
    }
}
