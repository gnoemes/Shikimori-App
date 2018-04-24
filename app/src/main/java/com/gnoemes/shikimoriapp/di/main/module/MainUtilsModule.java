package com.gnoemes.shikimoriapp.di.main.module;

import com.gnoemes.shikimoriapp.di.main.scope.MainActivityScope;
import com.gnoemes.shikimoriapp.presentation.presenter.search.converter.AnimeViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.search.converter.AnimeViewModelConverterImpl;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.MainResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.MainResourceProviderImpl;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.TitleResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.TitleResourceProviderImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface MainUtilsModule {

    @Binds
    @MainActivityScope
    MainResourceProvider bindMainResourceProvider(MainResourceProviderImpl provider);

    @Binds
    TitleResourceProvider bindTitleResourceProvider(TitleResourceProviderImpl resourceProvider);

    @Binds
    AnimeViewModelConverter bindAnimeViewModelConverter(AnimeViewModelConverterImpl converter);
}
