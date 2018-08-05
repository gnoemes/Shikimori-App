package com.gnoemes.shikimoriapp.di.history;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.anime.AnimeRepositoryModule;
import com.gnoemes.shikimoriapp.di.anime.AnimeUtilsModule;
import com.gnoemes.shikimoriapp.di.base.modules.BaseChildFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomScope;
import com.gnoemes.shikimoriapp.di.search.SearchRepositoryModule;
import com.gnoemes.shikimoriapp.domain.history.HistoryInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.history.HistoryPresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.history.converter.HistoryViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.history.HistoryFragment;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {BaseChildFragmentModule.class,
        HistoryInteractorModule.class,
        HistoryUtilModule.class,
        SearchRepositoryModule.class,
        AnimeRepositoryModule.class,
        AnimeUtilsModule.class
})
public interface HistoryModule {

    @Provides
    static HistoryPresenter provideHistoryPresenter(HistoryInteractor interactor,
                                                    HistoryViewModelConverter converter) {
        return new HistoryPresenter(interactor, converter);
    }

    @Binds
    @Named(BaseChildFragmentModule.CHILD_FRAGMENT)
    @BottomScope
    Fragment bindFragment(HistoryFragment historyFragment);
}
