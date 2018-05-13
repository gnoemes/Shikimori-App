package com.gnoemes.shikimoriapp.di.anime;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.base.modules.BaseChildFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomChildScope;
import com.gnoemes.shikimoriapp.domain.anime.AnimeInteractor;
import com.gnoemes.shikimoriapp.domain.anime.AnimeInteractorImpl;
import com.gnoemes.shikimoriapp.domain.anime.series.SeriesInteractor;
import com.gnoemes.shikimoriapp.domain.app.UserSettingsInteractor;
import com.gnoemes.shikimoriapp.domain.comments.CommentsInteractor;
import com.gnoemes.shikimoriapp.domain.rates.UserRatesInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.AnimePresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.converter.AnimeDetailsViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.converter.AnimeLinkViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.provider.AnimeDetailsResourceProvider;
import com.gnoemes.shikimoriapp.presentation.presenter.comments.converter.CommentsViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.anime.AnimeFragment;
import com.gnoemes.shikimoriapp.presentation.view.anime.converter.EpisodeViewModelConverter;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {BaseChildFragmentModule.class, SeriesModule.class,
        AnimeUtilsModule.class, AnimeRepositoryModule.class, CommentsModule.class})
public interface AnimeModule {

    @Binds
    @Named(BaseChildFragmentModule.CHILD_FRAGMENT)
    @BottomChildScope
    Fragment bindFragment(AnimeFragment animeFragment);

    @Provides
    static AnimePresenter provideAnimePresenter(AnimeInteractor interactor,
                                                SeriesInteractor seriesInteractor,
                                                UserSettingsInteractor settingsInteractor,
                                                CommentsInteractor commentsInteractor,
                                                UserRatesInteractor ratesInteractor,
                                                AnimeDetailsViewModelConverter converter,
                                                EpisodeViewModelConverter modelConverter,
                                                AnimeLinkViewModelConverter linkViewModelConverter,
                                                CommentsViewModelConverter commentsViewModelConverter,
                                                AnimeDetailsResourceProvider resourceProvider) {
        return new AnimePresenter(interactor, seriesInteractor, settingsInteractor, commentsInteractor, ratesInteractor, converter, modelConverter, linkViewModelConverter, commentsViewModelConverter, resourceProvider);
    }

    @Binds
    AnimeInteractor bindAnimeDetailsInteractor(AnimeInteractorImpl interactor);
}

