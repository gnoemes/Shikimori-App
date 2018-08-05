package com.gnoemes.shikimoriapp.di.alternative;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.anime.AnimeInteractorModule;
import com.gnoemes.shikimoriapp.di.anime.AnimeRepositoryModule;
import com.gnoemes.shikimoriapp.di.anime.AnimeUtilsModule;
import com.gnoemes.shikimoriapp.di.base.modules.BaseChildFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomScope;
import com.gnoemes.shikimoriapp.domain.anime.AnimeInteractor;
import com.gnoemes.shikimoriapp.domain.anime.series.AlternativeSeriesInteractor;
import com.gnoemes.shikimoriapp.domain.app.AnalyticsInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.alternative.AlternativeEpisodesPresenter;
import com.gnoemes.shikimoriapp.presentation.view.alternative.episodes.AlternativeEpisodesFragment;
import com.gnoemes.shikimoriapp.presentation.view.alternative.episodes.converter.AlternativeEpisodeViewModelConverter;

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
public interface AlternativeEpisodesModule {


    @Provides
    static AlternativeEpisodesPresenter provideHistoryPresenter(AnimeInteractor interactor,
                                                                AlternativeSeriesInteractor seriesInteractor,
                                                                AlternativeEpisodeViewModelConverter viewModelConverter,
                                                                AnalyticsInteractor analyticsInteractor) {
        return new AlternativeEpisodesPresenter(interactor, seriesInteractor, viewModelConverter, analyticsInteractor);
    }

    @Binds
    @Named(BaseChildFragmentModule.CHILD_FRAGMENT)
    @BottomScope
    Fragment bindFragment(AlternativeEpisodesFragment alternativeEpisodesFragment);
}
