package com.gnoemes.shikimoriapp.di.app.module;

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
import com.gnoemes.shikimoriapp.data.repository.rates.UserRatesRepository;
import com.gnoemes.shikimoriapp.data.repository.rates.UserRatesRepositoryImpl;
import com.gnoemes.shikimoriapp.data.repository.screenshots.ScreenshotRepository;
import com.gnoemes.shikimoriapp.data.repository.screenshots.ScreenshotRepositoryImpl;
import com.gnoemes.shikimoriapp.data.repository.user.UserRepository;
import com.gnoemes.shikimoriapp.data.repository.user.UserRepositoryImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Reusable;

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
    @Reusable
    DownloadRepository bindDownloadRepository(DownloadRepositoryImpl repository);

    @Binds
    @Singleton
    DownloadSource bindDownloadSource(DownloadSourceImpl source);

    @Binds
    @Reusable
    ScreenshotRepository bindScreenshotRepository(ScreenshotRepositoryImpl repository);
}
