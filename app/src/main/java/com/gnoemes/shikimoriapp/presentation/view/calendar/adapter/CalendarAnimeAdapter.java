package com.gnoemes.shikimoriapp.presentation.view.calendar.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.BuildConfig;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.calendar.presentation.CalendarAnimeViewModel;
import com.gnoemes.shikimoriapp.presentation.view.calendar.adapter.provider.CalendarAnimeResourceProvider;
import com.gnoemes.shikimoriapp.utils.date.converter.DateTimeConverter;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.mpt.android.stv.Slice;
import com.mpt.android.stv.SpannableTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarAnimeAdapter extends RecyclerView.Adapter<CalendarAnimeAdapter.ViewHolder> {

    private List<CalendarAnimeViewModel> animeList = new ArrayList<>();
    private CalendarAnimeResourceProvider resourceProvider;
    private ImageLoader imageLoader;
    private DateTimeConverter dateTimeConverter;

    public CalendarAnimeAdapter(CalendarAnimeResourceProvider resourceProvider,
                                ImageLoader imageLoader,
                                DateTimeConverter dateTimeConverter) {
        this.resourceProvider = resourceProvider;
        this.imageLoader = imageLoader;
        this.dateTimeConverter = dateTimeConverter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_calendar_anime, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CalendarAnimeViewModel item = animeList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    public void addNewItems(List<CalendarAnimeViewModel> animeViewModels) {
        animeList = animeViewModels;
        notifyDataSetChanged();
    }

    public CalendarAnimeViewModel getItemByPosition(int position) {
        return animeList.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_anime)
        ImageView image;
        @BindView(R.id.text_type)
        TextView type;
        @BindView(R.id.text_name)
        SpannableTextView name;
        @BindView(R.id.text_next_episode)
        TextView nextEpisode;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(CalendarAnimeViewModel item) {

            imageLoader.setImageWithFit(image, BuildConfig.ShikimoriBaseUrl + item.getImageOriginalUrl());

            type.setText(item.getType().name());
            type.setBackgroundResource(resourceProvider.getColorByAnimeType(item.getType()));

            name.reset();
            if (item.getRussianName() != null && Locale.getDefault().getLanguage().equals("ru")) {
                name.addSlice(getSliceWithName(item.getRussianName()));
            } else {
                name.addSlice(getSliceWithName(item.getName()));
            }

            String episodes = String.format(resourceProvider.getEpisodeStringFormat(),
                    item.getEpisodesAired(),
                    item.getEpisodes() == 0 ? "xxx" : item.getEpisodes());

            name.addSlice(new Slice.Builder(episodes)
                    .textColor(itemView.getContext().getResources().getColor(R.color.colorAccentInverse))
                    .textSize((int) itemView.getContext().getResources().getDimension(R.dimen.text_normal))
                    .build());
            name.display();


            String episodeInTime = String.format(resourceProvider.getEpisodeInString(), item.getNextEpisode(),
                    dateTimeConverter.convertCalendarTimeToString(item.getNextEpisodeDate()));
            nextEpisode.setText(episodeInTime);
            nextEpisode.setBackgroundResource(resourceProvider.getColorByAnimeType(item.getType()));
        }

        private Slice getSliceWithName(String name) {
            return new Slice.Builder(name)
                    .textColor(itemView.getContext().getResources().getColor(R.color.white))
                    .build();
        }
    }
}
