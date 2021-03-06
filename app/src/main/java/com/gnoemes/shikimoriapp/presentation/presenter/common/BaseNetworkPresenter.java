package com.gnoemes.shikimoriapp.presentation.presenter.common;

import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseNetworkView;
import com.gnoemes.shikimoriapp.utils.rx.ErrorUtils;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseNetworkPresenter<View extends BaseNetworkView> extends BaseNavigationPresenter<View> {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected void unsubscribeOnDestroy(@NonNull Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void initData() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    protected void processErrors(Throwable throwable) {
        ErrorUtils errorUtils = new ErrorUtils();
        errorUtils.processErrors(throwable, getRouter(), getViewState());
    }
}
