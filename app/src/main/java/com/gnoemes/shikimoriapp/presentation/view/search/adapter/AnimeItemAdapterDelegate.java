package com.gnoemes.shikimoriapp.presentation.view.search.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.domain.Status;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeViewModel;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.presentation.view.search.provider.SearchAnimeResourceProvider;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DefaultItemCallback;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mpt.android.stv.Slice;
import com.mpt.android.stv.SpannableTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimeItemAdapterDelegate extends AdapterDelegate<List<BaseItem>> {

    @NonNull
    private SearchAnimeResourceProvider resourceProvider;
    @NonNull
    private DefaultItemCallback callback;
    @NonNull
    private ImageLoader imageLoader;

    public AnimeItemAdapterDelegate(@NonNull SearchAnimeResourceProvider resourceProvider,
                                    @NonNull DefaultItemCallback callback,
                                    @NonNull ImageLoader imageLoader) {
        this.resourceProvider = resourceProvider;
        this.callback = callback;
        this.imageLoader = imageLoader;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseItem> items, int position) {
        return items.get(position) instanceof AnimeViewModel;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View item = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_anime_search, parent, false);
        return new ViewHolder(item);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ViewHolder viewHolder = (ViewHolder) holder;
        AnimeViewModel item = (AnimeViewModel) items.get(position);

        viewHolder.bind(item);

    }

    @Override
    protected void onViewRecycled(@NonNull RecyclerView.ViewHolder viewHolder) {
        super.onViewRecycled(viewHolder);
        imageLoader.clearImage(((ViewHolder) viewHolder).animeImage);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.container)
        ConstraintLayout layout;
        @BindView(R.id.image_anime)
        RoundedImageView animeImage;
        @BindView(R.id.text_name)
        SpannableTextView name;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(AnimeViewModel item) {
            imageLoader.clearImage(animeImage);

            layout.setOnClickListener(null);

            imageLoader.setImageWithFit(animeImage, item.getImageOriginalUrl());

            name.reset();
            if (!TextUtils.isEmpty(item.getName())) {
                name.addSlice(getSliceWithName(item.getName()));
            } else {
                name.addSlice(getSliceWithName(item.getSecondName()));
            }

            String episodes = String.format(resourceProvider.getEpisodeStringFormat(),
                    item.getStatus() == Status.RELEASED ? item.getEpisodes() : item.getEpisodesAired(),
                    item.getEpisodes() == 0 ? "xxx" : item.getEpisodes());

            name.addSlice(new Slice.Builder(episodes)
                    .textColor(itemView.getContext().getResources().getColor(R.color.colorAccentInverse))
                    .build());
            name.display();

            layout.setOnClickListener(v -> callback.onItemClick(item.getId()));
        }

        private Slice getSliceWithName(String name) {
            return new Slice.Builder(name)
                    .textColor(itemView.getContext().getResources().getColor(R.color.white))
                    .build();
        }
    }
}
