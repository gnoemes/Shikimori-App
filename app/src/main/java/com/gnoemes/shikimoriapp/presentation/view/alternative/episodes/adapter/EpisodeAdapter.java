package com.gnoemes.shikimoriapp.presentation.view.alternative.episodes.adapter;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodeOptionsItem;
import com.gnoemes.shikimoriapp.presentation.view.common.adapter.BaseListAdapter;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.AdapterResourceProvider;
import com.gnoemes.shikimoriapp.utils.view.StickyHeaders;

public class EpisodeAdapter extends BaseListAdapter implements StickyHeaders {

    public EpisodeAdapter(@Nullable AdapterResourceProvider resourceProvider,
                          EpisodePickCallback callback,
                          EpisodeOptionCallback optionCallback) {
        super(resourceProvider);
        delegatesManager.addDelegate(new EpisodeAlternativeIAdapterDelegate(callback));
        delegatesManager.addDelegate(new EpisodeAlternativeOptionAdapterDelegate(optionCallback));

    }

    @Override
    public boolean isStickyHeader(int position) {
        return items.get(position) instanceof EpisodeOptionsItem;
    }
}
