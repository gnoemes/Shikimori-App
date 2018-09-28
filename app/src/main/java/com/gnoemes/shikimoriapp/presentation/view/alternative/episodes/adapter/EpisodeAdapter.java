package com.gnoemes.shikimoriapp.presentation.view.alternative.episodes.adapter;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodeOptionsItem;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodePlaceholderItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.presentation.view.common.adapter.BaseListAdapter;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.AdapterResourceProvider;
import com.gnoemes.shikimoriapp.utils.view.StickyHeaders;

import java.util.Collections;
import java.util.List;

public class EpisodeAdapter extends BaseListAdapter implements StickyHeaders {

    public EpisodeAdapter(@Nullable AdapterResourceProvider resourceProvider,
                          EpisodePickCallback callback,
                          EpisodeOptionCallback optionCallback) {
        super(resourceProvider);
        delegatesManager.addDelegate(new EpisodeAlternativeIAdapterDelegate(callback));
        delegatesManager.addDelegate(new EpisodeAlternativeOptionAdapterDelegate(optionCallback));
    }

    public void bindItems(Boolean isEpisodeReversed, List<BaseItem> episodeItems) {
        items.clear();

        if (episodeItems.isEmpty()) {
            items.add(new EpisodePlaceholderItem());
        } else {
            items.addAll(episodeItems);
            if (isEpisodeReversed) {
                reverseItems();
            }
        }
        this.notifyDataSetChanged();
    }

    public void reverseItems() {
        if (!items.isEmpty()) {
            items.add(items.get(0));
            Collections.reverse(items);
            items.remove(items.size() - 1);
            this.notifyDataSetChanged();
        }
    }

    @Override
    public boolean isStickyHeader(int position) {
        return items.get(position) instanceof EpisodeOptionsItem;
    }
}
