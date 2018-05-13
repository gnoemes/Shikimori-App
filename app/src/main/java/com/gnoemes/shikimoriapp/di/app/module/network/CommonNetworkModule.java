package com.gnoemes.shikimoriapp.di.app.module.network;

import com.gnoemes.shikimoriapp.BuildConfig;
import com.gnoemes.shikimoriapp.entity.app.data.AppConfig;
import com.gnoemes.shikimoriapp.utils.net.UserAgentInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

@Module
public interface CommonNetworkModule {

    @Provides
    @Singleton
    static OkHttpClient provideOkHttpClient(HttpLoggingInterceptor interceptor,
                                            UserAgentInterceptor userAgentInterceptor) {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(userAgentInterceptor)
                .addNetworkInterceptor(interceptor)
                .connectTimeout(AppConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(AppConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    static Retrofit.Builder provideRetrofitBuilder(Converter.Factory factory, OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(factory);
    }

    @Provides
    @Singleton
    static Retrofit provideRetrofit(Retrofit.Builder builder) {
        return builder.baseUrl(BuildConfig.ShikimoriBaseUrl).build();
    }

}
