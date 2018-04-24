package com.gnoemes.shikimoriapp.presentation.view.search.adapter;

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
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeViewModel;
import com.gnoemes.shikimoriapp.entity.search.presentation.BaseSearchItem;
import com.gnoemes.shikimoriapp.presentation.view.search.provider.SearchAnimeResourceProvider;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.mpt.android.stv.Slice;
import com.mpt.android.stv.SpannableTextView;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimeItemAdapterDelegate extends AdapterDelegate<List<BaseSearchItem>> {

    @NonNull
    private SearchAnimeResourceProvider resourceProvider;
    @NonNull
    private SearchAnimeItemCallback callback;
    @NonNull
    private ImageLoader imageLoader;

    public AnimeItemAdapterDelegate(@NonNull SearchAnimeResourceProvider resourceProvider,
                                    @NonNull SearchAnimeItemCallback callback,
                                    @NonNull ImageLoader imageLoader) {
        this.resourceProvider = resourceProvider;
        this.callback = callback;
        this.imageLoader = imageLoader;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseSearchItem> items, int position) {
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
    protected void onBindViewHolder(@NonNull List<BaseSearchItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ViewHolder viewHolder = (ViewHolder) holder;
        AnimeViewModel item = (AnimeViewModel) items.get(position);

        viewHolder.bind(item);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.container)
        ConstraintLayout layout;
        @BindView(R.id.image_anime)
        ImageView animeImage;
        @BindView(R.id.text_type)
        TextView textType;
        @BindView(R.id.text_name)
        SpannableTextView name;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            layout.setOnClickListener(v -> callback.onItemClick(itemView.getId()));
        }

        public void bind(AnimeViewModel item) {

            imageLoader.setImage(animeImage, item.getImageOriginalUrl());

            textType.setText(item.getType().name());
            textType.setBackgroundResource(resourceProvider.getColorByAnimeType(item.getType()));

            name.reset();
            if (item.getRussianName() != null && Locale.getDefault().getLanguage().equals("ru")) {
                name.addSlice(getSliceWithName(item.getRussianName()));
            } else {
                name.addSlice(getSliceWithName(item.getName()));
            }

            String episodes = String.format(resourceProvider.getEpisodeStringFormat(),
                    item.getStatus() == AnimeStatus.RELEASED ? item.getEpisodes() : item.getEpisodesAired(),
                    item.getEpisodes() == 0 ? "xxx" : item.getEpisodes());

            name.addSlice(new Slice.Builder(episodes)
                    .textColor(itemView.getContext().getResources().getColor(R.color.colorAccentInverse))
                    .textSize((int) itemView.getContext().getResources().getDimension(R.dimen.text_normal))
                    .build());
            name.display();
        }

        private Slice getSliceWithName(String name) {
            return new Slice.Builder(name)
                    .textColor(itemView.getContext().getResources().getColor(R.color.white))
                    .build();
        }
    }
}
