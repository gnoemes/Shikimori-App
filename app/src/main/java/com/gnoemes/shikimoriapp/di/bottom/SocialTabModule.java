package com.gnoemes.shikimoriapp.di.bottom;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.anime.AnimeModule;
import com.gnoemes.shikimoriapp.di.base.modules.BaseFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomChildScope;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomScope;
import com.gnoemes.shikimoriapp.di.fav.FavoriteModule;
import com.gnoemes.shikimoriapp.di.history.HistoryModule;
import com.gnoemes.shikimoriapp.di.profile.ProfileModule;
import com.gnoemes.shikimoriapp.di.similar.SimilarModule;
import com.gnoemes.shikimoriapp.di.social.SocialModule;
import com.gnoemes.shikimoriapp.di.translations.TranslationsModule;
import com.gnoemes.shikimoriapp.presentation.view.anime.AnimeFragment;
import com.gnoemes.shikimoriapp.presentation.view.bottom.SocialFragmentContainer;
import com.gnoemes.shikimoriapp.presentation.view.fav.FavoriteFragment;
import com.gnoemes.shikimoriapp.presentation.view.history.HistoryFragment;
import com.gnoemes.shikimoriapp.presentation.view.profile.ProfileFragment;
import com.gnoemes.shikimoriapp.presentation.view.similar.SimilarFragment;
import com.gnoemes.shikimoriapp.presentation.view.social.SocialFragment;
import com.gnoemes.shikimoriapp.presentation.view.translations.TranslationsFragment;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = BaseFragmentModule.class)
public interface SocialTabModule {

    @BottomChildScope
    @ContributesAndroidInjector(modules = {FavoriteModule.class})
    FavoriteFragment favoriteFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = {ProfileModule.class})
    ProfileFragment profileFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = {HistoryModule.class})
    HistoryFragment historyFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = SocialModule.class)
    SocialFragment socialFragmentInejctor();

    @BottomChildScope
    @ContributesAndroidInjector(modules = {TranslationsModule.class})
    TranslationsFragment translationsFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = {SimilarModule.class, AnimeModule.class})
    SimilarFragment similarFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = {AnimeModule.class})
    AnimeFragment animeFragmentInjector();

    @Binds
    @BottomScope
    @Named(BaseFragmentModule.FRAGMENT)
    Fragment bindFragment(SocialFragmentContainer container);
}
