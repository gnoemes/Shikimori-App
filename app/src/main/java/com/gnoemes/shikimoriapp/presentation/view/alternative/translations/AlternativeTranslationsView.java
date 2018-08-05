package com.gnoemes.shikimoriapp.presentation.view.alternative.translations;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.AlternativeTranslationViewModel;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragmentView;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface AlternativeTranslationsView extends BaseFragmentView {

    @StateStrategyType(AddToEndStrategy.class)
    void showTranslations(List<AlternativeTranslationViewModel> models);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideErrorView();

    @StateStrategyType(SkipStrategy.class)
    void showErrorView();

    @StateStrategyType(SkipStrategy.class)
    void showEmptyView();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideEmptyView();

    @StateStrategyType(SkipStrategy.class)
    void showSettingsDialog();
}
