package com.gnoemes.shikimoriapp.presentation.view.topic.list.adapter;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.search.presentation.ProgressItemViewModel;
import com.gnoemes.shikimoriapp.entity.topic.presentation.TopicPlaceholderItem;
import com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime.AnimeDividerAdapterDelegate;
import com.gnoemes.shikimoriapp.presentation.view.search.adapter.ProgressItemAdapterDelegate;
import com.gnoemes.shikimoriapp.presentation.view.topic.provider.TopicResourceProvider;
import com.gnoemes.shikimoriapp.utils.date.converter.DateTimeConverter;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DefaultItemCallback;
import com.gnoemes.shikimoriapp.utils.view.LinkedItemCallback;
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;

import java.util.ArrayList;
import java.util.List;

public class TopicAdapter extends ListDelegationAdapter<List<BaseItem>> {

    public TopicAdapter(ImageLoader imageLoader,
                        DefaultItemCallback callback,
                        LinkedItemCallback linkedItemCallback,
                        DefaultItemCallback userProfileCallback,
                        DateTimeConverter dateTimeConverter,
                        TopicResourceProvider resourceProvider) {
        delegatesManager.addDelegate(new TopicAdapterDelegate(imageLoader, callback, linkedItemCallback, userProfileCallback, dateTimeConverter, resourceProvider));
        delegatesManager.addDelegate(new TopicLinkOnlyAdapterDelegate(imageLoader, callback, linkedItemCallback, dateTimeConverter, resourceProvider));
        delegatesManager.addDelegate(new TopicWithDescriptionAdapterDelegate(imageLoader, callback, linkedItemCallback, dateTimeConverter, resourceProvider));
        delegatesManager.addDelegate(new TopicClubAdapterDelegate(imageLoader, callback, linkedItemCallback, userProfileCallback, dateTimeConverter, resourceProvider));
        delegatesManager.addDelegate(new AnimeDividerAdapterDelegate());
        delegatesManager.addDelegate(new TopicPlaceholderAdapterDelegate());
        delegatesManager.addDelegate(new ProgressItemAdapterDelegate());

        setItems(new ArrayList<>());
    }

    public void bindItems(List<BaseItem> baseItems) {
        items.clear();
        if (!baseItems.isEmpty()) {
            items.addAll(baseItems);
            notifyDataSetChanged();
        } else {
            items.add(new TopicPlaceholderItem());
        }
    }

    public void insertMoreItems(List<BaseItem> baseItems) {
        if (!baseItems.isEmpty() && baseItems.size() - 1 > 0) {
            int prev = getItemCount();
            //Api bug, on pagination previous item returns too
            items.addAll(baseItems.subList(1, baseItems.size()));
            notifyItemChanged(prev, getItemCount());
        }
    }

    public void clearItems() {
        items.clear();
    }

    public void showProgress() {
        items.add(new ProgressItemViewModel());
    }

    public void hideProgress() {
        items.remove(items.size() - 1);
    }

}
