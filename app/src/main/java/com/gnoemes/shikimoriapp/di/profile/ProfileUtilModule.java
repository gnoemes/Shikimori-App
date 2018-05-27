package com.gnoemes.shikimoriapp.di.profile;

import com.gnoemes.shikimoriapp.presentation.presenter.profile.converter.ProfileViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.profile.converter.ProfileViewModelConverterImpl;
import com.gnoemes.shikimoriapp.presentation.view.profile.provider.ProfileResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.profile.provider.ProfileResourceProviderImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface ProfileUtilModule {

    @Binds
    ProfileViewModelConverter bindProfileViewModelConverter(ProfileViewModelConverterImpl converter);

    @Binds
    ProfileResourceProvider bindProfileResourceProvider(ProfileResourceProviderImpl provider);
}
