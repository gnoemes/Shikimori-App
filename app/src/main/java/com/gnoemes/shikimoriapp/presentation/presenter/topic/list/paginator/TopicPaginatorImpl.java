package com.gnoemes.shikimoriapp.presentation.presenter.topic.list.paginator;

import com.gnoemes.shikimoriapp.domain.social.TopicInteractor;
import com.gnoemes.shikimoriapp.entity.app.data.AppConfig;
import com.gnoemes.shikimoriapp.entity.forum.domain.ForumType;
import com.gnoemes.shikimoriapp.entity.topic.domain.Topic;
import com.gnoemes.shikimoriapp.presentation.presenter.common.ViewController;

import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.Disposable;

//TODO refactor paginators to BaseItem with convertation inside
public class TopicPaginatorImpl implements TopicPaginator {

    private TopicInteractor interactor;
    private ViewController<Topic> viewController;

    private int currentPage;
    private int defaultPage = 1;

    private State currentState = new EMPTY_STATE();
    private List<Topic> currentData = Collections.emptyList();
    private ForumType forumType = ForumType.ALL;
    private Disposable disposable;

    public TopicPaginatorImpl(TopicInteractor interactor, ViewController<Topic> viewController) {
        this.interactor = interactor;
        this.viewController = viewController;
    }

    private void loadPage(int page) {
        unsubscribe();

        disposable = interactor.getTopicList(page, AppConfig.DEFAULT_LIMIT, forumType)
                .subscribe(
                        items -> currentState.newData(items),
                        throwable -> currentState.error(throwable));
    }

    private void unsubscribe() {
        if (disposable != null) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }

    @Override
    public void setForumType(ForumType type) {
        this.forumType = type;
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

    private interface State {
        void restart();

        void refresh();

        void loadNewPage();

        void release();

        void newData(List<Topic> list);

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
        public void newData(List<Topic> list) {
        }

        @Override
        public void error(Throwable throwable) {
        }

    }

    private class EMPTY_STATE extends EMPTY {

        @Override
        public void refresh() {
            currentState = new EMPTY_PROGRESS_STATE();
            viewController.showEmptyProgress(true);
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
        public void newData(List<Topic> list) {
            if (!list.isEmpty()) {
                currentState = new DATA_STATE();
                currentData.clear();
                currentData = list;
                currentPage = defaultPage;
                viewController.showList(true, currentData);
                viewController.showEmptyView(false);
                viewController.showEmptyProgress(false);
                viewController.showRefreshProgress(false);
            } else {
                currentState = new EMPTY_DATA_STATE();
                viewController.showEmptyProgress(false);
                viewController.showRefreshProgress(false);
                viewController.showEmptyView(true);
            }
        }

        @Override
        public void error(Throwable throwable) {
            currentState = new EMPTY_ERROR_STATE();
            viewController.showEmptyProgress(false);
            viewController.showEmptyError(true, throwable);
            viewController.showRefreshProgress(false);
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
        public void newData(List<Topic> list) {

        }

        @Override
        public void error(Throwable throwable) {

        }
    }

    private class EMPTY_ERROR_STATE extends EMPTY_ERROR {

        @Override
        public void restart() {
            currentState = new EMPTY_PROGRESS_STATE();
            viewController.showEmptyError(false, null);
            viewController.showEmptyProgress(true);
            loadPage(defaultPage);
        }

        @Override
        public void refresh() {
            currentState = new EMPTY_PROGRESS_STATE();
            viewController.showEmptyError(false, null);
            viewController.showEmptyProgress(true);
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
        public void newData(List<Topic> list) {

        }

        @Override
        public void error(Throwable throwable) {

        }
    }

    private class EMPTY_DATA_STATE extends EMPTY_DATA {

        @Override
        public void restart() {
            currentState = new EMPTY_PROGRESS_STATE();
            viewController.showEmptyView(false);
            viewController.showRefreshProgress(false);
            viewController.showEmptyProgress(true);
            loadPage(defaultPage);

        }

        @Override
        public void refresh() {
            currentState = new EMPTY_PROGRESS_STATE();
            viewController.showEmptyView(false);
            viewController.showEmptyProgress(true);
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
        public void newData(List<Topic> list) {

        }
    }

    private class DATA_STATE extends DATA {

        @Override
        public void restart() {
            currentState = new EMPTY_PROGRESS_STATE();
            viewController.showList(false, null);
            viewController.showRefreshProgress(false);
            viewController.showEmptyProgress(true);
            loadPage(defaultPage);
        }

        @Override
        public void refresh() {
            currentState = new REFRESH_STATE();
            viewController.showRefreshProgress(true);
            loadPage(defaultPage);
        }

        @Override
        public void loadNewPage() {
            currentState = new PAGE_PROGRESS_STATE();
            viewController.showPageProgress(true);
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
            viewController.showError(throwable);
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
            viewController.showList(false, null);
            viewController.showRefreshProgress(false);
            viewController.showEmptyProgress(true);
            loadPage(defaultPage);
        }

        @Override
        public void newData(List<Topic> list) {
            if (!list.isEmpty()) {
                currentState = new DATA_STATE();
                currentData.clear();
                currentData = list;
                currentPage = defaultPage;
                viewController.showRefreshProgress(false);
                viewController.showEmptyError(false, null);
                viewController.showList(true, currentData);
                viewController.showEmptyView(false);
            } else {
                currentState = new EMPTY_DATA_STATE();
                currentData.clear();
                viewController.showList(false, null);
                viewController.showRefreshProgress(false);
                viewController.showEmptyView(true);
            }
        }

        @Override
        public void error(Throwable throwable) {
            currentState = new DATA_STATE();
            viewController.showRefreshProgress(false);
            viewController.showError(throwable);
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
            viewController.showList(false, null);
            viewController.showPageProgress(false);
            viewController.showEmptyProgress(true);
            loadPage(defaultPage);
        }

        @Override
        public void newData(List<Topic> list) {
            if (!list.isEmpty()) {
                currentState = new DATA_STATE();
                currentData = list;
                viewController.showPageProgress(false);
                viewController.showList(true, currentData);
                viewController.showEmptyView(false);
            } else {
                currentState = new ALL_DATA_STATE();
                viewController.showPageProgress(false);
            }

        }

        @Override
        public void refresh() {
            currentState = new REFRESH_STATE();
            viewController.showPageProgress(false);
            viewController.showRefreshProgress(true);
            loadPage(defaultPage);
        }


        @Override
        public void error(Throwable throwable) {
            currentState = new DATA_STATE();
            viewController.showPageProgress(false);
            viewController.showError(throwable);
        }

        @Override
        public void release() {
            currentState = new RELEASED_STATE();
            unsubscribe();
        }
    }


    private abstract class ALL_DATA implements State {

        @Override
        public void newData(List<Topic> list) {

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
            viewController.showList(false, null);
            viewController.showEmptyProgress(true);
            loadPage(defaultPage);

        }

        @Override
        public void refresh() {
            currentState = new REFRESH_STATE();
            viewController.showRefreshProgress(true);
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
        public void newData(List<Topic> list) {

        }

        @Override
        public void error(Throwable throwable) {

        }
    }

    private class RELEASED_STATE extends RELEASED {

    }
}
