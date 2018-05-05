package com.gnoemes.shikimoriapp.data.repository.app;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.entity.app.domain.Token;

import io.reactivex.Single;

public interface AuthorizationRepository {

    Single<Token> signIn(@NonNull String authCode);

    Single<Token> refreshToken(@NonNull String refreshToken);

}
