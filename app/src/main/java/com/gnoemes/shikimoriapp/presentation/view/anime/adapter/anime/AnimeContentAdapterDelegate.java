package com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.AnimeContentItem;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.BaseAnimeItem;
import com.gnoemes.shikimoriapp.utils.view.AttributesHelper;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.mpt.android.stv.Slice;
import com.mpt.android.stv.SpannableTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimeContentAdapterDelegate extends AdapterDelegate<List<BaseAnimeItem>> {

    @Override
    protected boolean isForViewType(@NonNull List<BaseAnimeItem> items, int position) {
        return items.get(position) instanceof AnimeContentItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_anime_content, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseAnimeItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ViewHolder viewHolder = (ViewHolder) holder;
        AnimeContentItem item = (AnimeContentItem) items.get(position);
        viewHolder.bind(item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint)
        ConstraintLayout layout;

        @BindView(R.id.text_description)
        TextView descriptionView;

        @BindView(R.id.text_desctiption_title)
        SpannableTextView descriptionTitleView;

        private int textColor;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            textColor = AttributesHelper.withContext(itemView.getContext())
                    .getColor(R.attr.colorText);

            Drawable card = DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.card)
                    .withAttributeColor(R.attr.colorBackgroundContent)
                    .tint()
                    .get();

            layout.setBackground(card);

            descriptionTitleView.addSlice(getSliceTitle(itemView.getResources()
                    .getString(R.string.common_description)));

            descriptionTitleView.display();
        }

        public void bind(AnimeContentItem item) {
            descriptionView.setText(item.getDescription());
        }


        /**
         * Returns slice for title (e.g. <b>Genre:</b>)
         *
         * @param text String
         * @return Slice title
         */
        private Slice getSliceTitle(String text) {
            return new Slice.Builder(text.concat(": "))
                    .textColor(textColor)
                    .style(Typeface.BOLD)
                    .build();
        }
    }
}
