package com.gnoemes.shikimoriapp.presentation.presenter.fav;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.domain.app.UserSettingsInteractor;
import com.gnoemes.shikimoriapp.domain.rates.UserRatesInteractor;
import com.gnoemes.shikimoriapp.entity.app.domain.UserSettings;
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens;
import com.gnoemes.shikimoriapp.entity.rates.domain.AnimeRate;
import com.gnoemes.shikimoriapp.entity.rates.domain.RateStatus;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
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

    private FavoritePaginator wachingPaginator;
    private FavoritePaginator plannedPaginator;
    private FavoritePaginator reWatchingPaginator;
    private FavoritePaginator completedPaginator;
    private FavoritePaginator onHoldPaginator;
    private FavoritePaginator droppedPaginator;

    private long id;
    private FavoritePaginatorImpl.StatusViewController<AnimeRate> controller = new FavoritePaginatorImpl.StatusViewController<AnimeRate>() {
        @Override
        public void showList(boolean show, List<AnimeRate> list, RateStatus status, boolean isFirstPage) {
            if (show) {
                if (isFirstPage) {
                    getViewState().showList(status, converter.convertFrom(list));
                } else {
                    getViewState().insertMore(status, converter.convertFrom(list));
                }
            }
        }

        @Override
        public void showRefreshProgress(boolean show, RateStatus status) {
            if (show) {
                getViewState().showLoading(status);
            } else {
                getViewState().hideLoading(status);
            }
        }

        @Override
        public void showPageProgress(boolean show, RateStatus status) {
            if (show) {
                getViewState().onShowPageLoading(status);
            } else {
                getViewState().onHidePageLoading(status);
            }
        }

        @Override
        public void showError(RateStatus status, Throwable throwable) {
//            if (throwable instanceof BaseException) {
//                BaseException exception = (BaseException) throwable;
//                switch (exception.getTag()) {
//                    case ContentException.TAG:
//                        getViewState().onHidePageLoading(status);
//                        break;
//                    default:
//                        processErrors(throwable);
//                }
//            } else {
//                processErrors(throwable);
//            }
        }

        @Override
        public void showEmptyView(boolean show, RateStatus status) {
            if (show) {
                getViewState().showList(status, Collections.emptyList());
            }
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
        loadUserSettings();
        getViewState().setTitle(R.string.common_anime_rates);
    }

    private void loadUserSettings() {
        Disposable disposable = settingsInteractor.getUserSettings()
                .forEach(this::setSettings);

        unsubscribeOnDestroy(disposable);
    }

    private void setSettings(UserSettings settings) {
        this.id = settings.getUserBrief() == null ? -1 : settings.getUserBrief().getId();
        initPaginators();
    }

    private void initPaginators() {
        wachingPaginator = new FavoritePaginatorImpl(interactor, controller);
        wachingPaginator.setId(id);
        wachingPaginator.setStatus(RateStatus.WATCHING);
        wachingPaginator.refresh();

        plannedPaginator = new FavoritePaginatorImpl(interactor, controller);
        plannedPaginator.setId(id);
        plannedPaginator.setStatus(RateStatus.PLANNED);
        plannedPaginator.refresh();

        reWatchingPaginator = new FavoritePaginatorImpl(interactor, controller);
        reWatchingPaginator.setId(id);
        reWatchingPaginator.setStatus(RateStatus.REWATCHING);
        reWatchingPaginator.refresh();

        completedPaginator = new FavoritePaginatorImpl(interactor, controller);
        completedPaginator.setId(id);
        completedPaginator.setStatus(RateStatus.COMPLETED);
        completedPaginator.refresh();

        onHoldPaginator = new FavoritePaginatorImpl(interactor, controller);
        onHoldPaginator.setId(id);
        onHoldPaginator.setStatus(RateStatus.ON_HOLD);
        onHoldPaginator.refresh();

        droppedPaginator = new FavoritePaginatorImpl(interactor, controller);
        droppedPaginator.setId(id);
        droppedPaginator.setStatus(RateStatus.DROPPED);
        droppedPaginator.refresh();
    }

    public void onItemClicked(long id) {
        getRouter().navigateTo(Screens.ANIME_DETAILS, id);
    }

    public void onWatchingRefresh() {
        wachingPaginator.refresh();
    }

    public void onPlannedRefresh() {
        plannedPaginator.refresh();
    }

    public void onReWatchingRefresh() {
        reWatchingPaginator.refresh();
    }

    public void onCompletedRefresh() {
        completedPaginator.refresh();
    }

    public void onHoldRefresh() {
        onHoldPaginator.refresh();
    }

    public void onDroppedRefresh() {
        droppedPaginator.refresh();
    }

    public void loadDroppedNextPage() {
        droppedPaginator.loadNewPage();
    }

    public void loadOnHoldNextPage() {
        onHoldPaginator.loadNewPage();
    }

    public void loadCompletedNextPage() {
        completedPaginator.loadNewPage();
    }

    public void loadReWatchingNextPage() {
        reWatchingPaginator.loadNewPage();
    }

    public void loadPlannedNextPage() {
        plannedPaginator.loadNewPage();
    }

    public void loadWatchingNextPage() {
        wachingPaginator.loadNewPage();
    }

    public void onTabSelected(RateStatus status) {
        switch (status) {
            case WATCHING:
                getViewState().setTitle(R.string.rate_watching);
                break;
            case PLANNED:
                getViewState().setTitle(R.string.rate_planned);
                break;
            case REWATCHING:
                getViewState().setTitle(R.string.rate_rewatch);
                break;
            case COMPLETED:
                getViewState().setTitle(R.string.rate_completed);
                break;
            case ON_HOLD:
                getViewState().setTitle(R.string.rate_on_hold);
                break;
            case DROPPED:
                getViewState().setTitle(R.string.rate_dropped);
                break;
            case FAVORITE:
                getViewState().setTitle(R.string.common_favorite);
                break;
        }
    }

    @Override
    public void onDestroy() {
        wachingPaginator.release();
        plannedPaginator.release();
        reWatchingPaginator.release();
        completedPaginator.release();
        onHoldPaginator.release();
        droppedPaginator.release();

        wachingPaginator = null;
        plannedPaginator = null;
        reWatchingPaginator = null;
        completedPaginator = null;
        onHoldPaginator = null;
        droppedPaginator = null;
        super.onDestroy();

    }
}

