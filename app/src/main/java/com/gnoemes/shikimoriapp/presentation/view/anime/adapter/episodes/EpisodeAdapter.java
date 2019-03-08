package com.gnoemes.shikimoriapp.presentation.view.anime.adapter.episodes;

import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.BaseEpisodeItem;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.EpisodeOptionsItem;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodePlaceholderItem;
import com.gnoemes.shikimoriapp.utils.view.StickyHeaders;
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EpisodeAdapter extends ListDelegationAdapter<List<BaseEpisodeItem>> implements StickyHeaders {

    public EpisodeAdapter(EpisodePickCallback callback, EpisodeOptionCallback optionCallback) {
        delegatesManager.addDelegate(new EpisodesAdapterDelegate(callback));
        delegatesManager.addDelegate(new EpisodeOptionsAdapterDelegate(optionCallback));
        delegatesManager.addDelegate(new EpisodePlaceHolderAdapterDelegate(optionCallback));
        delegatesManager.addDelegate(new EpisodeErrorAdapterDelegate(optionCallback));

        setItems(new ArrayList<>());
    }

    public void bindItems(Boolean isEpisodeReversed, List<BaseEpisodeItem> episodeItems) {
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
