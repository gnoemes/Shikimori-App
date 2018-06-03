package com.gnoemes.shikimoriapp.di.history;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.base.modules.BaseChildFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomScope;
import com.gnoemes.shikimoriapp.domain.app.AnalyticsInteractor;
import com.gnoemes.shikimoriapp.domain.user.UserInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.history.HistoryPresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.history.converter.HistoryViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.history.HistoryFragment;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {BaseChildFragmentModule.class, HistoryUtilModule.class})
public interface HistoryModule {

    @Provides
    static HistoryPresenter provideFavoritePresenter(UserInteractor interactor,
                                                     HistoryViewModelConverter converter,
                                                     AnalyticsInteractor analyticsInteractor) {
        return new HistoryPresenter(interactor, converter, analyticsInteractor);
    }

    @Binds
    @Named(BaseChildFragmentModule.CHILD_FRAGMENT)
    @BottomScope
    Fragment bindFragment(HistoryFragment historyFragment);
}
