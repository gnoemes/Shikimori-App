package com.gnoemes.shikimoriapp.presentation.view.anime;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeDetailsViewModel;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeLinkViewModel;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.BaseEpisodeItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.rates.domain.UserRate;
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

    @StateStrategyType(AddToEndStrategy.class)
    void showComments(List<BaseItem> baseCommentItems);

    @StateStrategyType(AddToEndStrategy.class)
    void insetMoreComments(List<BaseItem> baseCommentItems);

    @StateStrategyType(SkipStrategy.class)
    void showErrorView();

    @StateStrategyType(SkipStrategy.class)
    void hideErrorView();

    @StateStrategyType(SkipStrategy.class)
    void showSettingsWizard(boolean loadEpisode);

    @StateStrategyType(SkipStrategy.class)
    void showLinksDialog(List<AnimeLinkViewModel> animeLinkViewModels);

    @StateStrategyType(SkipStrategy.class)
    void onShowRefresh();

    @StateStrategyType(SkipStrategy.class)
    void onHideRefresh();

    @StateStrategyType(SkipStrategy.class)
    void onShowPageLoading();

    @StateStrategyType(SkipStrategy.class)
    void onHidePageLoading();

    @StateStrategyType(SkipStrategy.class)
    void showRatesDialog(UserRate data);

    @StateStrategyType(SkipStrategy.class)
    void showClearHistoryDialog();

}
