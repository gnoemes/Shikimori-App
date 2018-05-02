package com.gnoemes.shikimoriapp.presentation.view.menu.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.menu.presentration.BaseMenuItem;
import com.gnoemes.shikimoriapp.entity.menu.presentration.MenuCategoryViewModel;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuCategoryAdapterDelegate extends AdapterDelegate<List<BaseMenuItem>> {

    private MenuItemCallback callback;

    MenuCategoryAdapterDelegate(@NonNull MenuItemCallback callback) {
        this.callback = callback;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseMenuItem> items, int position) {
        return items.get(position) instanceof MenuCategoryViewModel;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_menu_category, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseMenuItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        MenuCategoryViewModel model = (MenuCategoryViewModel) items.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bind(model);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint)
        ConstraintLayout layout;

        @BindView(R.id.icon)
        ImageView icon;

        @BindView(R.id.text_category)
        TextView categoryText;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            Drawable card = DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.card)
                    .withAttributeColor(R.attr.colorBackgroundContent)
                    .tint()
                    .get();

            layout.setBackground(card);
        }

        public void bind(MenuCategoryViewModel model) {
            categoryText.setText(null);
            icon.setImageDrawable(null);

            switch (model.getCategory()) {
                case SETTINGS:
                    applyIcon(R.drawable.ic_settings);
                    categoryText.setText(R.string.common_settings);
                    break;
            }

            layout.setOnClickListener(v -> callback.onAction(model.getCategory()));
        }

        private void applyIcon(@DrawableRes int drawableRes) {
            DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(drawableRes)
                    .withAttributeColor(R.attr.colorText)
                    .tint()
                    .applyTo(icon);
        }
    }
}
