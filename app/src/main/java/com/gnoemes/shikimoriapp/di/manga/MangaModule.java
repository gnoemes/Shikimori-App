package com.gnoemes.shikimoriapp.di.manga;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.base.modules.BaseChildFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomScope;
import com.gnoemes.shikimoriapp.domain.app.AnalyticsInteractor;
import com.gnoemes.shikimoriapp.domain.manga.MangaInteractor;
import com.gnoemes.shikimoriapp.domain.ranobe.RanobeInteractor;
import com.gnoemes.shikimoriapp.domain.rates.UserRatesInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.converter.LinkViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.manga.MangaPresenter;
import com.gnoemes.shikimoriapp.presentation.view.manga.MangaFragment;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {
        BaseChildFragmentModule.class,
        MangaInteractorModule.class,
        MangaRepositoryModule.class,
        MangaUtilModule.class
})
public interface MangaModule {


    @Provides
    static MangaPresenter provideMangaPresenter(MangaInteractor mangaInteractor,
                                                RanobeInteractor ranobeInteractor,
                                                UserRatesInteractor userRatesInteractor,
                                                LinkViewModelConverter linkViewModelConverter,
                                                AnalyticsInteractor analyticsInteractor) {
        return new MangaPresenter(mangaInteractor, ranobeInteractor, userRatesInteractor, linkViewModelConverter, analyticsInteractor);
    }

    @Binds
    @Named(BaseChildFragmentModule.CHILD_FRAGMENT)
    @BottomScope
    Fragment bindFragment(MangaFragment mangaFragment);
}
