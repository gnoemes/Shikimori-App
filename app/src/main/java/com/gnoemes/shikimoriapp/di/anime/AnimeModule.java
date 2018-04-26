package com.gnoemes.shikimoriapp.di.anime;

import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeDetailsResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeDetailsResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.anime.repository.AnimeRepository;
import com.gnoemes.shikimoriapp.data.repository.anime.repository.AnimeRepositoryImpl;
import com.gnoemes.shikimoriapp.domain.anime.AnimeDetailsInteractor;
import com.gnoemes.shikimoriapp.domain.anime.AnimeDetailsInteractorImpl;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.AnimeDetailsViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.AnimeDetailsViewModelConverterImpl;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.AnimePresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AnimeModule {

    @Provides
    static AnimePresenter provideAnimePresenter(AnimeDetailsInteractor interactor,
                                                AnimeDetailsViewModelConverter converter) {
        return new AnimePresenter(interactor, converter);
    }

    @Binds
    abstract AnimeDetailsResponseConverter bindAnimeDetailsResponseConverter(AnimeDetailsResponseConverterImpl converter);

    @Binds
    abstract AnimeRepository bindAnimeRepository(AnimeRepositoryImpl repository);

    @Binds
    abstract AnimeDetailsInteractor bindAnimeDetailsInteractor(AnimeDetailsInteractorImpl interactor);

    @Binds
    abstract AnimeDetailsViewModelConverter bindAnimeDetailsViewModelConverter(AnimeDetailsViewModelConverterImpl converter);
}
