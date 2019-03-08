package com.gnoemes.shikimoriapp.di.anime;

import com.gnoemes.shikimoriapp.data.local.db.EpisodeDbSource;
import com.gnoemes.shikimoriapp.data.local.db.converters.EpisodeDAOConverter;
import com.gnoemes.shikimoriapp.data.local.db.converters.EpisodeDAOConverterImpl;
import com.gnoemes.shikimoriapp.data.local.db.impl.EpisodeDbSourceImpl;
import com.gnoemes.shikimoriapp.data.repository.series.SeriesRepository;
import com.gnoemes.shikimoriapp.data.repository.series.SeriesRepositoryImpl;
import com.gnoemes.shikimoriapp.data.repository.series.converters.PlayEpisodeConverter;
import com.gnoemes.shikimoriapp.data.repository.series.converters.PlayEpisodeResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.series.converters.PlayVideoResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.series.converters.SeriesResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.series.converters.TranslationResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.series.converters.hostings.SibnetVideoResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.series.converters.hostings.VkVideoResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.series.converters.hostings.impl.SibnetVideoResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.series.converters.hostings.impl.VkVideoResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.series.converters.impl.PlayEpisodeConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.series.converters.impl.PlayEpisodeResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.series.converters.impl.PlayVideoResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.series.converters.impl.SeriesResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.series.converters.impl.TranslationResponseConverterImpl;
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
    TranslationResponseConverter bindTranslationResponseConverter(TranslationResponseConverterImpl converter);

    @Binds
    EpisodeDbSource bindEpisodeDbSource(EpisodeDbSourceImpl source);

    @Binds
    EpisodeDAOConverter bindEpisodeDaoConverter(EpisodeDAOConverterImpl converter);

    @Binds
    SeriesInteractor bindSeriesInteractor(SeriesInteractorImpl interactor);

    @Binds
    EpisodeViewModelConverter bindEpisodeViewModelConverter(EpisodeViewModelConverterImpl converter);

    @Binds
    PlayEpisodeConverter bindPlayEpisodeConverter(PlayEpisodeConverterImpl converter);

    @Binds
    PlayEpisodeResponseConverter bindPlayEpisodeResponseConverter(PlayEpisodeResponseConverterImpl converter);

    @Binds
    PlayVideoResponseConverter bindPlayVideoResponseConverter(PlayVideoResponseConverterImpl converter);

    @Binds
    SibnetVideoResponseConverter bindSibnetVideoResponseConverter(SibnetVideoResponseConverterImpl converter);

    @Binds
    VkVideoResponseConverter bindVkVideoResponseConverter(VkVideoResponseConverterImpl converter);

}
