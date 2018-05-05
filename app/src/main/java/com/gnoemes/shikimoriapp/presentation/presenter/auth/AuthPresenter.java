package com.gnoemes.shikimoriapp.presentation.presenter.auth;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.domain.auth.AuthInteractor;
import com.gnoemes.shikimoriapp.entity.app.domain.AuthType;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.view.auth.AuthView;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class AuthPresenter extends BaseNetworkPresenter<AuthView> {

    private AuthInteractor interactor;
    private AuthType authType;

    public AuthPresenter(AuthInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void initData() {
        switch (authType) {
            case SIGN_UP:
                getViewState().onSignUp();
                break;
            case OAUTH:
                getViewState().onSignIn();
                break;
        }
    }

    public void onAuthCodeReceived(String authCode) {
        Disposable disposable = interactor.signIn(authCode)
                .subscribe(this::onAuthSuccess);

        unsubscribeOnDestroy(disposable);
    }

    private void onAuthSuccess() {
        getViewState().onBackPressed();
    }

    public void setAuthType(AuthType authType) {
        this.authType = authType;
    }
}
