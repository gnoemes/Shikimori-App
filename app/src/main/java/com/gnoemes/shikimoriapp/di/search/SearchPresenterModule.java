package com.gnoemes.shikimoriapp.di.search;

import com.gnoemes.shikimoriapp.domain.app.AnalyticsInteractor;
import com.gnoemes.shikimoriapp.domain.search.SearchInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.search.SearchPresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.search.converter.AnimeViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.search.converter.CharacterConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.search.converter.MangaViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.search.converter.PersonConverter;
import com.gnoemes.shikimoriapp.presentation.view.search.filter.FilterResourceProvider;

import dagger.Module;
import dagger.Provides;

@Module
public interface SearchPresenterModule {

    @Provides
    static SearchPresenter provideSearchPresenter(SearchInteractor searchInteractor,
                                                  AnimeViewModelConverter converter,
                                                  MangaViewModelConverter modelConverter,
                                                  CharacterConverter characterConverter,
                                                  PersonConverter personConverter,
                                                  AnalyticsInteractor analyticsInteractor,
                                                  FilterResourceProvider filterResourceProvider
    ) {
        return new SearchPresenter(searchInteractor, analyticsInteractor, converter, modelConverter, characterConverter, personConverter, filterResourceProvider);
    }

}
