package com.gnoemes.shikimoriapp.presentation.presenter.fav;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.domain.app.UserSettingsInteractor;
import com.gnoemes.shikimoriapp.domain.rates.UserRatesInteractor;
import com.gnoemes.shikimoriapp.entity.app.domain.UserSettings;
import com.gnoemes.shikimoriapp.entity.app.domain.UserStatus;
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens;
import com.gnoemes.shikimoriapp.entity.rates.domain.AnimeRate;
import com.gnoemes.shikimoriapp.entity.rates.domain.RateStatus;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.common.ViewController;
import com.gnoemes.shikimoriapp.presentation.view.fav.FavoriteView;
import com.gnoemes.shikimoriapp.presentation.view.fav.converter.AnimeRateViewModelConverter;

import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class FavoritePresenter extends BaseNetworkPresenter<FavoriteView> {

    private UserRatesInteractor interactor;
    private UserSettingsInteractor settingsInteractor;
    private AnimeRateViewModelConverter converter;

    private FavoritePaginator paginator;

    private RateStatus currentStatus;
    private Long userId;
    private UserSettings settings;

    private ViewController<AnimeRate> controller = new ViewController<AnimeRate>() {
        @Override
        public void showEmptyError(boolean show, Throwable throwable) {

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
                             @NonNull AnimeRateViewModelConverter converter) {
        this.interactor = interactor;
        this.settingsInteractor = settingsInteractor;
        this.converter = converter;
    }

    @Override
    public void initData() {
        currentStatus = RateStatus.WATCHING;
        if (userId == null) {
            loadUserSettings();
        } else {
            getViewState().addBackArrow();
            initPaginator();
        }
    }

    public void loadNextPage() {
        paginator.loadNewPage();
    }

    public void onRefresh() {
        paginator.refresh();
    }

    public void onStatusChanged(RateStatus currentStatus) {
        this.currentStatus = currentStatus;
        destroyPaginator();
        initPaginator();
    }

    private void loadUserSettings() {
        Disposable disposable = settingsInteractor.getUserSettings()
                .forEach(this::setSettings);

        unsubscribeOnDestroy(disposable);
    }

    private void setSettings(UserSettings settings) {
        this.userId = settings.getUserBrief() == null ? -1 : settings.getUserBrief().getId();
        this.settings = settings;
        initPaginator();
    }

    private void initPaginator() {
        paginator = new FavoritePaginatorImpl(interactor, controller);
        paginator.setId(userId);
        paginator.setStatus(currentStatus);
        paginator.setUserStatus(settings == null ? UserStatus.AUTHORIZED : settings.getStatus());
        paginator.refresh();
    }

    public void onItemClicked(long id) {
        getRouter().navigateTo(Screens.ANIME_DETAILS, id);
    }

    private void destroyPaginator() {
        paginator.release();
        paginator = null;
    }

    @Override
    public void onDestroy() {
        destroyPaginator();
        super.onDestroy();
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}

