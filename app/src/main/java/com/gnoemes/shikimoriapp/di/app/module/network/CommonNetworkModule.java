package com.gnoemes.shikimoriapp.di.app.module.network;

import com.gnoemes.shikimoriapp.BuildConfig;
import com.gnoemes.shikimoriapp.di.app.qualifiers.CommonApi;
import com.gnoemes.shikimoriapp.entity.app.data.AppConfig;

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
    @CommonApi
    static OkHttpClient provideOkHttpClient(HttpLoggingInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(AppConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(AppConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    @CommonApi
    static Retrofit.Builder provideRetrofitBuilder(Converter.Factory factory, @CommonApi OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(factory);
    }

    @Provides
    @Singleton
    @CommonApi
    static Retrofit provideRetrofit(@CommonApi Retrofit.Builder builder) {
        return builder.baseUrl(BuildConfig.ShikimoriBaseUrl).build();
    }

//    @Provides
//    @Singleton
//    @ClientCacheApi
//    static OkHttpClient provdeOkHttpClientCache(HttpLoggingInterceptor interceptor, Context context) {
//        return new OkHttpClient.Builder()
//                .addInterceptor(interceptor)
//                .cache(new Cache(context.getCacheDir(), 1024 * 1024))
//                .connectTimeout(AppConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .readTimeout(AppConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .build();
//    }
}
