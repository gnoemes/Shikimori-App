package com.gnoemes.shikimoriapp.presentation.view.anime;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeDetailsViewModel;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeLinkViewModel;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.BaseEpisodeItem;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragmentView;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface AnimeView extends BaseFragmentView {

    @StateStrategyType(SkipStrategy.class)
    void setPage(int i);

    @StateStrategyType(AddToEndStrategy.class)
    void setAnimeData(AnimeDetailsViewModel model);

    @StateStrategyType(AddToEndStrategy.class)
    void showEpisodeList(List<BaseEpisodeItem> episodes);

    @StateStrategyType(SkipStrategy.class)
    void showErrorView();

    @StateStrategyType(SkipStrategy.class)
    void hideErrorView();

    @StateStrategyType(SkipStrategy.class)
    void showSettingsWizard();

    @StateStrategyType(SkipStrategy.class)
    void showLinksDialog(List<AnimeLinkViewModel> animeLinkViewModels);

    @StateStrategyType(SkipStrategy.class)
    void playVideoOnWeb(String embedUrl);

    @StateStrategyType(SkipStrategy.class)
    void onShowRefresh();

    @StateStrategyType(SkipStrategy.class)
    void onHideRefresh();
}
