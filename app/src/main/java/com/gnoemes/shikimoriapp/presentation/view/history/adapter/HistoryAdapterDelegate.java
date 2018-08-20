package com.gnoemes.shikimoriapp.presentation.view.history.adapter;

import android.graphics.Typeface;
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
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeStatus;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeType;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.history.presentation.HistoryItem;
import com.gnoemes.shikimoriapp.utils.date.converter.DateTimeConverter;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.AttributesHelper;
import com.gnoemes.shikimoriapp.utils.view.DefaultItemCallback;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.mpt.android.stv.Slice;
import com.mpt.android.stv.SpannableTextView;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryAdapterDelegate extends AdapterDelegate<List<BaseItem>> {

    private ImageLoader imageLoader;
    private DateTimeConverter dateTimeConverter;
    private DefaultItemCallback callback;

    public HistoryAdapterDelegate(ImageLoader imageLoader,
                                  DateTimeConverter dateTimeConverter,
                                  DefaultItemCallback callback) {
        this.imageLoader = imageLoader;
        this.dateTimeConverter = dateTimeConverter;
        this.callback = callback;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseItem> items, int position) {
        return items.get(position) instanceof HistoryItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ViewHolder viewHolder = (ViewHolder) holder;
        HistoryItem item = (HistoryItem) items.get(position);
        viewHolder.bind(item);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint)
        ConstraintLayout layout;
        @BindView(R.id.image_anime)
        ImageView animeImageView;
        @BindView(R.id.anime_title)
        TextView animeTitleView;
        @BindView(R.id.text_season)
        SpannableTextView seasonView;
        @BindView(R.id.text_anime_type)
        SpannableTextView typeView;
        @BindView(R.id.text_status)
        SpannableTextView statusView;

        private int textColor;
        private int textSize;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            textColor = AttributesHelper.withContext(itemView.getContext())
                    .getColor(R.attr.colorText);
            textSize = (int) itemView.getContext().getResources().getDimension(R.dimen.text_default);

            Drawable card = DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.card)
                    .withAttributeColor(R.attr.colorBackgroundContent)
                    .tint()
                    .get();

            layout.setBackground(card);
        }

        public void bind(HistoryItem item) {
            layout.setOnClickListener(null);

            imageLoader.setImageWithFit(animeImageView, item.getImageOriginalUrl());

            seasonView.reset();
            typeView.reset();
            statusView.reset();

            seasonView.addSlice(getSliceTitle(itemView.getResources().getString(R.string.common_season)));
            typeView.addSlice(getSliceTitle(itemView.getResources().getString(R.string.common_type)));
            statusView.addSlice(getSliceTitle(itemView.getResources().getString(R.string.common_status)));

            seasonView.addSlice(getSliceContent(convertSeason(item.getAiredDate())));
            seasonView.display();

            typeView.addSlice(getSliceContent(convertType(item.getType(), item.getEpisodes())));
            typeView.display();

            statusView.addSlice(getSliceContent(convertStatus(item.getStatus())));
            statusView.display();

            //TODO locale/settings
            animeTitleView.setText(item.getName());

            layout.setOnClickListener(v -> callback.onItemClick(item.getId()));
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
                    .textSize(textSize)
                    .style(Typeface.BOLD)
                    .build();
        }

        /**
         * Returns slice for content (base text)
         *
         * @param text String
         * @return Slice content
         */
        private Slice getSliceContent(String text) {
            return new Slice.Builder(text)
                    .textColor(textColor)
                    .textSize(textSize)
                    .build();
        }

        private String convertStatus(AnimeStatus status) {
            if (status == null) {
                return itemView.getContext().getString(R.string.error_no_data);
            }
            switch (status) {
                case ANONS:
                    return itemView.getContext().getString(R.string.status_anons);
                case ONGOING:
                    return itemView.getContext().getString(R.string.status_ongoing);
                case RELEASED:
                    return itemView.getContext().getString(R.string.status_released);
                default:
                    return itemView.getContext().getString(R.string.error_no_data);
            }
        }

        private String convertSeason(DateTime airedDate) {
            return dateTimeConverter.convertAnimeSeasonToString(airedDate);
        }

        private String convertType(AnimeType type, int episodes) {
            return String.format(itemView.getContext().getString(R.string.type_pattern_simple), type.toString().toUpperCase(),
                    episodes == 0 ? "xxx" : String.valueOf(episodes));
        }
    }
}
