package com.gnoemes.shikimoriapp.presentation.view.common.adapter;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.PlaceHolderItem;
import com.gnoemes.shikimoriapp.entity.search.presentation.ProgressItemViewModel;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.AdapterResourceProvider;
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseListAdapter extends ListDelegationAdapter<List<BaseItem>> {

    @Nullable
    protected AdapterResourceProvider resourceProvider;
    private ProgressItemViewModel progressItem;

    public BaseListAdapter(@Nullable AdapterResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;
        delegatesManager.addDelegate(new TopDividerAdapterDelegate());
        delegatesManager.addDelegate(new DoubleDividerAdapterDelegate());
        delegatesManager.addDelegate(new BottomDividerAdapterDelegate());
        delegatesManager.addDelegate(new ProgressItemAdapterDelegate());
        delegatesManager.addDelegate(new PlaceholderAdapterDelegate());

        setItems(new ArrayList<>());

        progressItem = new ProgressItemViewModel();
    }

    public void bindItems(List<BaseItem> baseItems) {
        items.clear();
        if (baseItems.isEmpty()) {
            if (resourceProvider != null) {
                items.add(new PlaceHolderItem(resourceProvider.getPlaceholderMessage()));
            }
        } else {
//            baseItems.add(0, new TopDividerItem());
            items.addAll(baseItems);
        }
        notifyDataSetChanged();
    }

    public void insertMore(List<BaseItem> baseItems) {
        int prev = getItemCount();
        items.addAll(baseItems);
        notifyItemChanged(prev, getItemCount());
    }

    public void clearList() {
        items.clear();
        notifyDataSetChanged();
    }

    public void showPageLoading() {
        if (!items.contains(progressItem)) {
            items.add(progressItem);
            notifyItemChanged(items.size() - 1);
        }
    }

    public void hidePageLoading() {
        if (items.contains(progressItem)) {
            items.remove(progressItem);
            notifyItemRemoved(items.size());
        }
    }
}
