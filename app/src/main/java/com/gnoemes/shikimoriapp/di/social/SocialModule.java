package com.gnoemes.shikimoriapp.di.social;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.base.modules.BaseChildFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomScope;
import com.gnoemes.shikimoriapp.presentation.presenter.social.SocialPresenter;
import com.gnoemes.shikimoriapp.presentation.view.social.SocialFragment;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {BaseChildFragmentModule.class})
public interface SocialModule {

    @Provides
    static SocialPresenter provideSocialPresenter() {
        return new SocialPresenter();
    }


    @Binds
    @Named(BaseChildFragmentModule.CHILD_FRAGMENT)
    @BottomScope
    Fragment bindFragment(SocialFragment socialFragment);
}
