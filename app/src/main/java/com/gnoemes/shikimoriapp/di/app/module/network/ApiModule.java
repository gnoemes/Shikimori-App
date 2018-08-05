package com.gnoemes.shikimoriapp.di.app.module.network;

import com.gnoemes.shikimoriapp.data.network.AlternativeApi;
import com.gnoemes.shikimoriapp.data.network.AnimesApi;
import com.gnoemes.shikimoriapp.data.network.AuthApi;
import com.gnoemes.shikimoriapp.data.network.CalendarApi;
import com.gnoemes.shikimoriapp.data.network.CommentsApi;
import com.gnoemes.shikimoriapp.data.network.RolesApi;
import com.gnoemes.shikimoriapp.data.network.TopicApi;
import com.gnoemes.shikimoriapp.data.network.UserApi;
import com.gnoemes.shikimoriapp.data.network.VideoApi;
import com.gnoemes.shikimoriapp.di.app.qualifiers.AuthCommonApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {RetrofitModule.class, CommonNetworkModule.class, VideoNetworkModule.class,
        AuthCommonNetworkModule.class, AlternativeNetworkModule.class})
public interface ApiModule {

    @Singleton
    @Provides
    static CalendarApi bindCalendarApi(Retrofit retrofit) {
        return retrofit.create(CalendarApi.class);
    }

    @Singleton
    @Provides
    static AnimesApi bindAnimesApi(@AuthCommonApi Retrofit retrofit) {
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

    @Singleton
    @Provides
    static UserApi bindUserApi(@AuthCommonApi Retrofit retrofit) {
        return retrofit.create(UserApi.class);
    }

    @Singleton
    @Provides
    static CommentsApi bindCommentsApi(Retrofit retrofit) {
        return retrofit.create(CommentsApi.class);
    }

    @Singleton
    @Provides
    static RolesApi bindCharactersApi(Retrofit retrofit) {
        return retrofit.create(RolesApi.class);
    }

    @Singleton
    @Provides
    static TopicApi bindTopicApi(@AuthCommonApi Retrofit retrofit) {
        return retrofit.create(TopicApi.class);
    }

    @Singleton
    @Provides
    static AlternativeApi bindAlternativeApi(@com.gnoemes.shikimoriapp.di.app.qualifiers.AlternativeApi Retrofit retrofit) {
        return retrofit.create(AlternativeApi.class);
    }
}
