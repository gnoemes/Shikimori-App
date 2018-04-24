package com.gnoemes.shikimoriapp.presentation.view.search.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.search.presentation.BaseSearchItem;
import com.gnoemes.shikimoriapp.entity.search.presentation.ProgressItemViewModel;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgressItemAdapterDelegate extends AdapterDelegate<List<BaseSearchItem>> {

    @Override
    protected boolean isForViewType(@NonNull List<BaseSearchItem> items, int position) {
        return items.get(position) instanceof ProgressItemViewModel;
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
    protected void onBindViewHolder(@NonNull List<BaseSearchItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.progress_bar)
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
