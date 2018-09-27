package com.gnoemes.shikimoriapp.presentation.view.calendar.adapter;

import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.calendar.presentation.CalendarAnimeViewModel;
import com.gnoemes.shikimoriapp.presentation.view.calendar.adapter.provider.CalendarAnimeResourceProvider;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DefaultItemCallback;
import com.mpt.android.stv.Slice;
import com.mpt.android.stv.SpannableTextView;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class CalendarAnimeAdapter extends RecyclerView.Adapter<CalendarAnimeAdapter.ViewHolder> {

    private List<CalendarAnimeViewModel> animeList = new ArrayList<>();
    private CalendarAnimeResourceProvider resourceProvider;
    private ImageLoader imageLoader;
    private DefaultItemCallback callback;

    public CalendarAnimeAdapter(CalendarAnimeResourceProvider resourceProvider,
                                ImageLoader imageLoader,
                                DefaultItemCallback callback) {
        this.resourceProvider = resourceProvider;
        this.imageLoader = imageLoader;
        this.callback = callback;
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

    public void bindItems(List<CalendarAnimeViewModel> animeViewModels) {
        animeList.clear();
        animeList.addAll(animeViewModels);
        notifyDataSetChanged();
    }

    private void clearHolder(ViewHolder holder) {
        if (holder.d != null && !holder.d.isDisposed()) {
            holder.d.dispose();
        }
        holder.container.setOnClickListener(null);
        imageLoader.clearImage(holder.image);
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        clearHolder(holder);
    }

    @Override
    public boolean onFailedToRecycleView(@NonNull ViewHolder holder) {
        clearHolder(holder);
        return super.onFailedToRecycleView(holder);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final PeriodFormatter hoursAndMinutesAndSeconds = new PeriodFormatterBuilder()
                .printZeroAlways()
                .minimumPrintedDigits(2)
                .appendHours()
                .appendSeparator(":")
                .minimumPrintedDigits(2)
                .appendMinutes()
                .appendSeparator(":")
                .minimumPrintedDigits(2)
                .appendSeconds()
                .toFormatter();
        @BindView(R.id.imageView)
        ImageView image;
        @BindView(R.id.typeView)
        TextView type;
        @BindView(R.id.nameView)
        SpannableTextView name;
        @BindView(R.id.nextEpisodeView)
        TextView nextEpisode;
        @BindView(R.id.cardView)
        MaterialCardView container;
        @BindView(R.id.timer)
        TextView timerView;
        Disposable d;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(CalendarAnimeViewModel item) {
            imageLoader.setImageWithFit(image, item.getImageOriginalUrl());

            type.setText(item.getType().name());

            name.reset();
            if (!TextUtils.isEmpty(item.getName())) {
                name.addSlice(getSliceWithName(item.getName()));
            } else {
                name.addSlice(getSliceWithName(item.getSecondName()));
            }

            String episodes = String.format(resourceProvider.getEpisodeStringFormat(),
                    item.getEpisodesAired(),
                    item.getEpisodes() == 0 ? "xxx" : item.getEpisodes());

            name.addSlice(new Slice.Builder(episodes)
                    .textColor(itemView.getContext().getResources().getColor(R.color.colorAccentInverse))
                    .textSize((int) itemView.getContext().getResources().getDimension(R.dimen.text_normal))
                    .build());
            name.display();


            String episodeInTime = String.format(resourceProvider.getEpisodeInString(), item.getNextEpisode());
            nextEpisode.setText(episodeInTime);

            d = Observable.interval(300, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .forEach(aLong -> {
                        DateTime now = DateTime.now();
                        if (now.isBefore(item.getNextEpisodeDate())) {
                            Duration duration = new Interval(now, item.getNextEpisodeDate()).toDuration();
                            timerView.setText(hoursAndMinutesAndSeconds.print(duration.toPeriod()));
                        } else {
                            timerView.setText(R.string.calendar_must_aired);
                        }
                    });

            container.setOnClickListener(view -> callback.onItemClick(item.getId()));
        }

        private Slice getSliceWithName(String name) {
            return new Slice.Builder(name)
                    .textColor(itemView.getContext().getResources().getColor(R.color.white))
                    .build();
        }
    }
}
