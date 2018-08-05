package com.gnoemes.shikimoriapp.di.alternative;

import com.gnoemes.shikimoriapp.data.repository.alternative.converters.EpisodeResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.alternative.converters.SeriesResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.alternative.converters.TranslationResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.alternative.converters.impl.EpisodeResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.alternative.converters.impl.SeriesResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.alternative.converters.impl.TranslationResponseConverterImpl;
import com.gnoemes.shikimoriapp.presentation.view.alternative.episodes.converter.AlternativeEpisodeViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.alternative.episodes.converter.AlternativeEpisodeViewModelConverterImpl;
import com.gnoemes.shikimoriapp.presentation.view.alternative.episodes.provider.AlternativeEpisodeResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.alternative.episodes.provider.AlternativeEpisodeResourceProviderImpl;
import com.gnoemes.shikimoriapp.presentation.view.alternative.translations.converter.AlternativeTranslationViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.alternative.translations.converter.AlternativeTranslationViewModelConverterImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface AlternativeUtilModule {

    @Binds
    EpisodeResponseConverter bindEpisodeResponseConverter(EpisodeResponseConverterImpl converter);

    @Binds
    SeriesResponseConverter bindSeriesResponseConverter(SeriesResponseConverterImpl converter);

    @Binds
    TranslationResponseConverter bindTranslationResponseConverter(TranslationResponseConverterImpl converter);

    @Binds
    AlternativeEpisodeResourceProvider bindAlternativeEpisodeResourceProvider(AlternativeEpisodeResourceProviderImpl provider);

    @Binds
    AlternativeEpisodeViewModelConverter bindAlternativeEpisodeViewModelConverter(AlternativeEpisodeViewModelConverterImpl converter);

    @Binds
    AlternativeTranslationViewModelConverter bindAlternativeTranslationViewModelConverter(AlternativeTranslationViewModelConverterImpl converter);

}
