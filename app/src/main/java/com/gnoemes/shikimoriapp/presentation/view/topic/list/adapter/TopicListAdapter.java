package com.gnoemes.shikimoriapp.presentation.view.topic.list.adapter;

import com.gnoemes.shikimoriapp.presentation.view.common.adapter.BaseListAdapter;
import com.gnoemes.shikimoriapp.presentation.view.topic.provider.TopicResourceProvider;
import com.gnoemes.shikimoriapp.utils.date.converter.DateTimeConverter;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DefaultItemCallback;
import com.gnoemes.shikimoriapp.utils.view.LinkedItemCallback;

public class TopicListAdapter extends BaseListAdapter {

    public TopicListAdapter(ImageLoader imageLoader,
                            DefaultItemCallback callback,
                            LinkedItemCallback linkedItemCallback,
                            DefaultItemCallback userProfileCallback,
                            DateTimeConverter dateTimeConverter,
                            TopicResourceProvider resourceProvider) {
        super(resourceProvider);
        delegatesManager.addDelegate(new TopicAdapterDelegate(imageLoader, callback, linkedItemCallback, userProfileCallback, dateTimeConverter, resourceProvider));
        delegatesManager.addDelegate(new TopicLinkOnlyAdapterDelegate(imageLoader, callback, linkedItemCallback, dateTimeConverter, resourceProvider));
        delegatesManager.addDelegate(new TopicWithDescriptionAdapterDelegate(imageLoader, callback, linkedItemCallback, dateTimeConverter, resourceProvider));
        delegatesManager.addDelegate(new TopicClubAdapterDelegate(imageLoader, callback, linkedItemCallback, userProfileCallback, dateTimeConverter, resourceProvider));
    }
}
