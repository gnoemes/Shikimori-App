package com.gnoemes.shikimoriapp.utils.net;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.repository.app.TokenRepository;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    private static final String ACCESS_TOKEN_HEADER = "Authorization";

    private TokenRepository tokenRepository;

    public TokenInterceptor(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder requestBuilder = original.newBuilder();
        if (tokenRepository.isTokenExists()) {
            requestBuilder.addHeader(ACCESS_TOKEN_HEADER, String.format("Bearer %s", tokenRepository.getToken().getAuthToken()));
        }

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
