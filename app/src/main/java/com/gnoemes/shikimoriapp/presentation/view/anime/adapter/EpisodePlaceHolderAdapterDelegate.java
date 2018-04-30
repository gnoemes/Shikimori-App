package com.gnoemes.shikimoriapp.presentation.view.anime.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.BaseEpisodeItem;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodePlaceholderItem;
import com.gnoemes.shikimoriapp.presentation.view.common.widget.EmptyContentView;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EpisodePlaceHolderAdapterDelegate extends AdapterDelegate<List<BaseEpisodeItem>> {


    @Override
    protected boolean isForViewType(@NonNull List<BaseEpisodeItem> items, int position) {
        return items.get(position) instanceof EpisodePlaceholderItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_placeholder_empty, parent, false);
        return new EpisodePlaceHolderAdapterDelegate.ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseEpisodeItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.view_empty)
        EmptyContentView emptyContentView;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            emptyContentView.setText(R.string.episode_nothing);

        }

    }
}
