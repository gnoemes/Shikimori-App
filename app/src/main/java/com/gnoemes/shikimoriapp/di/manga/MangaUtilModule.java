package com.gnoemes.shikimoriapp.di.manga;

import com.gnoemes.shikimoriapp.data.repository.manga.converter.MangaDetailsResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.manga.converter.MangaDetailsResponseConverterImpl;
import com.gnoemes.shikimoriapp.presentation.view.manga.converter.MangaDetailsViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.manga.converter.MangaDetailsViewModelConverterImpl;
import com.gnoemes.shikimoriapp.presentation.view.manga.provider.ChapterAdaoterResourceProviderImpl;
import com.gnoemes.shikimoriapp.presentation.view.manga.provider.ChapterAdapterResourceProvider;

import dagger.Binds;
import dagger.Module;
import dagger.Reusable;

@Module
public interface MangaUtilModule {

    @Binds
    @Reusable
    ChapterAdapterResourceProvider bindChapterAdapterResourceProvider(ChapterAdaoterResourceProviderImpl provider);

    @Binds
    @Reusable
    MangaDetailsViewModelConverter bindMangaDetailsViewModelConverter(MangaDetailsViewModelConverterImpl converter);

    @Binds
    @Reusable
    MangaDetailsResponseConverter bindMangaDetailsResponseConverter(MangaDetailsResponseConverterImpl converter);
}
