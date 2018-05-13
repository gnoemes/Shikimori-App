package com.gnoemes.shikimoriapp.di.app.module.network;

import com.gnoemes.shikimoriapp.BuildConfig;
import com.gnoemes.shikimoriapp.data.repository.app.AuthorizationRepository;
import com.gnoemes.shikimoriapp.data.repository.app.TokenRepository;
import com.gnoemes.shikimoriapp.di.app.qualifiers.AuthCommonApi;
import com.gnoemes.shikimoriapp.entity.app.data.AppConfig;
import com.gnoemes.shikimoriapp.utils.net.AuthHolder;
import com.gnoemes.shikimoriapp.utils.net.ShikiAuthenticator;
import com.gnoemes.shikimoriapp.utils.net.TokenInterceptor;
import com.gnoemes.shikimoriapp.utils.net.UserAgentInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

@Module
public interface AuthCommonNetworkModule {

    @Provides
    @Singleton
    @AuthCommonApi
    static OkHttpClient provideOkHttpClient(HttpLoggingInterceptor interceptor,
                                            UserAgentInterceptor userAgentInterceptor,
                                            @AuthCommonApi Authenticator authenticator,
                                            @AuthCommonApi TokenInterceptor tokenInterceptor) {
        return new OkHttpClient.Builder()
                .authenticator(authenticator)
                .addInterceptor(tokenInterceptor)
                .addInterceptor(userAgentInterceptor)
                .addInterceptor(interceptor)
                .connectTimeout(AppConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(AppConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    @AuthCommonApi
    static Retrofit.Builder provideRetrofitBuilder(Converter.Factory factory, @AuthCommonApi OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(factory);
    }

    @Provides
    @Singleton
    @AuthCommonApi
    static Retrofit provideRetrofit(@AuthCommonApi Retrofit.Builder builder) {
        return builder.baseUrl(BuildConfig.ShikimoriBaseUrl).build();
    }

    @Provides
    @Singleton
    @AuthCommonApi
    static AuthHolder provideAuthHolder(TokenRepository tokenRepository,
                                        AuthorizationRepository repository) {
        return new AuthHolder(tokenRepository, repository);
    }

    @Provides
    @Singleton
    @AuthCommonApi
    static Authenticator bindShikiAuthenticator(@AuthCommonApi AuthHolder authHolder) {
        return new ShikiAuthenticator(authHolder);
    }

    @Provides
    @Singleton
    @AuthCommonApi
    static TokenInterceptor bindTokenInterceptor(TokenRepository repository) {
        return new TokenInterceptor(repository);
    }
}
