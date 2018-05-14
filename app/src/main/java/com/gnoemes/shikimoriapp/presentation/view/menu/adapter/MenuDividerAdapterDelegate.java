package com.gnoemes.shikimoriapp.presentation.view.menu.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.menu.presentration.BaseMenuItem;
import com.gnoemes.shikimoriapp.entity.menu.presentration.MenuDividerViewModel;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

public class MenuDividerAdapterDelegate extends AdapterDelegate<List<BaseMenuItem>> {

    @Override
    protected boolean isForViewType(@NonNull List<BaseMenuItem> items, int position) {
        return items.get(position) instanceof MenuDividerViewModel;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.layout_shadow_double, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseMenuItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {

    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
