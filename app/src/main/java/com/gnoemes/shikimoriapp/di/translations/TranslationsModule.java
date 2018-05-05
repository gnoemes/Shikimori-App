package com.gnoemes.shikimoriapp.di.translations;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.anime.SeriesModule;
import com.gnoemes.shikimoriapp.di.base.modules.BaseChildFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomChildScope;
import com.gnoemes.shikimoriapp.domain.anime.series.SeriesInteractor;
import com.gnoemes.shikimoriapp.domain.app.UserSettingsInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.translations.TranslationsPresenter;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.TitleResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.translations.TranslationsFragment;
import com.gnoemes.shikimoriapp.presentation.view.translations.converter.TranslationViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.translations.converter.TranslationViewModelConverterImpl;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {BaseChildFragmentModule.class, SeriesModule.class})
public interface TranslationsModule {

    @Binds
    @BottomChildScope
    @Named(BaseChildFragmentModule.CHILD_FRAGMENT)
    Fragment bindFragment(TranslationsFragment fragment);

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
