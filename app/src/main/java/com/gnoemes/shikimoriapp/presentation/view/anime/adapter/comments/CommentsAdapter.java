package com.gnoemes.shikimoriapp.presentation.view.anime.adapter.comments;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.comments.presentation.CommentPageLoadingItem;
import com.gnoemes.shikimoriapp.entity.comments.presentation.CommentPlaceHolderItem;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DefaultItemCallback;
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends ListDelegationAdapter<List<BaseItem>> {

    public CommentsAdapter(ImageLoader loader, DefaultItemCallback callback) {
        delegatesManager.addDelegate(new CommentsDelegateAdapter(loader, callback));
        delegatesManager.addDelegate(new CommentsDividerDelegateAdapter());
        delegatesManager.addDelegate(new CommentsPlaceHolderAdapterDelegate());
        delegatesManager.addDelegate(new CommentsLoadingAdapterDelegate());

        setItems(new ArrayList<>());
    }

    public void bindItems(List<BaseItem> itemList) {
        items.clear();
        if (!itemList.isEmpty()) {
            items.addAll(itemList);
        } else {
            items.add(new CommentPlaceHolderItem());
        }
        notifyDataSetChanged();
    }

    public void insertMore(List<BaseItem> searchItems) {
        int prev = getItemCount();
        items.addAll(searchItems);
        notifyItemChanged(prev, getItemCount());
    }

    public void clearItems() {
        items.clear();
    }

    public void showProgress() {
        items.add(new CommentPageLoadingItem());
    }

    public void hideProgress() {
        items.remove(items.size() - 1);
        notifyItemChanged(items.size());
    }
}
