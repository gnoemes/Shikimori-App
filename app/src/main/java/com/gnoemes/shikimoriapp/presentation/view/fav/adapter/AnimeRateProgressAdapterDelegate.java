package com.gnoemes.shikimoriapp.presentation.view.fav.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.rates.presentation.AnimeRateProgressItem;
import com.gnoemes.shikimoriapp.entity.rates.presentation.BaseAnimeRateItem;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimeRateProgressAdapterDelegate extends AdapterDelegate<List<BaseAnimeRateItem>> {

    @Override
    protected boolean isForViewType(@NonNull List<BaseAnimeRateItem> items, int position) {
        return items.get(position) instanceof AnimeRateProgressItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_progress, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseAnimeRateItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.container)
        LinearLayout layout;

        @BindView(R.id.progress_bar)
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            layout.setGravity(Gravity.CENTER);
        }
    }
}
