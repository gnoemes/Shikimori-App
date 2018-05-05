package com.gnoemes.shikimoriapp.domain.auth;

import android.support.annotation.NonNull;

import io.reactivex.Completable;

public interface AuthInteractor {

    Completable signIn(@NonNull String authCode);
}
