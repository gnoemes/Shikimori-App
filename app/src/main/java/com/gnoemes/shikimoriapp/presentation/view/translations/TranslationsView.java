package com.gnoemes.shikimoriapp.presentation.view.translations;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.TranslationViewModel;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragmentView;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface TranslationsView extends BaseFragmentView {

    @StateStrategyType(AddToEndStrategy.class)
    void showTranslations(List<TranslationViewModel> translations);

    @StateStrategyType(SkipStrategy.class)
    void showEmptyView();

    @StateStrategyType(SkipStrategy.class)
    void hideEmptyView();

    @StateStrategyType(SkipStrategy.class)
    void showErrorView();

    @StateStrategyType(SkipStrategy.class)
    void hideErrorView();

    @StateStrategyType(SkipStrategy.class)
    void showSettingsDialog();

    @StateStrategyType(SkipStrategy.class)
    void playVideoOnWeb(String url);

    @StateStrategyType(SkipStrategy.class)
    void showPlayerDialog();

}
