package com.gnoemes.shikimoriapp.presentation.presenter.history;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.domain.app.AnalyticsInteractor;
import com.gnoemes.shikimoriapp.domain.user.UserInteractor;
import com.gnoemes.shikimoriapp.entity.app.domain.AnalyticsEvent;
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens;
import com.gnoemes.shikimoriapp.entity.user.domain.UserHistory;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.common.ViewController;
import com.gnoemes.shikimoriapp.presentation.presenter.history.converter.HistoryViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.history.paginator.HistoryPaginator;
import com.gnoemes.shikimoriapp.presentation.presenter.history.paginator.HistoryPaginatorImpl;
import com.gnoemes.shikimoriapp.presentation.view.history.HistoryView;

import java.util.Collections;
import java.util.List;

@InjectViewState
public class HistoryPresenter extends BaseNetworkPresenter<HistoryView> {

    private UserInteractor interactor;
    private HistoryViewModelConverter converter;
    private HistoryPaginator paginator;
    private AnalyticsInteractor analyticsInteractor;

    private List<UserHistory> prevList;

    private long id;

    private ViewController<UserHistory> controller = new ViewController<UserHistory>() {
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
        public void showList(boolean show, List<UserHistory> list) {
            if (show) {
                if (paginator.isFirstPage()) {
                    getViewState().showList(converter.convertFrom(null, list));
                } else {
                    getViewState().insertMore(converter.convertFrom(prevList, list));
                }
                prevList = list;
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

    public HistoryPresenter(UserInteractor interactor,
                            HistoryViewModelConverter converter,
                            AnalyticsInteractor analyticsInteractor) {
        this.interactor = interactor;
        this.converter = converter;
        this.analyticsInteractor = analyticsInteractor;
    }


    public void loadNextPage() {
        paginator.loadNewPage();
    }

    public void onItemClicked(long id) {
        //TODO for all types
        analyticsInteractor.logEvent(AnalyticsEvent.ANIME_OPENED);
        getRouter().navigateTo(Screens.ANIME_DETAILS, id);
    }

    public void onRefresh() {
        paginator.refresh();
    }

    @Override
    public void initData() {
        initPaginator();
        getViewState().setTitle(R.string.common_history);
    }

    private void initPaginator() {
        paginator = new HistoryPaginatorImpl(interactor, controller);
        paginator.setId(id);
        paginator.refresh();
    }

    public void setId(long id) {
        this.id = id;
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

}
