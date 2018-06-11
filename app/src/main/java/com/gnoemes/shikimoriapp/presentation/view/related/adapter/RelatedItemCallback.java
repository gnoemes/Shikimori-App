package com.gnoemes.shikimoriapp.presentation.view.related.adapter;

import com.gnoemes.shikimoriapp.entity.app.domain.Type;

public interface RelatedItemCallback {

    void onRelatedItemClicked(Type type, long id);
}
