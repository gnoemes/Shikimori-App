package com.gnoemes.shikimoriapp.di.similar;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.anime.AnimeRepoAndInteractorModule;
import com.gnoemes.shikimoriapp.di.anime.AnimeUtilsModule;
import com.gnoemes.shikimoriapp.di.base.modules.BaseChildFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomChildScope;
import com.gnoemes.shikimoriapp.domain.anime.AnimeInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.search.converter.AnimeViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.similar.SimilarPresenter;
import com.gnoemes.shikimoriapp.presentation.view.search.provider.SearchAnimeResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.search.provider.SearchAnimeResourceProviderImpl;
import com.gnoemes.shikimoriapp.presentation.view.similar.SimilarFragment;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {BaseChildFragmentModule.class, AnimeUtilsModule.class,
        AnimeRepoAndInteractorModule.class})
public interface SimilarModule {

    @Provides
    static SimilarPresenter provideSimilarPresenter(AnimeInteractor interactor,
                                                    AnimeViewModelConverter converter) {
        return new SimilarPresenter(interactor, converter);
    }

    @Binds
    @BottomChildScope
    @Named(BaseChildFragmentModule.CHILD_FRAGMENT)
    Fragment bindFragment(SimilarFragment fragment);

    @Binds
    SearchAnimeResourceProvider bindSearchAnimeResourceProvider(SearchAnimeResourceProviderImpl resourceProvider);
}
