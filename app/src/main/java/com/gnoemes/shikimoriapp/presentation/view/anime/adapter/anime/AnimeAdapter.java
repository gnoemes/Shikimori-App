package com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.BaseAnimeItem;
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;

import java.util.ArrayList;
import java.util.List;

public class AnimeAdapter extends ListDelegationAdapter<List<BaseAnimeItem>> {

    public AnimeAdapter(@NonNull AnimeItemCallback callback) {
        delegatesManager.addDelegate(new AnimeHeadAdapterDelegate(callback));
        delegatesManager.addDelegate(new AnimeContentAdapterDelegate());
        delegatesManager.addDelegate(new AnimeOtherAdapterDelegate(callback));

        setItems(new ArrayList<>());
    }

    public void bindItems(List<BaseAnimeItem> animeItems) {
        items.clear();
        items.addAll(animeItems);
        this.notifyDataSetChanged();
    }

}
