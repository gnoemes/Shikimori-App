package com.gnoemes.shikimoriapp.utils.net.shiki;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.di.app.qualifiers.AuthCommonApi;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class ShikiAuthenticator implements Authenticator {
    private static final String ACCESS_TOKEN_HEADER = "Authorization";

    private AuthHolder authHolder;

    public ShikiAuthenticator(@AuthCommonApi AuthHolder authHolder) {
        this.authHolder = authHolder;
    }

    @Nullable
    @Override
    public Request authenticate(@NonNull Route route, @NonNull Response response) {
        String storedToken = String.format("Bearer %s", authHolder.getToken().getAuthToken());
        String requestToken = response.request().header(ACCESS_TOKEN_HEADER);

        Request.Builder requestBuilder = response.request().newBuilder();

        if (storedToken.equals(requestToken)) {
            authHolder.refresh();
        }

        return buildRequest(requestBuilder);
    }

    private Request buildRequest(Request.Builder requestBuilder) {
        return requestBuilder.header(ACCESS_TOKEN_HEADER,
                String.format("Bearer %s", authHolder.getToken().getAuthToken())).build();
    }
}
