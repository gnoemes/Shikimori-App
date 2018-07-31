package com.gnoemes.shikimoriapp.presentation.view.forums;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gnoemes.shikimoriapp.entity.forum.domain.Forum;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragmentView;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface ForumsView extends BaseFragmentView {

    void showForums(List<Forum> forums);

    void showNetworkError();

    void hideNetworkError();
}
