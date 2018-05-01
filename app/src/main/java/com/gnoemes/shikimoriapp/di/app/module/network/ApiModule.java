package com.gnoemes.shikimoriapp.di.app.module.network;

import com.gnoemes.shikimoriapp.data.network.AnimesApi;
import com.gnoemes.shikimoriapp.data.network.CalendarApi;
import com.gnoemes.shikimoriapp.data.network.VideoApi;
import com.gnoemes.shikimoriapp.di.app.qualifiers.CommonApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {RetrofitModule.class, CommonNetworkModule.class, VideoNetworkModule.class})
public interface ApiModule {

    @Singleton
    @Provides
    static CalendarApi bindCalendarApi(@CommonApi Retrofit retrofit) {
        return retrofit.create(CalendarApi.class);
    }

    @Singleton
    @Provides
    static AnimesApi bindAnimesApi(@CommonApi Retrofit retrofit) {
        return retrofit.create(AnimesApi.class);
    }

    @Singleton
    @Provides
    static VideoApi bindVideoApi(@com.gnoemes.shikimoriapp.di.app.qualifiers.VideoApi Retrofit retrofit) {
        return retrofit.create(VideoApi.class);
    }
}
