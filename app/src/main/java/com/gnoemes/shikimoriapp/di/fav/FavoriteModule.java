package com.gnoemes.shikimoriapp.di.fav;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.base.modules.BaseChildFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomScope;
import com.gnoemes.shikimoriapp.domain.app.AnalyticsInteractor;
import com.gnoemes.shikimoriapp.domain.app.UserSettingsInteractor;
import com.gnoemes.shikimoriapp.domain.rates.UserRatesInteractor;
import com.gnoemes.shikimoriapp.domain.user.UserInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.fav.FavoritePresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.fav.converter.RateStatusCountConverter;
import com.gnoemes.shikimoriapp.presentation.view.fav.FavoriteFragment;
import com.gnoemes.shikimoriapp.presentation.view.fav.converter.AnimeRateViewModelConverter;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {BaseChildFragmentModule.class})
public interface FavoriteModule {

    @Provides
    static FavoritePresenter provideFavoritePresenter(UserRatesInteractor interactor,
                                                      UserSettingsInteractor settingsInteractor,
                                                      UserInteractor userInteractor,
                                                      AnimeRateViewModelConverter converter,
                                                      RateStatusCountConverter statusCountConverter,
                                                      AnalyticsInteractor analyticsInteractor) {
        return new FavoritePresenter(interactor, settingsInteractor, userInteractor, converter, statusCountConverter, analyticsInteractor);
    }

    @Binds
    @Named(BaseChildFragmentModule.CHILD_FRAGMENT)
    @BottomScope
    Fragment bindFragment(FavoriteFragment favoriteFragment);
}
