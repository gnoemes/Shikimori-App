package com.gnoemes.shikimoriapp.presentation.presenter.fav;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.domain.app.AnalyticsInteractor;
import com.gnoemes.shikimoriapp.domain.app.UserSettingsInteractor;
import com.gnoemes.shikimoriapp.domain.rates.UserRatesInteractor;
import com.gnoemes.shikimoriapp.domain.user.UserInteractor;
import com.gnoemes.shikimoriapp.entity.app.domain.AnalyticsEvent;
import com.gnoemes.shikimoriapp.entity.app.domain.BaseException;
import com.gnoemes.shikimoriapp.entity.app.domain.ContentException;
import com.gnoemes.shikimoriapp.entity.app.domain.HttpStatusCode;
import com.gnoemes.shikimoriapp.entity.app.domain.NetworkException;
import com.gnoemes.shikimoriapp.entity.app.domain.ServiceCodeException;
import com.gnoemes.shikimoriapp.entity.app.domain.UserSettings;
import com.gnoemes.shikimoriapp.entity.app.domain.UserStatus;
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens;
import com.gnoemes.shikimoriapp.entity.main.domain.Constants;
import com.gnoemes.shikimoriapp.entity.rates.domain.AnimeRate;
import com.gnoemes.shikimoriapp.entity.rates.domain.PlaceholderType;
import com.gnoemes.shikimoriapp.entity.rates.domain.RateStatus;
import com.gnoemes.shikimoriapp.entity.rates.domain.UserRate;
import com.gnoemes.shikimoriapp.entity.rates.presentation.AnimeRatePlaceholder;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.common.ViewController;
import com.gnoemes.shikimoriapp.presentation.presenter.fav.converter.RateStatusCountConverter;
import com.gnoemes.shikimoriapp.presentation.view.fav.FavoriteView;
import com.gnoemes.shikimoriapp.presentation.view.fav.converter.AnimeRateViewModelConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class FavoritePresenter extends BaseNetworkPresenter<FavoriteView> {

    private UserRatesInteractor interactor;
    private UserSettingsInteractor settingsInteractor;
    private UserInteractor userInteractor;
    private AnimeRateViewModelConverter converter;
    private AnalyticsInteractor analyticsInteractor;
    private RateStatusCountConverter statusCountConverter;

    private FavoritePaginator paginator;

    private RateStatus currentStatus;
    private Long userId;
    private UserSettings settings;

    private ViewController<AnimeRate> controller = new ViewController<AnimeRate>() {
        @Override
        public void showEmptyError(boolean show, Throwable throwable) {
            if (show) {
                processErrors(throwable);
            } else {
                getViewState().hideNetworkErrorView();
            }
        }

        @Override
        public void showEmptyView(boolean show) {
            if (show) {
                getViewState().showList(Collections.emptyList());
            }
        }

        @Override
        public void showList(boolean show, List<AnimeRate> list) {
            if (show) {
                if (paginator.isFirstPage()) {
                    getViewState().showList(converter.convertFrom(list));
                } else {
                    getViewState().insertMore(converter.convertFrom(list));
                }
            } else {
                getViewState().clearList();
            }
        }

        @Override
        public void showRefreshProgress(boolean show) {
            if (show) {
                getViewState().onShowLoading();
            } else {
                getViewState().onHideLoading();
            }
        }

        @Override
        public void showPageProgress(boolean show) {
            if (show) {
                getViewState().onShowPageLoading();
            } else {
                getViewState().onHidePageLoading();
            }
        }

        @Override
        public void showEmptyProgress(boolean show) {
            if (show) {
                getViewState().onShowLoading();
            } else {
                getViewState().onHideLoading();
            }
        }

        @Override
        public void showError(Throwable throwable) {
            processErrors(throwable);
        }
    };


    public FavoritePresenter(@NonNull UserRatesInteractor interactor,
                             @NonNull UserSettingsInteractor settingsInteractor,
                             @NonNull UserInteractor userInteractor,
                             @NonNull AnimeRateViewModelConverter converter,
                             @NonNull RateStatusCountConverter statusCountConverter,
                             @NonNull AnalyticsInteractor analyticsInteractor) {
        this.interactor = interactor;
        this.settingsInteractor = settingsInteractor;
        this.userInteractor = userInteractor;
        this.converter = converter;
        this.statusCountConverter = statusCountConverter;
        this.analyticsInteractor = analyticsInteractor;
    }

    @Override
    public void initData() {
        currentStatus = RateStatus.WATCHING;
        if (userId == null) {
            loadUserSettings();
        } else {
            getViewState().addBackArrow();
            loadUserProfile();
            initPaginator();
        }
    }

    private void loadUserProfile() {
        if (userId != null && userId != Constants.NO_ID) {
            Disposable disposable = userInteractor.getUserProfile(userId)
                    .map(statusCountConverter)
                    .subscribe(this::updateRateStatusCount, this::processErrors);

            unsubscribeOnDestroy(disposable);
        }
    }

    private void updateRateStatusCount(List<String> rates) {
        getViewState().updateRateItems(rates);
        getViewState().setSpinnerPosition(currentStatus);
    }

    public void loadNextPage() {
        if (paginator != null) {
            paginator.loadNewPage();
        }
    }

    public void onRefresh() {
        if (paginator != null) {
            paginator.refresh();
            loadUserProfile();
        }
    }

    public void onStatusChanged(RateStatus currentStatus) {
        this.currentStatus = currentStatus;
        getViewState().setSpinnerPosition(currentStatus);
        analyticsInteractor.logEvent(AnalyticsEvent.FAV_RATE_CHANGED);
        destroyPaginator();
        initPaginator();
    }

    public void onRandomOpen() {
        Disposable disposable = interactor.getUserRates(userId, /* page */1, Constants.MAX_LIMIT, currentStatus, settings == null ? UserStatus.AUTHORIZED : settings.getStatus())
                .subscribe(this::openRandomAnime, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    private void openRandomAnime(List<AnimeRate> rates) {
        if (!rates.isEmpty()) {
            Random random = new Random();
            int randomInt = random.nextInt(rates.size());
            long animeId = rates.get(randomInt).getAnime().getId();
            onItemClicked(animeId);
        }
    }

    private void loadUserSettings() {
        Disposable disposable = settingsInteractor.getUserSettings()
                .forEach(this::setSettings);

        unsubscribeOnDestroy(disposable);
    }

    private void setSettings(UserSettings settings) {
        this.userId = settings.getUserBrief() == null ? Constants.NO_ID : settings.getUserBrief().getId();
        this.settings = settings;
        loadUserProfile();
        initPaginator();
    }

    private void initPaginator() {
        paginator = new FavoritePaginatorImpl(interactor, controller);
        paginator.setId(userId);
        paginator.setStatus(currentStatus);
        paginator.setUserStatus(settings == null ? UserStatus.AUTHORIZED : settings.getStatus());
        paginator.refresh();
        getViewState().hideNetworkErrorView();
    }

    public void onItemClicked(long id) {
        analyticsInteractor.logEvent(AnalyticsEvent.ANIME_OPENED);
        getRouter().navigateTo(Screens.ANIME_DETAILS, id);
    }

    public void onItemChangeStatus(long id) {
        List<RateStatus> statuses = new ArrayList<>(Arrays.asList(RateStatus.values()));
        statuses.remove(RateStatus.FAVORITE);
        statuses.remove(currentStatus);

        getViewState().showChangeRateDialog(id, statuses);
    }

    private void destroyPaginator() {
        if (paginator != null) {
            paginator.release();
            paginator = null;
        }
    }

    @Override
    public void onDestroy() {
        destroyPaginator();
        super.onDestroy();
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
            case ContentException.TAG:
                //not implemented (empty page)
                break;
            case ServiceCodeException.TAG:
                processHttpError(throwable);
                break;
            default:
                super.processErrors(throwable);
        }
    }

    private void processHttpError(Throwable throwable) {
        ServiceCodeException exception = (ServiceCodeException) throwable;
        switch (exception.getServiceCode()) {
            case HttpStatusCode.FORBIDDED:
                getViewState().showList(Collections.singletonList(new AnimeRatePlaceholder(PlaceholderType.ERROR)));
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
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void onItemStatusChanged(long id, RateStatus status) {
        UserRate rate = new UserRate(id, status);

        Disposable disposable = interactor.updateRate(rate)
                .subscribe(this::onRefresh, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    public void onItemStatusRemove(long id) {
        Disposable disposable = interactor.deleteRate(id)
                .subscribe(this::onRefresh, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }
}

