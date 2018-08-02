package com.gnoemes.shikimoriapp.di.history;

import com.gnoemes.shikimoriapp.domain.history.HistorySortConverter;
import com.gnoemes.shikimoriapp.domain.history.HistorySortConverterImpl;
import com.gnoemes.shikimoriapp.domain.search.SearchQueryBuilder;
import com.gnoemes.shikimoriapp.domain.search.SearchQueryBuilderImpl;
import com.gnoemes.shikimoriapp.presentation.presenter.history.converter.HistoryViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.history.converter.HistoryViewModelConverterImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface HistoryUtilModule {

    @Binds
    SearchQueryBuilder bindSearchQueryBuilder(SearchQueryBuilderImpl queryBuilder);

    @Binds
    HistoryViewModelConverter bindHistoryViewModelConverter(HistoryViewModelConverterImpl modelConverter);

    @Binds
    HistorySortConverter bindHistorySortConverter(HistorySortConverterImpl converter);
}
