package com.gnoemes.shikimoriapp.presentation.view.person.adapter;

import com.gnoemes.shikimoriapp.presentation.view.common.adapter.BaseListAdapter;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;

public class PersonDetailsAdapter extends BaseListAdapter {

    public PersonDetailsAdapter(ImageLoader imageLoader,
                                PersonItemCallback callback) {
        super(null);

        delegatesManager.addDelegate(new PersonHeadAdapterDelegate(imageLoader));
        delegatesManager.addDelegate(new PersonRelatedAdapterDelegate(imageLoader, callback));
    }
}
