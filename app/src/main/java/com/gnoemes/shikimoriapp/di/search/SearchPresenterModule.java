package com.gnoemes.shikimoriapp.di.search;

import com.gnoemes.shikimoriapp.domain.search.SearchInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.search.SearchPresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.search.converter.AnimeViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.TitleResourceProvider;

import dagger.Module;
import dagger.Provides;

@Module
public interface SearchPresenterModule {

    @Provides
    static SearchPresenter provideSearchPresenter(TitleResourceProvider resourceProvider,
                                                  SearchInteractor searchInteractor,
                                                  AnimeViewModelConverter converter) {
        return new SearchPresenter(resourceProvider, searchInteractor, converter);
    }

}
