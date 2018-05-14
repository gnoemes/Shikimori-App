package com.gnoemes.shikimoriapp.presentation.view.fav.adapter;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.entity.rates.presentation.AnimeRatePlaceholder;
import com.gnoemes.shikimoriapp.entity.rates.presentation.AnimeRateProgressItem;
import com.gnoemes.shikimoriapp.entity.rates.presentation.BaseAnimeRateItem;
import com.gnoemes.shikimoriapp.presentation.view.fav.provider.UserRatesAnimeResourceProvider;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DefaultItemCallback;
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;

import java.util.ArrayList;
import java.util.List;

public class AnimeRateAdapter extends ListDelegationAdapter<List<BaseAnimeRateItem>> {

    public AnimeRateAdapter(@NonNull ImageLoader imageLoader,
                            @NonNull UserRatesAnimeResourceProvider resourceProvider,
                            @NonNull DefaultItemCallback callback) {
        delegatesManager.addDelegate(new RateAdapterDelegate(imageLoader, resourceProvider, callback));
        delegatesManager.addDelegate(new AnimeRatePlaceholderAdapterDelegate());
        delegatesManager.addDelegate(new AnimeRateProgressAdapterDelegate());
        delegatesManager.addDelegate(new AnimeRateProgressAdapterDelegate());

        setItems(new ArrayList<>());
    }

    public void bindItems(List<BaseAnimeRateItem> viewModels) {
        items.clear();
        if (viewModels.isEmpty()) {
            items.add(new AnimeRatePlaceholder());
        } else {
            items.addAll(viewModels);
        }
        notifyDataSetChanged();
    }

    public void insertMore(List<BaseAnimeRateItem> viewModels) {
        if (!viewModels.isEmpty() && viewModels.size() - 1 > 0) {
            int prev = getItemCount();
            //Api bug, on pagination previous item returns too
            items.addAll(viewModels.subList(1, viewModels.size() - 1));
            notifyItemChanged(prev, getItemCount());
        }
    }

    public void clearItems() {
        items.clear();
    }

    public void showProgress() {
        items.add(new AnimeRateProgressItem());
    }

    public void hideProgress() {
        items.remove(items.size() - 1);
    }
}
