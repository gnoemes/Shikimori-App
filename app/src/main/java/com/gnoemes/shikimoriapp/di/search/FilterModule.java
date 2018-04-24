package com.gnoemes.shikimoriapp.di.search;

import com.gnoemes.shikimoriapp.presentation.view.search.filter.FilterResourceConverter;
import com.gnoemes.shikimoriapp.presentation.view.search.filter.FilterResourceConverterImpl;
import com.gnoemes.shikimoriapp.presentation.view.search.filter.FilterResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.search.filter.FilterResourceProviderImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface FilterModule {

    @Binds
    FilterResourceProvider bindFilterResourceProvider(FilterResourceProviderImpl provider);

    @Binds
    FilterResourceConverter bindFilterResourceConverter(FilterResourceConverterImpl resourceConverter);

}
