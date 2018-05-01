package com.gnoemes.shikimoriapp.di.anime;

import com.gnoemes.shikimoriapp.data.repository.anime.AnimeRepository;
import com.gnoemes.shikimoriapp.data.repository.anime.AnimeRepositoryImpl;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeDetailsResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeDetailsResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeLinkResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeLinkResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeListResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeListResponseConverterImpl;
import com.gnoemes.shikimoriapp.domain.anime.AnimeInteractor;
import com.gnoemes.shikimoriapp.domain.anime.AnimeInteractorImpl;
import com.gnoemes.shikimoriapp.domain.anime.series.SeriesInteractor;
import com.gnoemes.shikimoriapp.domain.app.UserSettingsInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.AnimePresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.converter.AnimeDetailsViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.converter.AnimeDetailsViewModelConverterImpl;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.converter.AnimeLinkViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.converter.AnimeLinkViewModelConverterImpl;
import com.gnoemes.shikimoriapp.presentation.view.anime.converter.EpisodeViewModelConverter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public interface AnimeModule {

    @Provides
    static AnimePresenter provideAnimePresenter(AnimeInteractor interactor,
                                                SeriesInteractor seriesInteractor,
                                                UserSettingsInteractor settingsInteractor,
                                                AnimeDetailsViewModelConverter converter,
                                                EpisodeViewModelConverter modelConverter,
                                                AnimeLinkViewModelConverter linkViewModelConverter) {
        return new AnimePresenter(interactor, seriesInteractor, settingsInteractor, converter, modelConverter, linkViewModelConverter);
    }

    @Binds
    AnimeDetailsResponseConverter bindAnimeDetailsResponseConverter(AnimeDetailsResponseConverterImpl converter);

    @Binds
    AnimeRepository bindAnimeRepository(AnimeRepositoryImpl repository);

    @Binds
    AnimeInteractor bindAnimeDetailsInteractor(AnimeInteractorImpl interactor);

    @Binds
    AnimeDetailsViewModelConverter bindAnimeDetailsViewModelConverter(AnimeDetailsViewModelConverterImpl converter);

    @Binds
    AnimeListResponseConverter bindAnimeListResponseConverter(AnimeListResponseConverterImpl converter);

    @Binds
    AnimeLinkViewModelConverter bindAnimeLinkViewModelConverter(AnimeLinkViewModelConverterImpl converter);

    @Binds
    AnimeLinkResponseConverter bindAnimeLinkResponseConverter(AnimeLinkResponseConverterImpl converter);
}
