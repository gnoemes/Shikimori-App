package com.gnoemes.shikimoriapp.presentation.view.topic.list.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.domain.LinkedContent;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.topic.presentation.TopicItem;
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

public class TopicAdapterDelegate extends AdapterDelegate<List<BaseItem>> {

    private ImageLoader imageLoader;
    private TopicListAdapter.TopicItemCallback callback;
    private LinkedItemCallback linkedCallback;
    private DateTimeConverter dateTimeConverter;
    private TopicResourceProvider resourceProvider;
    private DefaultItemCallback userProfileCallback;

    public TopicAdapterDelegate(ImageLoader imageLoader,
                                TopicListAdapter.TopicItemCallback callback,
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
        return items.get(position) instanceof TopicItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_topic, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ViewHolder viewHolder = (ViewHolder) holder;
        TopicItem topicItem = (TopicItem) items.get(position);
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
        @BindView(R.id.image_avatar)
        CircleImageView avatarView;

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

        public void bind(TopicItem topicItem) {
            layout.setOnClickListener(null);
            linkedImageView.setOnClickListener(null);
            avatarView.setOnClickListener(null);

            bindLinkedContent(topicItem);
            titleView.setText(topicItem.getTitle());
            commentsCountView.setText(String.valueOf(topicItem.getCommentsCount()));

            switch (topicItem.getType()) {
                case COLLECTION:
                case REVIEW:
                case COSPLAY:
                case NEWS:
                    labelTag.setText(resourceProvider.getLabelTitle(topicItem.getType()).toLowerCase());
                    labelTag.setBackgroundColor(
                            ResourcesCompat.getColor(itemView.getResources(),
                                    resourceProvider.getLabelColor(topicItem.getType()),
                                    null)
                    );
                    labelTag.setVisibility(View.VISIBLE);
                    break;
                default:
                    labelTag.setVisibility(View.GONE);
            }

            dateView.setText(dateTimeConverter.convertDateAgoToString(topicItem.getCreatedDate()));

            layout.setOnClickListener(v -> callback.onItemClicked(topicItem.getForum(), topicItem.getId()));
        }

        private void bindLinkedContent(TopicItem item) {
            LinkedContent content = item.getLinkedContent();
            int margin = (int) itemView.getResources().getDimension(R.dimen.margin_normal);
            if (content != null) {
                linkedImageView.setVisibility(View.VISIBLE);
                avatarView.setVisibility(View.GONE);
                ConstraintSet set = new ConstraintSet();
                set.clone(layout);
                set.connect(R.id.text_title, ConstraintSet.START, R.id.image_linked, ConstraintSet.END, margin);
                set.applyTo(layout);
                imageLoader.setImageWithFit(linkedImageView, content.getImageUrl());
            } else {
                linkedImageView.setVisibility(View.GONE);
                avatarView.setVisibility(View.VISIBLE);
                ConstraintSet set = new ConstraintSet();
                set.clone(layout);
                set.connect(R.id.text_title, ConstraintSet.START, R.id.image_avatar, ConstraintSet.END, margin);
                set.applyTo(layout);
                imageLoader.setImageWithPlaceHolder(avatarView, item.getUserBrief().getAvatarUrl(), 0);
            }

            linkedImageView.setOnClickListener(v -> {
                if (content != null) {
                    linkedCallback.onLinkedContentClicked(content.getLinkedId(), content.getLinkedType());
                }
            });

            avatarView.setOnClickListener(v -> userProfileCallback.onItemClick(item.getUserBrief().getId()));
        }
    }
}
