package com.gnoemes.shikimoriapp.presentation.view.topic.list.adapter;

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
import com.gnoemes.shikimoriapp.entity.app.domain.LinkedContent;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.topic.presentation.TopicWithDescriptionItem;
import com.gnoemes.shikimoriapp.presentation.view.topic.provider.TopicResourceProvider;
import com.gnoemes.shikimoriapp.utils.date.converter.DateTimeConverter;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DefaultItemCallback;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.gnoemes.shikimoriapp.utils.view.LinkedItemCallback;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopicWithDescriptionAdapterDelegate extends AdapterDelegate<List<BaseItem>> {

    private ImageLoader imageLoader;
    private DefaultItemCallback callback;
    private LinkedItemCallback linkedCallback;
    private DateTimeConverter dateTimeConverter;
    private TopicResourceProvider resourceProvider;

    public TopicWithDescriptionAdapterDelegate(ImageLoader imageLoader,
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
        return items.get(position) instanceof TopicWithDescriptionItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_topic_with_description, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ViewHolder viewHolder = (ViewHolder) holder;
        TopicWithDescriptionItem topicItem = (TopicWithDescriptionItem) items.get(position);
        viewHolder.bind(topicItem);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint)
        ConstraintLayout layout;
        @BindView(R.id.image_linked)
        ImageView linkedImageView;
        @BindView(R.id.text_description)
        TextView descriptionView;
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

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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

        public void bind(TopicWithDescriptionItem topicItem) {
            layout.setOnClickListener(null);
            linkedImageView.setOnClickListener(null);

            bindLinkedContent(topicItem.getLinkedContent());
            titleView.setText(topicItem.getTitle());
            descriptionView.setText(topicItem.getDescription());
            commentsCountView.setText(String.valueOf(topicItem.getCommentsCount()));

            dateView.setText(dateTimeConverter.convertDateAgoToString(topicItem.getCreatedDate()));

            layout.setOnClickListener(v -> callback.onItemClick(topicItem.getId()));
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
    }
}
