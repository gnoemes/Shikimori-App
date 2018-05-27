package com.gnoemes.shikimoriapp.presentation.view.search.adapter;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.search.presentation.BaseSearchItem;
import com.gnoemes.shikimoriapp.entity.search.presentation.ProgressItemViewModel;
import com.gnoemes.shikimoriapp.presentation.view.search.provider.SearchAnimeResourceProvider;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DefaultItemCallback;
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchAnimeAdapter extends ListDelegationAdapter<List<BaseItem>> {

    public SearchAnimeAdapter(@NonNull SearchAnimeResourceProvider resourceProvider,
                              @NonNull ImageLoader imageLoader,
                              @NonNull DefaultItemCallback callback) {

        delegatesManager.addDelegate(new AnimeItemAdapterDelegate(resourceProvider, callback, imageLoader));
        delegatesManager.addDelegate(new ProgressItemAdapterDelegate());

        setItems(new ArrayList<>());
    }

    public void bindItems(List<BaseSearchItem> searchItems) {
        items.clear();
        items.addAll(searchItems);
        notifyDataSetChanged();
    }

    public void insertMore(List<BaseSearchItem> searchItems) {
        int prev = getItemCount();
        items.addAll(searchItems);
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
