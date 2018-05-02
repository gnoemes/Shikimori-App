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
import com.gnoemes.shikimoriapp.entity.menu.presentration.MenuCategoryWithBadgeViewModel;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuCategoryWithBadgeAdapterDelegate extends AdapterDelegate<List<BaseMenuItem>> {

    private MenuItemCallback callback;

    MenuCategoryWithBadgeAdapterDelegate(@NonNull MenuItemCallback callback) {
        this.callback = callback;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseMenuItem> items, int position) {
        return items.get(position) instanceof MenuCategoryWithBadgeViewModel;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_menu_with_badge, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseMenuItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        MenuCategoryWithBadgeViewModel model = (MenuCategoryWithBadgeViewModel) items.get(position);
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

        @BindView(R.id.badge)
        TextView badge;

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

        public void bind(MenuCategoryWithBadgeViewModel model) {
            categoryText.setText(null);
            icon.setImageDrawable(null);
            badge.setText(null);
            badge.setVisibility(View.GONE);

            switch (model.getCategory()) {
                case NEWS:
                    applyIcon(R.drawable.ic_newspaper);
                    categoryText.setText(R.string.common_news);
                    break;
                case NOTIFICATIONS:
                    applyIcon(R.drawable.ic_notifications);
                    categoryText.setText(R.string.common_notifications);
                    break;
                case MESSAGES:
                    applyIcon(R.drawable.ic_local_post_office);
                    categoryText.setText(R.string.common_messages);
                    break;
                case FRIENDS:
                    applyIcon(R.drawable.ic_account_group);
                    categoryText.setText(R.string.common_friends);
                    break;
            }

            if (model.isNeedBadge()) {
                badge.setVisibility(View.VISIBLE);
                badge.setText(String.valueOf(model.getBadgeValue()));
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
