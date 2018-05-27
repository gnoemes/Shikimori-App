package com.gnoemes.shikimoriapp.di.bottom;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.anime.AnimeModule;
import com.gnoemes.shikimoriapp.di.base.modules.BaseFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomChildScope;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomScope;
import com.gnoemes.shikimoriapp.di.fav.FavoriteModule;
import com.gnoemes.shikimoriapp.di.history.HistoryModule;
import com.gnoemes.shikimoriapp.di.profile.ProfileModule;
import com.gnoemes.shikimoriapp.di.search.FilterModule;
import com.gnoemes.shikimoriapp.di.search.SearchModule;
import com.gnoemes.shikimoriapp.di.similar.SimilarModule;
import com.gnoemes.shikimoriapp.di.translations.TranslationsModule;
import com.gnoemes.shikimoriapp.presentation.view.anime.AnimeFragment;
import com.gnoemes.shikimoriapp.presentation.view.bottom.SearchFragmentContainer;
import com.gnoemes.shikimoriapp.presentation.view.fav.FavoriteFragment;
import com.gnoemes.shikimoriapp.presentation.view.history.HistoryFragment;
import com.gnoemes.shikimoriapp.presentation.view.profile.ProfileFragment;
import com.gnoemes.shikimoriapp.presentation.view.search.SearchFragment;
import com.gnoemes.shikimoriapp.presentation.view.search.filter.FilterDialogFragment;
import com.gnoemes.shikimoriapp.presentation.view.similar.SimilarFragment;
import com.gnoemes.shikimoriapp.presentation.view.translations.TranslationsFragment;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = BaseFragmentModule.class)
public interface SearchTabModule {

    @BottomChildScope
    @ContributesAndroidInjector(modules = {FavoriteModule.class})
    FavoriteFragment favoriteFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = {AnimeModule.class})
    AnimeFragment animeFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = SearchModule.class)
    SearchFragment searchFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = {FilterModule.class})
    FilterDialogFragment filterFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = {TranslationsModule.class})
    TranslationsFragment translationsFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = {SimilarModule.class, AnimeModule.class})
    SimilarFragment similarFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = {ProfileModule.class})
    ProfileFragment profileFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = HistoryModule.class)
    HistoryFragment historyFragmentInjector();

    @Binds
    @BottomScope
    @Named(BaseFragmentModule.FRAGMENT)
    Fragment bindFragment(SearchFragmentContainer container);
}
