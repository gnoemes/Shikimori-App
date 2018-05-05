package com.gnoemes.shikimoriapp.data.repository.app.impl;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.BuildConfig;
import com.gnoemes.shikimoriapp.data.network.AuthApi;
import com.gnoemes.shikimoriapp.data.repository.app.AuthorizationRepository;
import com.gnoemes.shikimoriapp.entity.app.domain.Token;

import javax.inject.Inject;

import io.reactivex.Single;

public class AuthorizationRepositoryImpl implements AuthorizationRepository {

    private static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
    private static final String AUTH_CODE = "authorization_code";
    private static final String REFRESH_TOKEN = "refresh_token";

    private AuthApi authApi;

    @Inject
    public AuthorizationRepositoryImpl(AuthApi authApi) {
        this.authApi = authApi;
    }

    @Override
    public Single<Token> signIn(@NonNull String authCode) {
        return authApi.getAccessToken(AUTH_CODE,
                BuildConfig.ShikimoriClientId,
                BuildConfig.ShikimoriClientSecret,
                authCode,
                REDIRECT_URI,
                null)
                .map(tokenResponse -> new Token(tokenResponse.getAccessToken(),
                        tokenResponse.getRefreshToken()));
    }

    @Override
    public Single<Token> refreshToken(@NonNull String refreshToken) {
        return authApi.getAccessToken(REFRESH_TOKEN,
                BuildConfig.ShikimoriClientId,
                BuildConfig.ShikimoriClientSecret,
                null,
                null,
                refreshToken)
                .map(tokenResponse -> new Token(tokenResponse.getAccessToken(),
                        tokenResponse.getRefreshToken()));
    }
}
