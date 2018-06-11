package com.gnoemes.shikimoriapp.presentation.view.related.adapter;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.related.presentation.RelatedPlaceholderItem;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.AnimeResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.MangaResourceProvider;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;

import java.util.ArrayList;
import java.util.List;

public class RelatedAdapter extends ListDelegationAdapter<List<BaseItem>> {

    public RelatedAdapter(ImageLoader imageLoader,
                          AnimeResourceProvider animeResourceProvider,
                          MangaResourceProvider mangaResourceProvider,
                          RelatedItemCallback callback) {
        delegatesManager.addDelegate(new RelatedAnimeAdapterDelegate(imageLoader, animeResourceProvider, callback));
        delegatesManager.addDelegate(new RelatedMangaAdapterDelegate(imageLoader, mangaResourceProvider, callback));
        delegatesManager.addDelegate(new RelatedPlaceholderAdapterDelegate());
        delegatesManager.addDelegate(new RelatedDividerAdapterDelegate());

        setItems(new ArrayList<>());
    }

    public void bindItems(List<BaseItem> relatedItems) {
        items.clear();
        if (relatedItems != null && !relatedItems.isEmpty()) {
            items.addAll(relatedItems);
        } else {
            items.add(new RelatedPlaceholderItem());
        }
        notifyDataSetChanged();
    }
}
