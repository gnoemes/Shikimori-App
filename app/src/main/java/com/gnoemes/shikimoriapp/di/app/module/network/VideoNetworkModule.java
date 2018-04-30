package com.gnoemes.shikimoriapp.di.app.module.network;

import com.gnoemes.shikimoriapp.BuildConfig;
import com.gnoemes.shikimoriapp.di.app.qualifiers.VideoApi;
import com.gnoemes.shikimoriapp.entity.app.data.AppConfig;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

@Module
public abstract class VideoNetworkModule {

    @Provides
    @Singleton
    @VideoApi
    static OkHttpClient provideOkHttpClient(HttpLoggingInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(AppConfig.LONG_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(AppConfig.LONG_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    @VideoApi
    static Retrofit.Builder provideRetrofitBuilder(Converter.Factory factory, @VideoApi OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(factory);
    }

    @Provides
    @Singleton
    @VideoApi
    static Retrofit provideRetrofit(@VideoApi Retrofit.Builder builder) {
        return builder.baseUrl(BuildConfig.SmotretAnimeBaseUrl).build();
    }


    @Provides
    @Singleton
    @VideoApi
    static ConnectionSpec provideConnectionSpec() {
        return new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_1, TlsVersion.TLS_1_2)
                .build();
    }
}
