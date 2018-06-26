package com.gnoemes.shikimoriapp.presentation.view.fav.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.rates.presentation.AnimeRatePlaceholder;
import com.gnoemes.shikimoriapp.entity.rates.presentation.BaseAnimeRateItem;
import com.gnoemes.shikimoriapp.presentation.view.common.widget.EmptyContentView;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimeRatePlaceholderAdapterDelegate extends AdapterDelegate<List<BaseAnimeRateItem>> {
    @Override
    protected boolean isForViewType(@NonNull List<BaseAnimeRateItem> items, int position) {
        return items.get(position) instanceof AnimeRatePlaceholder;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_placeholder_empty, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseAnimeRateItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        AnimeRatePlaceholder item = (AnimeRatePlaceholder) items.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bind(item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.view_empty)
        EmptyContentView emptyContentView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);


        }

        public void bind(AnimeRatePlaceholder item) {
            switch (item.getType()) {
                case EMPTY:
                    emptyContentView.setText(R.string.rate_empty);
                    break;
                case ERROR:
                    emptyContentView.setText(R.string.rate_forbidden);
                    break;
            }
        }
    }
}
