package com.gnoemes.shikimoriapp.presentation.view.anime.adapter;

import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.BaseEpisodeItem;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.EpisodeOptionsItem;
import com.gnoemes.shikimoriapp.utils.view.DefaultItemCallback;
import com.gnoemes.shikimoriapp.utils.view.StickyHeaders;
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;

import java.util.ArrayList;
import java.util.List;

public class EpisodeAdapter extends ListDelegationAdapter<List<BaseEpisodeItem>> implements StickyHeaders {

    public EpisodeAdapter(DefaultItemCallback callback, EpisodeOptionCallback optionCallback) {
        delegatesManager.addDelegate(new EpisodesAdapterDelegate(callback));
        delegatesManager.addDelegate(new EpisodeOptionsAdapterDelegate(optionCallback));

        setItems(new ArrayList<>());
    }

    public void bindItems(List<BaseEpisodeItem> episodeItems) {
        items.clear();
        items.addAll(episodeItems);
        this.notifyDataSetChanged();
    }

    @Override
    public boolean isStickyHeader(int position) {
        return items.get(position) instanceof EpisodeOptionsItem;
    }
}
