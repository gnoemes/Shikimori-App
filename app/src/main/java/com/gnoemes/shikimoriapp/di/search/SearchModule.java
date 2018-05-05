package com.gnoemes.shikimoriapp.di.search;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.base.modules.BaseChildFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomScope;
import com.gnoemes.shikimoriapp.presentation.view.search.SearchFragment;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;

@Module(includes = {BaseChildFragmentModule.class,
        SearchPresenterModule.class, SearchRepositoryModule.class,
        SearchInteractorModule.class, SearchUtilModule.class})
public interface SearchModule {

    @Binds
    @Named(BaseChildFragmentModule.CHILD_FRAGMENT)
    @BottomScope
    Fragment bindFragment(SearchFragment searchFragment);
}
