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
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.club.domain.Club;
import com.gnoemes.shikimoriapp.entity.topic.presentation.TopicClubItem;
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
import de.hdodenhof.circleimageview.CircleImageView;

public class TopicClubAdapterDelegate extends AdapterDelegate<List<BaseItem>> {

    private ImageLoader imageLoader;
    private DefaultItemCallback callback;
    private LinkedItemCallback linkedCallback;
    private DefaultItemCallback userProfileCallback;
    private DateTimeConverter dateTimeConverter;
    private TopicResourceProvider resourceProvider;

    public TopicClubAdapterDelegate(ImageLoader imageLoader,
                                    DefaultItemCallback callback,
                                    LinkedItemCallback linkedCallback,
                                    DefaultItemCallback userProfileCallback,
                                    DateTimeConverter dateTimeConverter,
                                    TopicResourceProvider resourceProvider) {
        this.imageLoader = imageLoader;
        this.callback = callback;
        this.linkedCallback = linkedCallback;
        this.userProfileCallback = userProfileCallback;
        this.dateTimeConverter = dateTimeConverter;
        this.resourceProvider = resourceProvider;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseItem> items, int position) {
        return items.get(position) instanceof TopicClubItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_topic_club, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ViewHolder viewHolder = (ViewHolder) holder;
        TopicClubItem topicItem = (TopicClubItem) items.get(position);
        viewHolder.bind(topicItem);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint)
        ConstraintLayout layout;
        @BindView(R.id.image_avatar)
        CircleImageView linkedImageView;
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

        public void bind(TopicClubItem topicItem) {
            layout.setOnClickListener(null);
            linkedImageView.setOnClickListener(null);

            if (topicItem.getLinkedContent() != null) {
                titleView.setText(((Club) topicItem.getLinkedContent()).getName());
            } else {
                titleView.setText(null);
            }

            descriptionView.setText(topicItem.getTitle());
            commentsCountView.setText(String.valueOf(topicItem.getCommentsCount()));

            linkedImageView.setVisibility(View.VISIBLE);
            imageLoader.setImageWithPlaceHolder(linkedImageView, topicItem.getUserBrief().getAvatarUrl(), 0);

            dateView.setText(dateTimeConverter.convertDateAgoToString(topicItem.getCreatedDate()));

            layout.setOnClickListener(v -> callback.onItemClick(topicItem.getId()));

            linkedImageView.setOnClickListener(v -> userProfileCallback.onItemClick(topicItem.getUserBrief().getId()));
        }
    }
}
