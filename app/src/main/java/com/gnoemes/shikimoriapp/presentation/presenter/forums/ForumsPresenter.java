package com.gnoemes.shikimoriapp.presentation.presenter.forums;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.domain.social.TopicInteractor;
import com.gnoemes.shikimoriapp.entity.app.domain.BaseException;
import com.gnoemes.shikimoriapp.entity.app.domain.NetworkException;
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens;
import com.gnoemes.shikimoriapp.entity.forum.domain.Forum;
import com.gnoemes.shikimoriapp.entity.forum.domain.ForumType;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.view.forums.ForumsView;

import java.util.List;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class ForumsPresenter extends BaseNetworkPresenter<ForumsView> {

    private TopicInteractor interactor;

    public ForumsPresenter(TopicInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void initData() {
        loadForums();
    }

    private void loadForums() {
        getViewState().hideNetworkError();
        getViewState().onShowLoading();

        Disposable disposable = interactor.getForums()
                .doOnEvent((forums, throwable) -> getViewState().onHideLoading())
                .subscribe(this::setForums, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    @Override
    protected void processErrors(Throwable throwable) {
        BaseException baseException = (BaseException) throwable;
        switch (baseException.getTag()) {
            case NetworkException.TAG:
                getViewState().showNetworkError();
                break;
            default:
                super.processErrors(throwable);
        }
    }


    public void onForumClicked(ForumType forumType) {
        getRouter().navigateTo(Screens.TOPICS_LIST, forumType);
    }

    public void onRefresh() {
        loadForums();
    }

    private void setForums(List<Forum> forums) {
        //TODO remove
        forums.add(0, new Forum(-1, "Все", ForumType.ALL, null));
        getViewState().showForums(forums);
    }
}
