package com.gnoemes.shikimoriapp.presentation.view.related;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragmentView;

import java.util.List;

public interface RelatedView extends BaseFragmentView {

    void showList(List<BaseItem> items);
}
