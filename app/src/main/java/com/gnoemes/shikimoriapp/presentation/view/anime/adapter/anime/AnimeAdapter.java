package com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;

import java.util.ArrayList;
import java.util.List;

public class AnimeAdapter extends ListDelegationAdapter<List<BaseItem>> {

    public AnimeAdapter(@NonNull AnimeItemCallback callback) {
        delegatesManager.addDelegate(new AnimeHeadAdapterDelegate(callback));
        delegatesManager.addDelegate(new AnimeContentAdapterDelegate());
        delegatesManager.addDelegate(new AnimeDividerAdapterDelegate());
        delegatesManager.addDelegate(new AnimeOtherAdapterDelegate(callback));
        delegatesManager.addDelegate(new AnimeActionAdapterDelegate(callback));

        setItems(new ArrayList<>());
    }

    public void bindItems(List<BaseItem> animeItems) {
        items.clear();
        items.addAll(animeItems);
        this.notifyDataSetChanged();
    }

}
