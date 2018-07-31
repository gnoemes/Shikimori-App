package com.gnoemes.shikimoriapp.presentation.presenter.topic.list;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.domain.social.TopicInteractor;
import com.gnoemes.shikimoriapp.entity.app.domain.BaseException;
import com.gnoemes.shikimoriapp.entity.app.domain.LinkedType;
import com.gnoemes.shikimoriapp.entity.app.domain.NetworkException;
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens;
import com.gnoemes.shikimoriapp.entity.forum.domain.ForumType;
import com.gnoemes.shikimoriapp.entity.topic.domain.Topic;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.common.ViewController;
import com.gnoemes.shikimoriapp.presentation.presenter.topic.list.converter.TopicViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.topic.list.paginator.TopicPaginator;
import com.gnoemes.shikimoriapp.presentation.presenter.topic.list.paginator.TopicPaginatorImpl;
import com.gnoemes.shikimoriapp.presentation.view.topic.list.TopicListView;
import com.gnoemes.shikimoriapp.presentation.view.topic.provider.TopicResourceProvider;

import java.util.Collections;
import java.util.List;

@InjectViewState
public class TopicListPresenter extends BaseNetworkPresenter<TopicListView> {

    private TopicInteractor interactor;
    private TopicViewModelConverter converter;
    private TopicResourceProvider resourceProvider;

    private TopicPaginator paginator;

    private ForumType forumType;

    private ViewController<Topic> viewController = new ViewController<Topic>() {
        @Override
        public void showEmptyError(boolean show, Throwable throwable) {
            if (show) {
                processErrors(throwable);
            } else {
                getViewState().clearList();
                getViewState().hideNetworkError();
            }
        }

        @Override
        public void showEmptyView(boolean show) {
            if (show) {
                getViewState().showList(Collections.emptyList());
            }
        }

        @Override
        public void showList(boolean show, List<Topic> list) {
            if (show) {
                if (paginator.isFirstPage()) {
                    getViewState().showList(converter.convertListFrom(list));
                } else {
                    getViewState().insetMore(converter.convertListFrom(list));
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


    public TopicListPresenter(TopicInteractor interactor,
                              TopicViewModelConverter converter,
                              TopicResourceProvider resourceProvider) {
        this.interactor = interactor;
        this.converter = converter;
        this.resourceProvider = resourceProvider;
    }

    @Override
    public void initData() {
        paginator = new TopicPaginatorImpl(interactor, viewController);
        onRefresh();
        getViewState().setTitle(resourceProvider.getTopicName(forumType));
    }

    public void loadNextPage() {
        if (paginator != null) {
            paginator.setForumType(forumType);
            paginator.loadNewPage();
        }
    }

    public void onRefresh() {
        if (paginator != null) {
            paginator.setForumType(forumType);
            paginator.refresh();
        }
    }

    public void onTopicClicked(long id) {
        //TODO navigate to topic fragment
    }

    public void onUserClicked(long id) {
        getRouter().navigateTo(Screens.PROFILE, id);
    }

    public void onLinkedContentClicked(long id, LinkedType linkedType) {
        switch (linkedType) {
            case ANIME:
                getRouter().navigateTo(Screens.ANIME_DETAILS, id);
                break;
            case CHARACTER:
                getRouter().navigateTo(Screens.CHARACTER_DETAILS, id);
                break;
        }
    }

    @Override
    protected void processErrors(Throwable throwable) {
        BaseException baseException = (BaseException) throwable;
        switch (baseException.getTag()) {
            case NetworkException.TAG:
                getViewState().showNetworkError();
                getViewState().clearList();
                break;
            default:
                super.processErrors(throwable);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (paginator != null) {
            paginator.release();
        }
    }

    public void setForumType(ForumType forumType) {
        this.forumType = forumType;
    }
}
