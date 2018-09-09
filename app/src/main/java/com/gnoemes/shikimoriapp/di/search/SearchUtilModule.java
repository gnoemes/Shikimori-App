package com.gnoemes.shikimoriapp.di.search;

import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeListResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeListResponseConverterImpl;
import com.gnoemes.shikimoriapp.domain.search.SearchQueryBuilder;
import com.gnoemes.shikimoriapp.domain.search.SearchQueryBuilderImpl;
import com.gnoemes.shikimoriapp.presentation.presenter.search.converter.CharacterConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.search.converter.CharacterConverterImpl;
import com.gnoemes.shikimoriapp.presentation.presenter.search.converter.PersonConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.search.converter.PersonConverterImpl;
import com.gnoemes.shikimoriapp.presentation.view.search.provider.SearchAnimeResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.search.provider.SearchAnimeResourceProviderImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface SearchUtilModule {

    @Binds
    AnimeListResponseConverter bindAnimeListResponseConverter(AnimeListResponseConverterImpl converter);

    @Binds
    SearchQueryBuilder bindSearchQueryBuilder(SearchQueryBuilderImpl queryBuilder);

    @Binds
    SearchAnimeResourceProvider bindSearchAnimeResourceProvider(SearchAnimeResourceProviderImpl resourceProvider);

    @Binds
    CharacterConverter bindCharacterConverter(CharacterConverterImpl converter);

    @Binds
    PersonConverter bindPersonConverter(PersonConverterImpl converter);
}
