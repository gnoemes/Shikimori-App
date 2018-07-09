package com.gnoemes.shikimoriapp.presentation.view.characters;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragmentView;

import java.util.List;

public interface CharacterDetailsView extends BaseFragmentView {
    void setData(List<BaseItem> baseItems);

    void showError();

    void hideError();

    void showNetworkError();

    void hideNetworkError();
}
