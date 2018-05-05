package com.gnoemes.shikimoriapp.utils.net;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class UserAgentInterceptor implements Interceptor {

    private static final String USER_AGENT_HEADER = "User-agent";
    private static final String USER_AGENT_CLIENT = "Shikimori App";

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder requestBuilder = original.newBuilder();
        requestBuilder.addHeader(USER_AGENT_HEADER, USER_AGENT_CLIENT);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
