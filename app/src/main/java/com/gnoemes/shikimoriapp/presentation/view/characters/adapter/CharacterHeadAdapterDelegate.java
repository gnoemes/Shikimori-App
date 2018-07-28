package com.gnoemes.shikimoriapp.presentation.view.characters.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.roles.presentation.CharacterHeadItem;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharacterHeadAdapterDelegate extends AdapterDelegate<List<BaseItem>> {

    private ImageLoader imageLoader;

    public CharacterHeadAdapterDelegate(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseItem> items, int position) {
        return items.get(position) instanceof CharacterHeadItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_character_head, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        CharacterHeadItem item = (CharacterHeadItem) items.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bind(item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint)
        ConstraintLayout layout;
        @BindView(R.id.character_image)
        ImageView imageView;
        @BindView(R.id.text_name_rus)
        TextView russianNameView;
        @BindView(R.id.text_name_jp)
        TextView jpNameView;
        @BindView(R.id.text_name_other)
        TextView otherNameView;
        @BindView(R.id.text_description)
        TextView descriptionView;
        @BindView(R.id.title_rus)
        TextView titleRusView;
        @BindView(R.id.title_jp)
        TextView titleJpView;
        @BindView(R.id.title_other)
        TextView titleOtherView;
        @BindView(R.id.divider1)
        View descriptionDividerView;
        @BindView(R.id.title_description)
        TextView titleDescriptionView;

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

        public void bind(CharacterHeadItem item) {

            imageLoader.setImageWithFit(imageView, item.getAnimeImage().getImageOriginalUrl());

            if (TextUtils.isEmpty(item.getRussianName())) {
                russianNameView.setVisibility(View.GONE);
                titleRusView.setVisibility(View.GONE);
            } else {
                russianNameView.setVisibility(View.VISIBLE);
                titleRusView.setVisibility(View.VISIBLE);
                russianNameView.setText(item.getRussianName());
            }

            if (TextUtils.isEmpty(item.getJapaneseName())) {
                jpNameView.setVisibility(View.GONE);
                titleJpView.setVisibility(View.GONE);
            } else {
                jpNameView.setVisibility(View.VISIBLE);
                titleJpView.setVisibility(View.VISIBLE);
                jpNameView.setText(item.getJapaneseName());
            }

            if (TextUtils.isEmpty(item.getAlternativeName())) {
                otherNameView.setVisibility(View.GONE);
                titleOtherView.setVisibility(View.GONE);
            } else {
                otherNameView.setVisibility(View.VISIBLE);
                titleOtherView.setVisibility(View.VISIBLE);
                otherNameView.setText(item.getAlternativeName());
            }

            if (TextUtils.isEmpty(item.getDescription())) {
                descriptionView.setVisibility(View.GONE);
                descriptionDividerView.setVisibility(View.GONE);
                titleDescriptionView.setVisibility(View.GONE);
            } else {
                descriptionView.setText(item.getDescription());
            }

        }
    }
}
