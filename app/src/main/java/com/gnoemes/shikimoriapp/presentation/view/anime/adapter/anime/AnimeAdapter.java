package com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.presentation.view.anime.provider.RateResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.common.adapter.DoubleDividerAdapterDelegate;
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;

import java.util.ArrayList;
import java.util.List;

public class AnimeAdapter extends ListDelegationAdapter<List<BaseItem>> {

    public AnimeAdapter(RateResourceProvider rateResourceProvider,
                        DetailsCharacterAdapter characterAdapter,
                        @NonNull AnimeItemCallback callback) {
        delegatesManager.addDelegate(new AnimeHeadAdapterDelegate(rateResourceProvider, callback));
        delegatesManager.addDelegate(new DetailsContentAdapterDelegate());
        delegatesManager.addDelegate(new DoubleDividerAdapterDelegate());
        delegatesManager.addDelegate(new AnimeOtherAdapterDelegate(callback));
        delegatesManager.addDelegate(new DetailsActionAdapterDelegate(callback));
        delegatesManager.addDelegate(new DetailsCharacterAdapterDelegate(characterAdapter));

        setItems(new ArrayList<>());
    }

    public void bindItems(List<BaseItem> animeItems) {
        items.clear();
        items.addAll(animeItems);
        this.notifyDataSetChanged();
    }

}
