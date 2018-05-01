package com.gnoemes.shikimoriapp.di.translations;

import com.gnoemes.shikimoriapp.domain.anime.series.SeriesInteractor;
import com.gnoemes.shikimoriapp.domain.app.UserSettingsInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.translations.TranslationsPresenter;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.TitleResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.translations.converter.TranslationViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.translations.converter.TranslationViewModelConverterImpl;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public interface TranslationsModule {

    @Provides
    static TranslationsPresenter provideTranslationsPresenter(SeriesInteractor interactor,
                                                              UserSettingsInteractor settingsInteractor,
                                                              TitleResourceProvider resourceProvider,
                                                              TranslationViewModelConverter converter) {
        return new TranslationsPresenter(interactor, settingsInteractor, resourceProvider, converter);
    }

    @Binds
    TranslationViewModelConverter bindTranslationViewModelConverter(TranslationViewModelConverterImpl converter);
}
