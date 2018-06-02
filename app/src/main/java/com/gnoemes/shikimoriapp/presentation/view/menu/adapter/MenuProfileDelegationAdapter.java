package com.gnoemes.shikimoriapp.presentation.view.menu.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.menu.domain.MenuCategory;
import com.gnoemes.shikimoriapp.entity.menu.presentration.BaseMenuItem;
import com.gnoemes.shikimoriapp.entity.menu.presentration.MenuProfileViewModel;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MenuProfileDelegationAdapter extends AdapterDelegate<List<BaseMenuItem>> {

    private ImageLoader imageLoader;
    private MenuItemCallback callback;

    MenuProfileDelegationAdapter(@NonNull ImageLoader imageLoader,
                                 @NonNull MenuItemCallback callback) {
        this.imageLoader = imageLoader;
        this.callback = callback;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseMenuItem> items, int position) {
        return items.get(position) instanceof MenuProfileViewModel;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_menu_profile, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseMenuItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        MenuProfileViewModel model = (MenuProfileViewModel) items.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bind(model);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint)
        ConstraintLayout layout;

        @BindView(R.id.image_profile)
        CircleImageView avatar;

        @BindView(R.id.text_name)
        TextView userName;

        @BindView(R.id.text_hint)
        TextView textHint;

        @BindView(R.id.image_arrow)
        ImageView arrow;

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

            DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.ic_chevron_right)
                    .withAttributeColor(R.attr.colorAccent)
                    .tint()
                    .applyTo(arrow);
        }

        public void bind(MenuProfileViewModel model) {
            userName.setText(null);
            avatar.setImageDrawable(null);
            textHint.setText(null);

            switch (model.getStatus()) {
                case GUEST:
                    userName.setText(R.string.common_guest);
                    avatar.setImageResource(R.mipmap.ic_launcher);
                    textHint.setText(R.string.menu_guest_hint);
                    break;
                case AUTHORIZED:
                    userName.setText(model.getUserName());
                    imageLoader.setImageWithPlaceHolder(avatar, model.getAvatarUrl(), R.attr.colorPrimary);
                    textHint.setText(R.string.menu_user_hint);
                    break;
            }

            layout.setOnClickListener(v -> callback.onAction(MenuCategory.PROFILE));
        }
    }
}
