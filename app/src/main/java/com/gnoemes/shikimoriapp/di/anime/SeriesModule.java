package com.gnoemes.shikimoriapp.di.anime;

import com.gnoemes.shikimoriapp.data.local.db.EpisodeDbSource;
import com.gnoemes.shikimoriapp.data.local.db.converters.EpisodeDAOConverter;
import com.gnoemes.shikimoriapp.data.local.db.converters.EpisodeDAOConverterImpl;
import com.gnoemes.shikimoriapp.data.local.db.impl.EpisodeDbSourceImpl;
import com.gnoemes.shikimoriapp.data.repository.anime.series.SeriesRepository;
import com.gnoemes.shikimoriapp.data.repository.anime.series.SeriesRepositoryImpl;
import com.gnoemes.shikimoriapp.data.repository.anime.series.converters.EpisodeResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.series.converters.PlayEpisodeConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.series.converters.PlayEpisodeResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.series.converters.SeriesResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.series.converters.TranslationResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.series.converters.impl.EpisodeResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.anime.series.converters.impl.PlayEpisodeConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.anime.series.converters.impl.PlayEpisodeResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.anime.series.converters.impl.SeriesResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.anime.series.converters.impl.TranslationResponseConverterImpl;
import com.gnoemes.shikimoriapp.domain.anime.series.SeriesInteractor;
import com.gnoemes.shikimoriapp.domain.anime.series.SeriesInteractorImpl;
import com.gnoemes.shikimoriapp.presentation.view.anime.converter.EpisodeViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.anime.converter.EpisodeViewModelConverterImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface SeriesModule {

    @Binds
    SeriesRepository bindSeriesRepository(SeriesRepositoryImpl repository);

    @Binds
    SeriesResponseConverter bindSeriesResponseConverter(SeriesResponseConverterImpl converter);

    @Binds
    EpisodeResponseConverter bindEpisodeResponseConverter(EpisodeResponseConverterImpl converter);

    @Binds
    EpisodeDbSource bindEpisodeDbSource(EpisodeDbSourceImpl source);

    @Binds
    EpisodeDAOConverter bindEpisodeDaoConverter(EpisodeDAOConverterImpl converter);

    @Binds
    SeriesInteractor bindSeriesInteractor(SeriesInteractorImpl interactor);

    @Binds
    TranslationResponseConverter bindTranslationResponseConverter(TranslationResponseConverterImpl converter);

    @Binds
    EpisodeViewModelConverter bindEpisodeViewModelConverter(EpisodeViewModelConverterImpl converter);

    @Binds
    PlayEpisodeConverter bindPlayEpisodeConverter(PlayEpisodeConverterImpl converter);

    @Binds
    PlayEpisodeResponseConverter bindPlayEpisodeResponseConverter(PlayEpisodeResponseConverterImpl converter);

}
