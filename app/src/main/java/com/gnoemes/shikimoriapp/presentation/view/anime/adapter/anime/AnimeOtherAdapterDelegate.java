package com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.presentation.DetailsAction;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.AnimeOtherItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.video.domain.VideoType;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimeOtherAdapterDelegate extends AdapterDelegate<List<BaseItem>> {

    private AnimeItemCallback callback;

    AnimeOtherAdapterDelegate(@NonNull AnimeItemCallback callback) {
        this.callback = callback;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseItem> items, int position) {
        return items.get(position) instanceof AnimeOtherItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_anime_other, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        AnimeOtherItem item = (AnimeOtherItem) items.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bind(item);
    }

    private int getTitleFromType(VideoType videoType) {
        switch (videoType) {
            case PROMO:
                return R.string.promo;
            case ENDING:
                return R.string.ending;
            case OPENING:
                return R.string.opening;
            case OTHER:
                return R.string.other;
            default:
                return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint)
        ConstraintLayout layout;

        @BindView(R.id.title)
        TextView titleView;

        @BindView(R.id.player_type)
        TextView playerView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            Drawable card = DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.card)
                    .withColor(R.color.blue)
                    .tint()
                    .get();

            layout.setBackground(card);
        }


        public void bind(AnimeOtherItem item) {
            layout.setOnClickListener(null);

            playerView.setText(item.getPlayer());

            if (!TextUtils.isEmpty(item.getName())) {
                titleView.setText(item.getName());
            } else {
                titleView.setText(getTitleFromType(item.getType()));
            }

            layout.setOnClickListener(v -> callback.onAction(DetailsAction.VIDEO, item.getUrl()));
        }
    }
}
