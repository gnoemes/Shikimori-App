package com.gnoemes.shikimoriapp.di.related;

import com.gnoemes.shikimoriapp.data.repository.related.converter.RelatedResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.related.converter.RelatedResponseConverterImpl;
import com.gnoemes.shikimoriapp.presentation.presenter.related.RelatedViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.related.RelatedViewModelConverterImpl;
import com.gnoemes.shikimoriapp.presentation.view.related.adapter.RelatedAnimeResourceProviderImpl;
import com.gnoemes.shikimoriapp.presentation.view.related.provider.RelatedAnimeResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.related.provider.RelatedMangaResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.related.provider.RelatedMangaResourceProviderImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface RelatedUtilsModule {

    @Binds
    RelatedResponseConverter bindRelatedResponseConverter(RelatedResponseConverterImpl converter);

    @Binds
    RelatedViewModelConverter bindRelatedViewModelConverter(RelatedViewModelConverterImpl converter);

    @Binds
    RelatedAnimeResourceProvider bindAnimeResourceProvider(RelatedAnimeResourceProviderImpl provider);

    @Binds
    RelatedMangaResourceProvider bindRelatedMangaResourceProvider(RelatedMangaResourceProviderImpl provider);
}
