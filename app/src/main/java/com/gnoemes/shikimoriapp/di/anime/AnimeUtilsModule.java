package com.gnoemes.shikimoriapp.di.anime;

import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeDetailsResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeDetailsResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeFranchiseResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeFranchiseResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeLinkResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeLinkResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeListResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeListResponseConverterImpl;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.converter.AnimeDetailsViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.converter.AnimeDetailsViewModelConverterImpl;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.converter.AnimeLinkViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.converter.AnimeLinkViewModelConverterImpl;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.provider.AnimeDetailsResourceProvider;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.provider.AnimeDetailsResourceProviderImpl;
import com.gnoemes.shikimoriapp.presentation.view.anime.converter.AnimeFranchiseNodeToStringConverter;
import com.gnoemes.shikimoriapp.presentation.view.anime.converter.AnimeFranchiseNodeToStringConverterImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface AnimeUtilsModule {

    @Binds
    AnimeDetailsViewModelConverter bindAnimeDetailsViewModelConverter(AnimeDetailsViewModelConverterImpl converter);

    @Binds
    AnimeListResponseConverter bindAnimeListResponseConverter(AnimeListResponseConverterImpl converter);

    @Binds
    AnimeLinkViewModelConverter bindAnimeLinkViewModelConverter(AnimeLinkViewModelConverterImpl converter);

    @Binds
    AnimeLinkResponseConverter bindAnimeLinkResponseConverter(AnimeLinkResponseConverterImpl converter);

    @Binds
    AnimeDetailsResponseConverter bindAnimeDetailsResponseConverter(AnimeDetailsResponseConverterImpl converter);

    @Binds
    AnimeDetailsResourceProvider bindAnimeDetailsResourceProvider(AnimeDetailsResourceProviderImpl provider);

    @Binds
    AnimeFranchiseResponseConverter bindAnimeFranchiseResponseConverter(AnimeFranchiseResponseConverterImpl converter);

    @Binds
    AnimeFranchiseNodeToStringConverter bindAnimeFranchiseNodeToStringConverter(AnimeFranchiseNodeToStringConverterImpl converter);
}
