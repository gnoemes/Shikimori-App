package com.gnoemes.shikimoriapp.di.app.module.network;

import android.content.Context;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.gnoemes.shikimoriapp.BuildConfig;
import com.gnoemes.shikimoriapp.di.app.qualifiers.VideoApi;
import com.gnoemes.shikimoriapp.entity.app.data.AppConfig;
import com.gnoemes.shikimoriapp.utils.net.parser.PlayShikimoriConverterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

@Module
public interface VideoNetworkModule {

    @Provides
    @Singleton
    @VideoApi
    static OkHttpClient provideOkHttpClient(HttpLoggingInterceptor interceptor,
                                            @VideoApi CookieJar cookieJar) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .cookieJar(cookieJar)
                .connectTimeout(AppConfig.LONG_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(AppConfig.LONG_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    @VideoApi
    static Retrofit.Builder provideRetrofitBuilder(@VideoApi Converter.Factory factory, @VideoApi OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(factory);
    }

    @Provides
    @Singleton
    @VideoApi
    static Retrofit provideRetrofit(@VideoApi Retrofit.Builder builder) {
        return builder.baseUrl(BuildConfig.PlaySkihimoriBaseUrl).build();
    }


    @Provides
    @Singleton
    @VideoApi
    static ConnectionSpec provideConnectionSpec() {
        return new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA)
                .build();
    }

    @Provides
    @Singleton
    @VideoApi
    static Converter.Factory provideFactory() {
        return new PlayShikimoriConverterFactory();
    }

    @Provides
    @Singleton
    @VideoApi
    static CookieJar provideCookieJar(Context context) {
        return new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
    }
}
