package com.gnoemes.shikimoriapp.presentation.view.characters.adapter;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;

import java.util.ArrayList;
import java.util.List;

public class CharacterDetailsAdapter extends ListDelegationAdapter<List<BaseItem>> {

    public CharacterDetailsAdapter(ImageLoader imageLoader,
                                   CharacterItemCallback callback) {
        delegatesManager.addDelegate(new CharacterHeadAdapterDelegate(imageLoader));
        delegatesManager.addDelegate(new CharacterRelatedAdapterDelegate(imageLoader, callback));
        delegatesManager.addDelegate(new CharacterDividerAdapterDelegate());

        setItems(new ArrayList<>());
    }


    public void bindItems(List<BaseItem> characterItems) {
        items.clear();
        items.addAll(characterItems);
        notifyDataSetChanged();
    }
}
