package com.gnoemes.shikimoriapp.presentation.presenter.calendar;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.domain.calendar.CalendarInteractor;
import com.gnoemes.shikimoriapp.entity.app.domain.BaseException;
import com.gnoemes.shikimoriapp.entity.app.domain.NetworkException;
import com.gnoemes.shikimoriapp.entity.calendar.presentation.CalendarItemViewModel;
import com.gnoemes.shikimoriapp.presentation.presenter.calendar.converter.CalendarViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.calendar.provider.CalendarResourceProvider;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.view.calendar.CalendarView;

import java.util.List;

import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class CalendarPresenter extends BaseNetworkPresenter<CalendarView> {

    private Router localRouter;
    private CalendarInteractor interactor;
    private CalendarResourceProvider resourceProvider;
    private CalendarViewModelConverter viewModelConverter;

    public CalendarPresenter(CalendarInteractor interactor,
                             CalendarResourceProvider resourceProvider,
                             CalendarViewModelConverter viewModelConverter) {
        this.interactor = interactor;
        this.resourceProvider = resourceProvider;
        this.viewModelConverter = viewModelConverter;
    }

    /**
     * Init primal data
     */
    @Override
    public void initData() {
        getViewState().setTitle(resourceProvider.getTitle());
        getViewState().initList();
        loadCalendarData();
    }

    /**
     * Get calendar from api
     */
    public void loadCalendarData() {
        getViewState().onShowLoading();
        getViewState().hideNetworkErrorView();

        Disposable subscription = interactor.getCalendarData()
                .doOnEvent((calendarItems, throwable) -> getViewState().onHideLoading())
                .map(viewModelConverter)
                .subscribe(this::showList, this::processErrors);

        unsubscribeOnDestroy(subscription);
    }

    /**
     * Show list on fragment
     */
    private void showList(List<CalendarItemViewModel> items) {
        getViewState().showCalendarData(items);
    }

    /**
     * Catch errors
     */
    @Override
    protected void processErrors(Throwable throwable) {
        BaseException baseException = (BaseException) throwable;
        switch (baseException.getTag()) {
            case NetworkException.TAG:
                processNetworkError(throwable);
                break;
            default:
                super.processErrors(throwable);
        }
    }

    /**
     * Catch network errors
     */
    private void processNetworkError(Throwable throwable) {
        getViewState().showNetworkErrorView();
        getViewState().hideList();
    }

    @Override
    public void onBackPressed() {
        getRouter().exit();
    }

    @Override
    public Router getRouter() {
        return localRouter;
    }

    public void setLocalRouter(Router localRouter) {
        this.localRouter = localRouter;
    }

    public void onAnimeClicked(long id) {
        Log.i("DEVE", "onAnimeClicked: " + id);
    }
}
