package com.gnoemes.shikimoriapp.di.alternative;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.anime.AnimeInteractorModule;
import com.gnoemes.shikimoriapp.di.anime.AnimeRepositoryModule;
import com.gnoemes.shikimoriapp.di.anime.AnimeUtilsModule;
import com.gnoemes.shikimoriapp.di.base.modules.BaseChildFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomScope;
import com.gnoemes.shikimoriapp.domain.anime.series.AlternativeSeriesInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.alternative.AlternativeTranslationsPresenter;
import com.gnoemes.shikimoriapp.presentation.view.alternative.translations.AlternativeTranslationsFragment;
import com.gnoemes.shikimoriapp.presentation.view.alternative.translations.converter.AlternativeTranslationViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.TitleResourceProvider;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {BaseChildFragmentModule.class,
        AlternativeInteractorModule.class,
        AlternativeRepositoryModule.class,
        AlternativeUtilModule.class,
        AnimeInteractorModule.class,
        AnimeRepositoryModule.class,
        AnimeUtilsModule.class
})
public interface AlternativeTranslationsModule {

    @Provides
    static AlternativeTranslationsPresenter provideHistoryPresenter(AlternativeSeriesInteractor seriesInteractor,
                                                                    TitleResourceProvider titleResourceProvider,
                                                                    AlternativeTranslationViewModelConverter converter) {
        return new AlternativeTranslationsPresenter(seriesInteractor, titleResourceProvider, converter);
    }

    @Binds
    @Named(BaseChildFragmentModule.CHILD_FRAGMENT)
    @BottomScope
    Fragment bindFragment(AlternativeTranslationsFragment alternativeTranslationsFragment);
}
