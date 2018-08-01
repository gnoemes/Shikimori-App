package com.gnoemes.shikimoriapp.presentation.view.userhistory.adapter;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.search.presentation.ProgressItemViewModel;
import com.gnoemes.shikimoriapp.presentation.view.search.adapter.ProgressItemAdapterDelegate;
import com.gnoemes.shikimoriapp.utils.view.DefaultItemCallback;
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserHistoryAdapter extends ListDelegationAdapter<List<BaseItem>> {

    public UserHistoryAdapter(DefaultItemCallback callback) {
        delegatesManager.addDelegate(new UserHistoryDividerAdapterDelegate());
        delegatesManager.addDelegate(new UserHistoryDateAdapterDelegate());
        delegatesManager.addDelegate(new UserHistoryValueAdapterDelegate(callback));
        delegatesManager.addDelegate(new ProgressItemAdapterDelegate());

        setItems(new ArrayList<>());
    }

    public void bindItems(List<BaseItem> historyItems) {
        items.clear();
        items.addAll(historyItems);
        notifyDataSetChanged();
    }

    public void insertMore(List<BaseItem> historyItems) {
        int prev = getItemCount();
        items.addAll(historyItems);
        notifyItemChanged(prev, getItemCount());
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
