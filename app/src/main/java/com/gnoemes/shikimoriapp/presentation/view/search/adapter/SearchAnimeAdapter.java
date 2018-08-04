package com.gnoemes.shikimoriapp.presentation.view.search.adapter;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.presentation.view.common.adapter.BaseListAdapter;
import com.gnoemes.shikimoriapp.presentation.view.search.provider.SearchAnimeResourceProvider;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DefaultItemCallback;

public class SearchAnimeAdapter extends BaseListAdapter {

    public SearchAnimeAdapter(@NonNull SearchAnimeResourceProvider resourceProvider,
                              @NonNull ImageLoader imageLoader,
                              @NonNull DefaultItemCallback callback) {
        super(null);
        delegatesManager.addDelegate(new AnimeItemAdapterDelegate(resourceProvider, callback, imageLoader));
    }
}
