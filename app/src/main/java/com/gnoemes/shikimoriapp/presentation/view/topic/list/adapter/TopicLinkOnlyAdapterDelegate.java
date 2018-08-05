package com.gnoemes.shikimoriapp.presentation.view.topic.list.adapter;

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
import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeStatus;
import com.gnoemes.shikimoriapp.entity.app.domain.LinkedContent;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.topic.presentation.TopicLinkOnlyItem;
import com.gnoemes.shikimoriapp.presentation.view.topic.provider.TopicResourceProvider;
import com.gnoemes.shikimoriapp.utils.date.converter.DateTimeConverter;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.AttributesHelper;
import com.gnoemes.shikimoriapp.utils.view.DefaultItemCallback;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.gnoemes.shikimoriapp.utils.view.LinkedItemCallback;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.mpt.android.stv.Slice;
import com.mpt.android.stv.SpannableTextView;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopicLinkOnlyAdapterDelegate extends AdapterDelegate<List<BaseItem>> {
    private ImageLoader imageLoader;
    private DefaultItemCallback callback;
    private LinkedItemCallback linkedCallback;
    private DateTimeConverter dateTimeConverter;
    private TopicResourceProvider resourceProvider;

    public TopicLinkOnlyAdapterDelegate(ImageLoader imageLoader,
                                        DefaultItemCallback callback,
                                        LinkedItemCallback linkedCallback,
                                        DateTimeConverter dateTimeConverter,
                                        TopicResourceProvider resourceProvider) {
        this.imageLoader = imageLoader;
        this.callback = callback;
        this.linkedCallback = linkedCallback;
        this.dateTimeConverter = dateTimeConverter;
        this.resourceProvider = resourceProvider;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseItem> items, int position) {
        return items.get(position) instanceof TopicLinkOnlyItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_topic_link_only, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ViewHolder viewHolder = (ViewHolder) holder;
        TopicLinkOnlyItem topicItem = (TopicLinkOnlyItem) items.get(position);
        viewHolder.bind(topicItem);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint)
        ConstraintLayout layout;
        @BindView(R.id.image_linked)
        ImageView linkedImageView;
        @BindView(R.id.tag_label)
        TextView labelTag;
        @BindView(R.id.text_date)
        TextView dateView;
        @BindView(R.id.text_title)
        TextView titleView;
        @BindView(R.id.text_details)
        TextView detailsView;
        @BindView(R.id.count_comments)
        TextView commentsCountView;
        @BindView(R.id.image_comments)
        ImageView commentImageView;
        @BindView(R.id.text_season)
        SpannableTextView seasonView;
        @BindView(R.id.text_type)
        SpannableTextView typeView;
        @BindView(R.id.text_status)
        SpannableTextView statusView;

        private int textColor;
        private int textSize;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            textColor = AttributesHelper.withContext(itemView.getContext())
                    .getColor(R.attr.colorText);
            textSize = (int) itemView.getContext().getResources().getDimension(R.dimen.text_small);

            Drawable chevron = DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.ic_chevron_right)
                    .withAttributeColor(R.attr.colorAccent)
                    .tint()
                    .get();

            Drawable card = DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.card)
                    .withAttributeColor(R.attr.colorBackgroundContent)
                    .tint()
                    .get();

            detailsView.setCompoundDrawablesWithIntrinsicBounds(null, null, chevron, null);
            layout.setBackground(card);
        }

        public void bind(TopicLinkOnlyItem item) {
            layout.setOnClickListener(null);
            linkedImageView.setOnClickListener(null);

            seasonView.reset();
            typeView.reset();
            statusView.reset();

            seasonView.addSlice(getSliceTitle(itemView.getResources().getString(R.string.common_season)));
            typeView.addSlice(getSliceTitle(itemView.getResources().getString(R.string.common_type)));
            statusView.addSlice(getSliceTitle(itemView.getResources().getString(R.string.common_status)));

            Anime anime = item.getLinkedContent() instanceof Anime ? (Anime) item.getLinkedContent() : null;
            if (anime != null) {
                seasonView.addSlice(getSliceContent(convertSeason(anime.getAiredDate())));
                seasonView.display();

                typeView.addSlice(getSliceContent(anime.getType().toString().toUpperCase()));
                typeView.display();

                statusView.addSlice(getSliceContent(convertStatus(anime.getStatus())));
                statusView.display();
            }

            bindLinkedContent(item.getLinkedContent());
            titleView.setText(item.getTitle());
            commentsCountView.setText(String.valueOf(item.getCommentsCount()));

            dateView.setText(dateTimeConverter.convertDateAgoToString(item.getCreatedDate()));

            layout.setOnClickListener(v -> callback.onItemClick(item.getId()));
        }

        private void bindLinkedContent(LinkedContent content) {
            if (content != null) {
                linkedImageView.setVisibility(View.VISIBLE);
                imageLoader.setImageWithFit(linkedImageView, content.getImageUrl());
            } else {
                linkedImageView.setVisibility(View.GONE);
            }

            linkedImageView.setOnClickListener(v -> {
                if (content != null) {
                    linkedCallback.onLinkedContentClicked(content.getId(), content.getLinkedType());
                }
            });
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
    }
}
