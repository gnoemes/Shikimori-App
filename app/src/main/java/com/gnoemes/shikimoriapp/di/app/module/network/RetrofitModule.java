package com.gnoemes.shikimoriapp.di.app.module.network;

import com.gnoemes.shikimoriapp.utils.date.DateTimeResponseConverter;
import com.gnoemes.shikimoriapp.utils.date.DateTimeResponseConverterImpl;
import com.gnoemes.shikimoriapp.utils.net.UserAgentInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public interface RetrofitModule {

    @Provides
    @Singleton
    static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @Singleton
    static UserAgentInterceptor provideUserAgentIntercepter() {
        return new UserAgentInterceptor();
    }

    @Provides
    @Singleton
    static Converter.Factory provideFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    static Gson provdeGson(DateTimeResponseConverter dateTimeResponseConverter) {
        return new GsonBuilder()
                .registerTypeAdapter(DateTime.class, dateTimeResponseConverter)
                .create();
    }

    @Binds
    DateTimeResponseConverter bindDateTimeConverter(DateTimeResponseConverterImpl converter);
}
