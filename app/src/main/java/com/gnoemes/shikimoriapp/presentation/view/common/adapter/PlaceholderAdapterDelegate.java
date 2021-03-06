package com.gnoemes.shikimoriapp.presentation.view.common.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.PlaceHolderItem;
import com.gnoemes.shikimoriapp.presentation.view.common.widget.EmptyContentView;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceholderAdapterDelegate extends AdapterDelegate<List<BaseItem>> {
    @Override
    protected boolean isForViewType(@NonNull List<BaseItem> items, int position) {
        return items.get(position) instanceof PlaceHolderItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_placeholder_empty, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        PlaceHolderItem item = (PlaceHolderItem) items.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bind(item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.view_empty)
        EmptyContentView emptyContentView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(PlaceHolderItem item) {
            emptyContentView.setText(item.getMessage());
        }
    }
}
