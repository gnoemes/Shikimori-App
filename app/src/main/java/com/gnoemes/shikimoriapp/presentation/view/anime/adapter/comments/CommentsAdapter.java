package com.gnoemes.shikimoriapp.presentation.view.anime.adapter.comments;

import com.gnoemes.shikimoriapp.entity.comments.presentation.BaseCommentItem;
import com.gnoemes.shikimoriapp.entity.comments.presentation.CommentPageLoadingItem;
import com.gnoemes.shikimoriapp.entity.comments.presentation.CommentPlaceHolderItem;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends ListDelegationAdapter<List<BaseCommentItem>> {

    public CommentsAdapter(ImageLoader loader) {
        delegatesManager.addDelegate(new CommentsDelegateAdapter(loader));
        delegatesManager.addDelegate(new CommentsPlaceHolderAdapterDelegate());
        delegatesManager.addDelegate(new CommentsLoadingAdapterDelegate());

        setItems(new ArrayList<>());
    }

    public void bindItems(List<BaseCommentItem> itemList) {
        items.clear();
        if (!itemList.isEmpty()) {
            items.addAll(itemList);
            notifyDataSetChanged();
        } else {
            itemList.add(new CommentPlaceHolderItem());
        }
    }

    public void insertMore(List<BaseCommentItem> searchItems) {
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
    }
}
