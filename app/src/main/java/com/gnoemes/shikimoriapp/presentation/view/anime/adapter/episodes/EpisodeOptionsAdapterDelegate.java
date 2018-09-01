package com.gnoemes.shikimoriapp.presentation.view.anime.adapter.episodes;

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
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.BaseEpisodeItem;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.EpisodeOptionsItem;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodeOptionAction;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EpisodeOptionsAdapterDelegate extends AdapterDelegate<List<BaseEpisodeItem>> {

    private EpisodeOptionCallback callback;

    EpisodeOptionsAdapterDelegate(@NonNull EpisodeOptionCallback callback) {
        this.callback = callback;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseEpisodeItem> items, int position) {
        return items.get(position) instanceof EpisodeOptionsItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_episode_options, parent, false);
        return new EpisodeOptionsAdapterDelegate.ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseEpisodeItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        EpisodeOptionsItem optionsItem = (EpisodeOptionsItem) items.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bind(optionsItem);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint)
        ConstraintLayout layout;

        @BindView(R.id.continueButton)
        TextView continueBtn;

        @BindView(R.id.episodeTextView)
        TextView nextEpisode;

        @BindView(R.id.alternativeImageView)
        ImageView alternativeBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            Drawable card = DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.card)
                    .withAttributeColor(R.attr.colorBackgroundWindow)
                    .tint()
                    .get();

            layout.setBackground(card);

            Drawable icon = DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.ic_play_circle_filled)
                    .withAttributeColor(R.attr.colorText)
                    .tint()
                    .get();

            continueBtn.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);

        }

        public void bind(EpisodeOptionsItem optionsItem) {

            String buttonText;
            if (optionsItem.isFirst()) {
                buttonText = itemView.getResources().getString(R.string.watch_now);
            } else {
                buttonText = itemView.getResources().getString(R.string.watch_continue);

            }
            nextEpisode.setText(String.format(itemView.getContext().getString(R.string.episode_list_format), optionsItem.getEpisodeItem().getId()));

            continueBtn.setText(buttonText);

            continueBtn.setOnClickListener(v -> callback.onAction(EpisodeOptionAction.WATCH_ONLINE, optionsItem.getEpisodeItem()));
            alternativeBtn.setOnClickListener(v -> callback.onAction(EpisodeOptionAction.ALTERNATIVE_SOURCE, null));
        }
    }
}
