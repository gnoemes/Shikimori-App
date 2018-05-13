package com.gnoemes.shikimoriapp.di.app.module;

import com.gnoemes.shikimoriapp.data.local.db.RateSyncDbSource;
import com.gnoemes.shikimoriapp.data.local.db.impl.RateSyncDbSourceImpl;
import com.gnoemes.shikimoriapp.data.repository.app.AuthorizationRepository;
import com.gnoemes.shikimoriapp.data.repository.app.TokenRepository;
import com.gnoemes.shikimoriapp.data.repository.app.TokenSource;
import com.gnoemes.shikimoriapp.data.repository.app.impl.AuthorizationRepositoryImpl;
import com.gnoemes.shikimoriapp.data.repository.app.impl.TokenRepositoryImpl;
import com.gnoemes.shikimoriapp.data.repository.app.impl.TokenSourceImpl;
import com.gnoemes.shikimoriapp.data.repository.rates.UserRatesRepository;
import com.gnoemes.shikimoriapp.data.repository.rates.UserRatesRepositoryImpl;
import com.gnoemes.shikimoriapp.data.repository.user.UserRepository;
import com.gnoemes.shikimoriapp.data.repository.user.UserRepositoryImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

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
}
