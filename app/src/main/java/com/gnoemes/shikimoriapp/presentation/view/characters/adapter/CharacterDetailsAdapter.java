package com.gnoemes.shikimoriapp.presentation.view.characters.adapter;

import com.gnoemes.shikimoriapp.presentation.view.common.adapter.BaseListAdapter;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;

public class CharacterDetailsAdapter extends BaseListAdapter {

    public CharacterDetailsAdapter(ImageLoader imageLoader,
                                   CharacterItemCallback callback) {
        super(null);
        delegatesManager.addDelegate(new CharacterHeadAdapterDelegate(imageLoader));
        delegatesManager.addDelegate(new CharacterRelatedAdapterDelegate(imageLoader, callback));
    }
}
