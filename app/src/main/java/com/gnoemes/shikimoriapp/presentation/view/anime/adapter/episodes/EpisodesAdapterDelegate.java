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
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.EpisodeItem;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EpisodesAdapterDelegate extends AdapterDelegate<List<BaseEpisodeItem>> {

    @NonNull
    private EpisodePickCallback callback;

    EpisodesAdapterDelegate(@NonNull EpisodePickCallback callback) {
        this.callback = callback;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseEpisodeItem> items, int position) {
        return items.get(position) instanceof EpisodeItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_episode, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseEpisodeItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ViewHolder viewHolder = (ViewHolder) holder;
        EpisodeItem episode = (EpisodeItem) items.get(position);
        viewHolder.bind(episode);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.container)
        ConstraintLayout layout;

        @BindView(R.id.image_watched)
        ImageView watchedBadge;

        @BindView(R.id.text_episode)
        TextView episodeName;

        @BindView(R.id.image_arrow)
        ImageView arrow;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(EpisodeItem episode) {
            watchedBadge.setVisibility(View.GONE);

            episodeName.setText(episode.getEpisodeFull());

            DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.ic_chevron_right)
                    .withAttributeColor(R.attr.colorDivider)
                    .tint()
                    .applyTo(arrow);

            if (episode.isWatched()) {
                DrawableHelper
                        .withContext(itemView.getContext())
                        .withDrawable(R.drawable.ic_approval)
                        .withAttributeColor(R.attr.colorDivider)
                        .tint()
                        .applyTo(watchedBadge);
                watchedBadge.setVisibility(View.VISIBLE);
            }

            Drawable card = DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.card)
                    .withAttributeColor(R.attr.colorBackgroundContent)
                    .tint()
                    .get();

            layout.setBackground(card);

            layout.setOnClickListener(v -> callback.onEpisodePicked(episode));
        }
    }
}
