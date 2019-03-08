package com.gnoemes.shikimoriapp.presentation.view.fav.adapter;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.TopDividerItem;
import com.gnoemes.shikimoriapp.entity.rates.domain.PlaceholderType;
import com.gnoemes.shikimoriapp.entity.rates.presentation.AnimeRatePlaceholder;
import com.gnoemes.shikimoriapp.presentation.view.common.adapter.BaseListAdapter;
import com.gnoemes.shikimoriapp.presentation.view.fav.provider.UserRatesAnimeResourceProvider;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DefaultItemCallback;

import java.util.List;

public class AnimeRateAdapter extends BaseListAdapter {

    public AnimeRateAdapter(@NonNull ImageLoader imageLoader,
                            @NonNull UserRatesAnimeResourceProvider resourceProvider,
                            @NonNull DefaultItemCallback callback,
                            @NonNull DefaultItemCallback rateChangeCallback) {
        super(null);
        delegatesManager.addDelegate(new RateAdapterDelegate(imageLoader, resourceProvider, callback, rateChangeCallback));
        delegatesManager.addDelegate(new AnimeRatePlaceholderAdapterDelegate());
    }

    @Override
    public void bindItems(List<BaseItem> viewModels) {
        items.clear();
        if (viewModels == null || viewModels.isEmpty()) {
            items.add(new AnimeRatePlaceholder(PlaceholderType.EMPTY));
        } else {
            items.addAll(viewModels);
            items.add(0, new TopDividerItem());
        }
        notifyDataSetChanged();
    }

}
