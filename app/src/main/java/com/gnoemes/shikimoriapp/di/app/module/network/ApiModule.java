package com.gnoemes.shikimoriapp.di.app.module.network;

import com.gnoemes.shikimoriapp.data.network.AnimesApi;
import com.gnoemes.shikimoriapp.data.network.AuthApi;
import com.gnoemes.shikimoriapp.data.network.CalendarApi;
import com.gnoemes.shikimoriapp.data.network.VideoApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {RetrofitModule.class, CommonNetworkModule.class, VideoNetworkModule.class,
        AuthCommonNetworkModule.class})
public interface ApiModule {

    @Singleton
    @Provides
    static CalendarApi bindCalendarApi(Retrofit retrofit) {
        return retrofit.create(CalendarApi.class);
    }

    @Singleton
    @Provides
    static AnimesApi bindAnimesApi(Retrofit retrofit) {
        return retrofit.create(AnimesApi.class);
    }

    @Singleton
    @Provides
    static VideoApi bindVideoApi(@com.gnoemes.shikimoriapp.di.app.qualifiers.VideoApi Retrofit retrofit) {
        return retrofit.create(VideoApi.class);
    }

    @Singleton
    @Provides
    static AuthApi bindAuthApi(Retrofit retrofit) {
        return retrofit.create(AuthApi.class);
    }
}
