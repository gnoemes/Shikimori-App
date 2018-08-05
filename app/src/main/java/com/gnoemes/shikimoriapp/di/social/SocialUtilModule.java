package com.gnoemes.shikimoriapp.di.social;

import com.gnoemes.shikimoriapp.presentation.presenter.social.provider.SocialResourceProvider;
import com.gnoemes.shikimoriapp.presentation.presenter.social.provider.SocialResourceProviderImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface SocialUtilModule {

    @Binds
    SocialResourceProvider bindSocialResourceProvider(SocialResourceProviderImpl provider);
}
