package com.gnoemes.shikimoriapp.di.profile;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.base.modules.BaseChildFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomChildScope;
import com.gnoemes.shikimoriapp.domain.app.AnalyticsInteractor;
import com.gnoemes.shikimoriapp.domain.user.UserInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.profile.ProfilePresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.profile.converter.ProfileViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.profile.ProfileFragment;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {BaseChildFragmentModule.class, ProfileUtilModule.class})
public interface ProfileModule {

    @Provides
    static ProfilePresenter provideProfilePresenter(UserInteractor userInteractor,
                                                    ProfileViewModelConverter converter,
                                                    AnalyticsInteractor analyticsInteractor) {
        return new ProfilePresenter(userInteractor, converter, analyticsInteractor);
    }

    @Binds
    @Named(BaseChildFragmentModule.CHILD_FRAGMENT)
    @BottomChildScope
    Fragment bindFragment(ProfileFragment profileFragment);

}
