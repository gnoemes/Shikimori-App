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
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.menu.presentration.MenuCategoryViewModel;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuCategoryAdapterDelegate extends AdapterDelegate<List<BaseItem>> {

    private MenuItemCallback callback;

    MenuCategoryAdapterDelegate(@NonNull MenuItemCallback callback) {
        this.callback = callback;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseItem> items, int position) {
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
    protected void onBindViewHolder(@NonNull List<BaseItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
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
                case HISTORY:
                    applyIcon(R.drawable.ic_history);
                    categoryText.setText(R.string.common_history);
                    break;
                case SETTINGS:
                    applyIcon(R.drawable.ic_settings);
                    categoryText.setText(R.string.common_settings);
                    break;
                case FOUR_PDA:
                    resizeAndApplyIcon(R.drawable.four_pda);
                    categoryText.setText(R.string.four_pda);
                    break;
                case SHIKIMORI_CLUB:
                    resizeAndApplyIcon(R.drawable.shikimori);
                    categoryText.setText(R.string.app_on_shikimori);
                    break;
                case SEND_MAIL_TO_DEV:
                    resizeAndApplyIcon(R.drawable.gmail);
                    categoryText.setText(R.string.send_mail_to_dev);
                    break;
                case SUPPORT:
                    applyIcon(R.drawable.ic_heart);
                    categoryText.setText(R.string.support);
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

        private void resizeAndApplyIcon(@DrawableRes int drawableRes) {
            DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(drawableRes)
                    .resize(24)
                    .applyTo(icon);
        }
    }
}
