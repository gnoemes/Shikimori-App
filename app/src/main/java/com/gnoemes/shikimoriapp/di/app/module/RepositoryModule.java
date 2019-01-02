package com.gnoemes.shikimoriapp.di.app.module;

import android.app.NotificationManager;
import android.content.Context;

import com.gnoemes.shikimoriapp.data.local.db.RateSyncDbSource;
import com.gnoemes.shikimoriapp.data.local.db.impl.RateSyncDbSourceImpl;
import com.gnoemes.shikimoriapp.data.repository.app.AuthorizationRepository;
import com.gnoemes.shikimoriapp.data.repository.app.FirebaseAnalyticsRepository;
import com.gnoemes.shikimoriapp.data.repository.app.TokenRepository;
import com.gnoemes.shikimoriapp.data.repository.app.TokenSource;
import com.gnoemes.shikimoriapp.data.repository.app.impl.AuthorizationRepositoryImpl;
import com.gnoemes.shikimoriapp.data.repository.app.impl.FirebaseAnalyticsRepositoryImpl;
import com.gnoemes.shikimoriapp.data.repository.app.impl.TokenRepositoryImpl;
import com.gnoemes.shikimoriapp.data.repository.app.impl.TokenSourceImpl;
import com.gnoemes.shikimoriapp.data.repository.download.DownloadRepository;
import com.gnoemes.shikimoriapp.data.repository.download.DownloadRepositoryImpl;
import com.gnoemes.shikimoriapp.data.repository.download.DownloadSource;
import com.gnoemes.shikimoriapp.data.repository.download.DownloadSourceImpl;
import com.gnoemes.shikimoriapp.data.repository.notifications.JobSchedulingRepository;
import com.gnoemes.shikimoriapp.data.repository.notifications.JobSchedulingRepositoryImpl;
import com.gnoemes.shikimoriapp.data.repository.notifications.NotificationDateSource;
import com.gnoemes.shikimoriapp.data.repository.notifications.NotificationDateSourceImpl;
import com.gnoemes.shikimoriapp.data.repository.notifications.NotificationSource;
import com.gnoemes.shikimoriapp.data.repository.notifications.NotificationSourceImpl;
import com.gnoemes.shikimoriapp.data.repository.notifications.NotificationsRepository;
import com.gnoemes.shikimoriapp.data.repository.notifications.NotificationsRepositoryImpl;
import com.gnoemes.shikimoriapp.data.repository.rates.UserRatesRepository;
import com.gnoemes.shikimoriapp.data.repository.rates.UserRatesRepositoryImpl;
import com.gnoemes.shikimoriapp.data.repository.series.AgentRepository;
import com.gnoemes.shikimoriapp.data.repository.series.AgentRepositoryImpl;
import com.gnoemes.shikimoriapp.data.repository.user.UserRepository;
import com.gnoemes.shikimoriapp.data.repository.user.UserRepositoryImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public interface RepositoryModule {

    @Binds
    @Singleton
    TokenSource bindTokenSource(TokenSourceImpl source);

    @Binds
    @Singleton
    TokenRepository bindTokenRepository(TokenRepositoryImpl repository);

    @Binds
    @Singleton
    AuthorizationRepository bindAuthorizationRepository(AuthorizationRepositoryImpl repository);

    @Binds
    @Singleton
    UserRepository bindUserRepository(UserRepositoryImpl repository);

    @Binds
    @Singleton
    UserRatesRepository bindUserRatesRepository(UserRatesRepositoryImpl repository);

    @Binds
    @Singleton
    RateSyncDbSource bindRateSyncDbSource(RateSyncDbSourceImpl source);

    @Binds
    @Singleton
    FirebaseAnalyticsRepository bindFirebaseAnalyticsRepository(FirebaseAnalyticsRepositoryImpl repository);

    @Binds
    @Singleton
    DownloadRepository bindDownloadRepository(DownloadRepositoryImpl repository);

    @Binds
    @Singleton
    DownloadSource bindDownloadSource(DownloadSourceImpl source);

    @Provides
    @Singleton
    static NotificationManager bindNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Binds
    @Singleton
    NotificationSource bindNotificationSource(NotificationSourceImpl source);

    @Binds
    @Singleton
    NotificationsRepository bindNotificationsRepository(NotificationsRepositoryImpl repository);

    @Binds
    @Singleton
    NotificationDateSource bindNotificationDateSource(NotificationDateSourceImpl source);

    @Binds
    @Singleton
    JobSchedulingRepository bindJobSchedulingRepository(JobSchedulingRepositoryImpl repository);

    @Binds
    @Singleton
    AgentRepository bindAgentRepository(AgentRepositoryImpl repository);
}
