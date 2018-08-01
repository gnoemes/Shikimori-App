package com.gnoemes.shikimoriapp.di.userhistory;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.base.modules.BaseChildFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomScope;
import com.gnoemes.shikimoriapp.domain.app.AnalyticsInteractor;
import com.gnoemes.shikimoriapp.domain.user.UserInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.history.UserHistoryPresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.history.converter.UserHistoryViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.userhistory.UserHistoryFragment;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {BaseChildFragmentModule.class, UserHistoryUtilModule.class})
public interface UserHistoryModule {

    @Provides
    static UserHistoryPresenter provideFavoritePresenter(UserInteractor interactor,
                                                         UserHistoryViewModelConverter converter,
                                                         AnalyticsInteractor analyticsInteractor) {
        return new UserHistoryPresenter(interactor, converter, analyticsInteractor);
    }

    @Binds
    @Named(BaseChildFragmentModule.CHILD_FRAGMENT)
    @BottomScope
    Fragment bindFragment(UserHistoryFragment userHistoryFragment);
}
