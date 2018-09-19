package com.gnoemes.shikimoriapp.di.app.module;

import com.gnoemes.shikimoriapp.domain.app.AnalyticsInteractor;
import com.gnoemes.shikimoriapp.domain.app.AnalyticsInteractorImpl;
import com.gnoemes.shikimoriapp.domain.app.LogoutInteractor;
import com.gnoemes.shikimoriapp.domain.app.LogoutInteractorImpl;
import com.gnoemes.shikimoriapp.domain.download.DownloadInteractor;
import com.gnoemes.shikimoriapp.domain.download.DownloadInteractorImpl;
import com.gnoemes.shikimoriapp.domain.notifications.JobSchedulingInteractor;
import com.gnoemes.shikimoriapp.domain.notifications.JobSchedulingInteractorImpl;
import com.gnoemes.shikimoriapp.domain.notifications.NotificationsInteractor;
import com.gnoemes.shikimoriapp.domain.notifications.NotificationsInteractorImpl;
import com.gnoemes.shikimoriapp.domain.rates.UserRatesInteractor;
import com.gnoemes.shikimoriapp.domain.rates.UserRatesInteractorImpl;
import com.gnoemes.shikimoriapp.domain.user.UserInteractor;
import com.gnoemes.shikimoriapp.domain.user.UserInteractorImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public interface InteractorModule {

    @Binds
    @Singleton
    UserInteractor bindUserInteractor(UserInteractorImpl interactor);

    @Binds
    @Singleton
    UserRatesInteractor bindUserRatesInteractor(UserRatesInteractorImpl userRatesInteractor);

    @Binds
    @Singleton
    AnalyticsInteractor bindAnalyticsInteractor(AnalyticsInteractorImpl interactor);

    @Binds
    @Singleton
    LogoutInteractor bindLogoutInteractor(LogoutInteractorImpl interactor);

    @Binds
    @Singleton
    DownloadInteractor bindDownloadInteractor(DownloadInteractorImpl interactor);

    @Binds
    @Singleton
    NotificationsInteractor bindNotificationsInteractor(NotificationsInteractorImpl interactor);

    @Binds
    @Singleton
    JobSchedulingInteractor bindJobSchedulingInteractor(JobSchedulingInteractorImpl interactor);
}
