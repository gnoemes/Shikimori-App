package com.gnoemes.shikimoriapp.presentation.view.history.adapter;

import com.gnoemes.shikimoriapp.presentation.view.common.adapter.BaseListAdapter;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.AdapterResourceProvider;
import com.gnoemes.shikimoriapp.utils.date.converter.DateTimeConverter;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DefaultItemCallback;

public class HistoryAdapter extends BaseListAdapter {

    public HistoryAdapter(ImageLoader imageLoader,
                          DateTimeConverter dateTimeConverter,
                          AdapterResourceProvider resourceProvider,
                          DefaultItemCallback callback) {
        super(resourceProvider);
        delegatesManager.addDelegate(new HistoryAdapterDelegate(imageLoader, dateTimeConverter, callback));
    }
}