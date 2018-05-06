package com.gnoemes.shikimoriapp.domain.user;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.repository.user.UserRepository;
import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief;
import com.gnoemes.shikimoriapp.utils.rx.RxUtils;
import com.gnoemes.shikimoriapp.utils.rx.SingleErrorHandler;

import javax.inject.Inject;

import io.reactivex.Single;

public class UserInteractorImpl implements UserInteractor {

    private UserRepository repository;
    private SingleErrorHandler singleErrorHandler;
    private RxUtils rxUtils;

    @Inject
    public UserInteractorImpl(@NonNull UserRepository repository,
                              @NonNull SingleErrorHandler singleErrorHandler,
                              @NonNull RxUtils rxUtils) {
        this.repository = repository;
        this.singleErrorHandler = singleErrorHandler;
        this.rxUtils = rxUtils;
    }

    @Override
    public Single<UserBrief> getMyUser() {
        return repository.getMyUserBrief()

                .compose((SingleErrorHandler<UserBrief>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }
}
