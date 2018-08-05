package com.gnoemes.shikimoriapp.presentation.view.alternative.episodes;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslationType;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragmentView;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface AlternativeEpisodesView extends BaseFragmentView {

    @StateStrategyType(AddToEndStrategy.class)
    void showList(List<BaseItem> episodes);

    @StateStrategyType(SkipStrategy.class)
    void showTranslationDialog(List<AlternativeTranslationType> types);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideErrorView();

    @StateStrategyType(SkipStrategy.class)
    void showErrorView();
}
