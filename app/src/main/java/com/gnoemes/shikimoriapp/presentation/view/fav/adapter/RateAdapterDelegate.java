package com.gnoemes.shikimoriapp.presentation.view.fav.adapter;

import android.graphics.Typeface;
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
import com.gnoemes.shikimoriapp.entity.rates.presentation.AnimeRateViewModel;
import com.gnoemes.shikimoriapp.presentation.view.fav.provider.UserRatesAnimeResourceProvider;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.AttributesHelper;
import com.gnoemes.shikimoriapp.utils.view.DefaultItemCallback;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.mpt.android.stv.Slice;
import com.mpt.android.stv.SpannableTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RateAdapterDelegate extends AdapterDelegate<List<BaseItem>> {

    private ImageLoader imageLoader;
    private UserRatesAnimeResourceProvider resourceProvider;
    private DefaultItemCallback callback;
    private DefaultItemCallback rateChangecallback;

    public RateAdapterDelegate(ImageLoader imageLoader,
                               UserRatesAnimeResourceProvider resourceProvider,
                               DefaultItemCallback callback,
                               DefaultItemCallback rateChangeCallback
    ) {
        this.imageLoader = imageLoader;
        this.resourceProvider = resourceProvider;
        this.callback = callback;
        this.rateChangecallback = rateChangeCallback;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseItem> items, int position) {
        return items.get(position) instanceof AnimeRateViewModel;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_rate_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onViewRecycled(@NonNull RecyclerView.ViewHolder viewHolder) {
        super.onViewRecycled(viewHolder);
        imageLoader.clearImage(((ViewHolder) viewHolder).animePreview);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        AnimeRateViewModel model = (AnimeRateViewModel) items.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bind(model);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint)
        ConstraintLayout layout;

        @BindView(R.id.image_anime)
        ImageView animePreview;

        @BindView(R.id.text_type)
        TextView type;

        @BindView(R.id.anime_title)
        TextView title;

        @BindView(R.id.rating)
        SpannableTextView rating;

        @BindView(R.id.episodes)
        SpannableTextView episodes;

        @BindView(R.id.comment)
        SpannableTextView comment;

        @BindView(R.id.image_watches)
        ImageView imageWatches;

        @BindView(R.id.text_watches)
        TextView watches;

        @BindView(R.id.image_menu)
        ImageView menuView;

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

            Drawable eye = DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.ic_eye)
                    .withAttributeColor(R.attr.colorText)
                    .tint()
                    .get();

            imageWatches.setImageDrawable(eye);

            layout.setPadding(0, 0, 0, 0);
            layout.setBackground(card);
        }

        public void bind(AnimeRateViewModel model) {
            layout.setOnClickListener(null);

            imageLoader.setImageWithFit(animePreview, model.getAnime().getImageOriginalUrl());
            imageWatches.setVisibility(View.GONE);
            watches.setVisibility(View.GONE);

            rating.reset();
            episodes.reset();
            comment.reset();

            String titleText = model.getAnime().getRussianName() == null ? model.getAnime().getName()
                    : model.getAnime().getRussianName();
            title.setText(titleText);

            type.setText(model.getAnime().getType().name());
            type.setBackgroundResource(resourceProvider.getColorByAnimeType(model.getAnime().getType()));


            String ratingText = model.getScore() == 0 ? "-" : String.valueOf(model.getScore());
            String ratingTitle = itemView.getContext().getResources().getString(R.string.rate_rating);
            rating.addSlice(getSliceTitle(ratingTitle));
            rating.addSlice(getSliceContent(ratingText));
            rating.display();

            String episodeTitle = itemView.getContext().getResources().getString(R.string.common_episodes);
            String episodesText = String.format(resourceProvider.getEpisodeRateStringFormat(),
                    String.valueOf(model.getEpisodes()),
                    String.valueOf(model.getAnime().getEpisodesAired() == 0 ? model.getAnime().getEpisodes() : model.getAnime().getEpisodesAired()),
                    model.getAnime().getEpisodes() == 0 ? "xxx" : model.getAnime().getEpisodes());
            episodes.addSlice(getSliceTitle(episodeTitle));
            episodes.addSlice(getSliceContent(episodesText));
            episodes.display();

            String commentContent = model.getText();
            if (!TextUtils.isEmpty(commentContent)) {
                String commentTitle = itemView.getContext().getResources().getString(R.string.rate_comment_title);
                comment.addSlice(getSliceTitle(commentTitle));
                comment.addSlice(getSliceContent(commentContent));
                comment.display();
                comment.setVisibility(View.VISIBLE);
            } else {
                comment.setVisibility(View.GONE);
            }

            if (model.getRewatches() != 0) {
                imageWatches.setVisibility(View.VISIBLE);
                watches.setVisibility(View.VISIBLE);
                watches.setText(String.valueOf(model.getRewatches()));
            }

            layout.setOnClickListener(v -> callback.onItemClick(model.getAnime().getId()));
            layout.setOnLongClickListener(v -> {
                rateChangecallback.onItemClick(model.getId());
                return true;
            });
            menuView.setOnClickListener(v -> rateChangecallback.onItemClick(model.getId()));
        }

        /**
         * Returns slice for title (e.g. <b>Genre:</b>)
         *
         * @param text String
         * @return Slice title
         */
        private Slice getSliceTitle(String text) {
            return new Slice.Builder(text.concat("\n"))
                    .textColor(textColor)
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
                    .build();
        }
    }
}
