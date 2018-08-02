package com.gnoemes.shikimoriapp.presentation.view.history.adapter;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.PlaceHolderItem;
import com.gnoemes.shikimoriapp.entity.search.presentation.ProgressItemViewModel;
import com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime.AnimeDividerAdapterDelegate;
import com.gnoemes.shikimoriapp.presentation.view.search.adapter.ProgressItemAdapterDelegate;
import com.gnoemes.shikimoriapp.utils.date.converter.DateTimeConverter;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DefaultItemCallback;
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends ListDelegationAdapter<List<BaseItem>> {

    public HistoryAdapter(ImageLoader imageLoader,
                          DateTimeConverter dateTimeConverter,
                          DefaultItemCallback callback) {
        delegatesManager.addDelegate(new HistoryAdapterDelegate(imageLoader, dateTimeConverter, callback));
        delegatesManager.addDelegate(new ProgressItemAdapterDelegate());
        delegatesManager.addDelegate(new AnimeDividerAdapterDelegate());
        delegatesManager.addDelegate(new HistoryPlaceHolderAdapterDelegate());

        setItems(new ArrayList<>());
    }

    public void bindItems(List<BaseItem> baseItems) {
        items.clear();
        if (baseItems.isEmpty()) {
            items.add(new PlaceHolderItem());
        } else {
            items.addAll(baseItems);
        }
        notifyDataSetChanged();
    }

    public void insertMore(List<BaseItem> baseItems) {
        int prev = getItemCount();
        items.addAll(baseItems);
        notifyItemRangeInserted(prev, baseItems.size());
    }

    public void clearList() {
        items.clear();
        notifyDataSetChanged();
    }

    public void showPageLoading() {
        items.add(new ProgressItemViewModel());
    }

    public void hidePageLoading() {
        items.remove(items.size() - 1);
        notifyItemChanged(items.size());
    }
}
