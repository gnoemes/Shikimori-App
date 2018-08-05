package com.gnoemes.shikimoriapp.presentation.view.person;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragmentView;

import java.util.List;

public interface PersonView extends BaseFragmentView {
    void setData(List<BaseItem> baseItems);

    void showError();

    void hideError();

    void showNetworkError();

    void hideNetworkError();
}
