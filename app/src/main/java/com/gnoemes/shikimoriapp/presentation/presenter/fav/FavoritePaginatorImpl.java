package com.gnoemes.shikimoriapp.presentation.presenter.fav;

import com.gnoemes.shikimoriapp.domain.rates.UserRatesInteractor;
import com.gnoemes.shikimoriapp.entity.app.data.AppConfig;
import com.gnoemes.shikimoriapp.entity.rates.domain.AnimeRate;
import com.gnoemes.shikimoriapp.entity.rates.domain.RateStatus;

import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class FavoritePaginatorImpl implements FavoritePaginator {

    private UserRatesInteractor interactor;
    private StatusViewController<AnimeRate> viewController;

    private int currentPage;
    private int defaultPage = 1;

    private State currentState = new EMPTY_STATE();
    private List<AnimeRate> currentData = Collections.emptyList();
    private RateStatus status;
    private long id;

    private Disposable disposable;

    public FavoritePaginatorImpl(UserRatesInteractor interactor,
                                 StatusViewController<AnimeRate> viewController) {
        this.interactor = interactor;
        this.viewController = viewController;
    }

    private void loadPage(int page) {
        unsubscribe();

        disposable = interactor.getUserRates(id, page, AppConfig.DEFAULT_LIMIT, status)
                .subscribe(
                        items -> currentState.newData(items),
                        throwable -> currentState.error(throwable));

    }

    @Override
    public void setStatus(RateStatus status) {
        this.status = status;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean isFirstPage() {
        return currentPage == defaultPage;
    }

    private void increasePage() {
        currentPage = currentPage + 1;
    }

    @Override
    public void restart() {
        currentState.restart();
    }

    @Override
    public void refresh() {
        currentState.refresh();
    }

    @Override
    public void loadNewPage() {
        currentState.loadNewPage();
    }

    @Override
    public void release() {
        currentState.release();
    }


    private void unsubscribe() {
        if (disposable != null) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }

    public interface StatusViewController<T> {

        void showList(boolean show, List<AnimeRate> list, RateStatus status, boolean isFirst);

        void showRefreshProgress(boolean show, RateStatus status);

        void showPageProgress(boolean show, RateStatus status);

        void showError(RateStatus status, Throwable throwable);

        void showEmptyView(boolean show, RateStatus status);
    }

    private interface State {
        void restart();

        void refresh();

        void loadNewPage();

        void release();

        void newData(List<AnimeRate> list);

        void error(Throwable throwable);
    }

    private abstract class EMPTY implements State {
        @Override
        public void restart() {
        }

        @Override
        public void loadNewPage() {
        }

        @Override
        public void newData(List<AnimeRate> list) {
        }

        @Override
        public void error(Throwable throwable) {
        }

    }

    private class EMPTY_STATE extends EMPTY {

        @Override
        public void refresh() {
            currentState = new EMPTY_PROGRESS_STATE();
            loadPage(defaultPage);
        }

        @Override
        public void release() {
            currentState = new RELEASED_STATE();
            unsubscribe();
        }
    }

    private abstract class EMPTY_PROGRESS implements State {


        @Override
        public void loadNewPage() {
        }

        @Override
        public void refresh() {

        }

    }

    private class EMPTY_PROGRESS_STATE extends EMPTY_PROGRESS {

        @Override
        public void restart() {
            loadPage(defaultPage);
        }

        @Override
        public void newData(List<AnimeRate> list) {
            if (!list.isEmpty()) {
                currentState = new DATA_STATE();
                currentData.clear();
                currentData = list;
                currentPage = defaultPage;
                viewController.showList(true, currentData, status, isFirstPage());
                viewController.showRefreshProgress(false, status);
            } else {
                currentState = new EMPTY_DATA_STATE();
                viewController.showRefreshProgress(false, status);
                viewController.showEmptyView(true, status);
            }
        }

        @Override
        public void error(Throwable throwable) {
            currentState = new EMPTY_ERROR_STATE();
            viewController.showRefreshProgress(false, status);
        }

        @Override
        public void release() {
            currentState = new RELEASED_STATE();
            unsubscribe();
        }
    }

    private abstract class EMPTY_ERROR implements State {
        @Override
        public void loadNewPage() {

        }

        @Override
        public void newData(List<AnimeRate> list) {

        }

        @Override
        public void error(Throwable throwable) {

        }
    }

    private class EMPTY_ERROR_STATE extends EMPTY_ERROR {

        @Override
        public void restart() {
            currentState = new EMPTY_PROGRESS_STATE();
            loadPage(defaultPage);
        }

        @Override
        public void refresh() {
            currentState = new EMPTY_PROGRESS_STATE();
            loadPage(defaultPage);
        }

        @Override
        public void release() {
            currentState = new RELEASED_STATE();
            unsubscribe();
        }
    }

    private abstract class EMPTY_DATA implements State {
        @Override
        public void loadNewPage() {

        }

        @Override
        public void newData(List<AnimeRate> list) {

        }

        @Override
        public void error(Throwable throwable) {

        }
    }

    private class EMPTY_DATA_STATE extends EMPTY_DATA {

        @Override
        public void restart() {
            currentState = new EMPTY_PROGRESS_STATE();
            viewController.showRefreshProgress(false, status);
            loadPage(defaultPage);

        }

        @Override
        public void refresh() {
            currentState = new EMPTY_PROGRESS_STATE();
            loadPage(defaultPage);

        }

        @Override
        public void release() {
            currentState = new RELEASED_STATE();
            unsubscribe();
        }
    }

    private abstract class DATA implements State {
        @Override
        public void newData(List<AnimeRate> list) {

        }
    }

    private class DATA_STATE extends DATA {

        @Override
        public void restart() {
            currentState = new EMPTY_PROGRESS_STATE();
            viewController.showList(false, null, status, isFirstPage());
            viewController.showRefreshProgress(false, status);
            loadPage(defaultPage);
        }

        @Override
        public void refresh() {
            currentState = new REFRESH_STATE();
            viewController.showRefreshProgress(true, status);
            loadPage(defaultPage);
        }

        @Override
        public void loadNewPage() {
            currentState = new PAGE_PROGRESS_STATE();
            viewController.showPageProgress(true, status);
            increasePage();
            loadPage(currentPage);

        }

        @Override
        public void release() {
            currentState = new RELEASED_STATE();
            unsubscribe();
        }

        @Override
        public void error(Throwable throwable) {
            viewController.showError(status, throwable);
        }
    }


    private abstract class REFRESH implements State {
        @Override
        public void refresh() {

        }

        @Override
        public void loadNewPage() {

        }
    }

    private class REFRESH_STATE extends REFRESH {

        @Override
        public void restart() {
            currentState = new EMPTY_PROGRESS_STATE();
            viewController.showList(false, null, status, isFirstPage());
            viewController.showRefreshProgress(false, status);
            loadPage(defaultPage);
        }

        @Override
        public void newData(List<AnimeRate> list) {
            if (!list.isEmpty()) {
                currentState = new DATA_STATE();
                currentData.clear();
                currentData = list;
                currentPage = defaultPage;
                viewController.showRefreshProgress(false, status);
                viewController.showList(true, currentData, status, isFirstPage());
            } else {
                currentState = new EMPTY_DATA_STATE();
                currentData.clear();
                viewController.showList(false, null, status, isFirstPage());
                viewController.showEmptyView(true, status);
                viewController.showRefreshProgress(false, status);
            }
        }

        @Override
        public void error(Throwable throwable) {
            currentState = new DATA_STATE();
            viewController.showRefreshProgress(false, status);
            viewController.showError(status, throwable);
        }

        @Override
        public void release() {
            currentState = new RELEASED_STATE();
            unsubscribe();
        }
    }

    private abstract class PAGE_PROGRESS implements State {

        @Override
        public void loadNewPage() {

        }
    }

    private class PAGE_PROGRESS_STATE extends PAGE_PROGRESS {

        @Override
        public void restart() {
            currentState = new EMPTY_PROGRESS_STATE();
            viewController.showList(false, null, status, isFirstPage());
            viewController.showPageProgress(false, status);
            loadPage(defaultPage);
        }

        @Override
        public void newData(List<AnimeRate> list) {
            if (!list.isEmpty()) {
                currentState = new DATA_STATE();
                currentData = list;
                increasePage();
                viewController.showPageProgress(false, status);
                viewController.showList(true, currentData, status, isFirstPage());
            } else {
                currentState = new ALL_DATA_STATE();
                viewController.showPageProgress(false, status);
                viewController.showEmptyView(true, status);
            }

        }

        @Override
        public void refresh() {
            currentState = new REFRESH_STATE();
            viewController.showPageProgress(false, status);
            viewController.showRefreshProgress(true, status);
            loadPage(defaultPage);
        }


        @Override
        public void error(Throwable throwable) {
            currentState = new DATA_STATE();
            viewController.showPageProgress(false, status);
            viewController.showError(status, throwable);
        }

        @Override
        public void release() {
            currentState = new RELEASED_STATE();
            unsubscribe();
        }
    }


    private abstract class ALL_DATA implements State {

        @Override
        public void newData(List<AnimeRate> list) {

        }

        @Override
        public void error(Throwable throwable) {

        }

        @Override
        public void loadNewPage() {

        }
    }

    private class ALL_DATA_STATE extends ALL_DATA {

        @Override
        public void restart() {
            currentState = new EMPTY_PROGRESS_STATE();
            viewController.showList(false, null, status, isFirstPage());
            loadPage(defaultPage);

        }

        @Override
        public void refresh() {
            currentState = new REFRESH_STATE();
            viewController.showRefreshProgress(true, status);
            loadPage(defaultPage);

        }

        @Override
        public void release() {
            currentState = new RELEASED_STATE();
            unsubscribe();
        }
    }

    private abstract class RELEASED implements State {
        @Override
        public void restart() {

        }

        @Override
        public void refresh() {

        }

        @Override
        public void loadNewPage() {

        }

        @Override
        public void release() {

        }

        @Override
        public void newData(List<AnimeRate> list) {

        }

        @Override
        public void error(Throwable throwable) {

        }
    }

    private class RELEASED_STATE extends RELEASED {

    }
}
