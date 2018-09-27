package com.gnoemes.shikimoriapp.presentation.view.calendar.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.calendar.presentation.CalendarItemViewModel;
import com.gnoemes.shikimoriapp.presentation.view.calendar.adapter.provider.CalendarAnimeResourceProvider;
import com.gnoemes.shikimoriapp.utils.date.converter.DateTimeConverter;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    private List<CalendarItemViewModel> calendarItems = new ArrayList<>();
    private DateTimeConverter dateTimeConverter;
    private CalendarAnimeResourceProvider resourceProvider;
    private ImageLoader imageLoader;
    private AnimeListener animeListener;

    public CalendarAdapter(@NonNull DateTimeConverter dateTimeConverter,
                           @NonNull CalendarAnimeResourceProvider resourceProvider,
                           @NonNull ImageLoader imageLoader,
                           AnimeListener animeListener) {
        this.dateTimeConverter = dateTimeConverter;
        this.resourceProvider = resourceProvider;
        this.imageLoader = imageLoader;
        this.animeListener = animeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_calendar, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CalendarItemViewModel item = calendarItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return calendarItems.size();
    }

    public void bindItems(List<CalendarItemViewModel> viewModels) {
        calendarItems.clear();
        calendarItems.addAll(viewModels);
        notifyDataSetChanged();
    }

    public interface AnimeListener {
        void onItemClicked(long id);
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        clearHolder(holder);
    }

    private void clearHolder(ViewHolder holder) {
        holder.animeAdapter.onDetachedFromRecyclerView(holder.animeList);
        holder.animeAdapter = null;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.linear_layout)
        LinearLayout layout;

        @BindView(R.id.text_date)
        TextView date;
        @BindView(R.id.list_anime)
        RecyclerView animeList;

        CalendarAnimeAdapter animeAdapter;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            Drawable card = DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.card)
                    .withAttributeColor(R.attr.colorBackgroundContent)
                    .tint()
                    .get();

            layout.setBackground(card);

        }

        void bind(CalendarItemViewModel item) {
            date.setText(dateTimeConverter.convertCalendarDateToString(item.getDate()));

            animeAdapter = new CalendarAnimeAdapter(resourceProvider, imageLoader, id -> animeListener.onItemClicked(id));

            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(),
                    LinearLayoutManager.HORIZONTAL, false);

            animeList.setLayoutManager(layoutManager);
            animeList.setAdapter(animeAdapter);


            animeAdapter.bindItems(item.getAnimeViewModels());
        }
    }
}
