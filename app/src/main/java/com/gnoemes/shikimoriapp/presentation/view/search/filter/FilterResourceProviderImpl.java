package com.gnoemes.shikimoriapp.presentation.view.search.filter;

import android.content.Context;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeGenre;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeStatus;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeType;
import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem;
import com.gnoemes.shikimoriapp.entity.search.domain.SearchConstants;
import com.gnoemes.shikimoriapp.utils.date.DateTimeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class FilterResourceProviderImpl implements FilterResourceProvider {

    private Context context;
    private DateTimeUtils dateTimeUtils;
    private FilterResourceConverter resourceConverter;

    @Inject
    public FilterResourceProviderImpl(Context context,
                                      DateTimeUtils dateTimeUtils,
                                      FilterResourceConverter resourceConverter) {
        this.context = context;
        this.dateTimeUtils = dateTimeUtils;
        this.resourceConverter = resourceConverter;
    }

    @Override
    public String getGenreString() {
        return context.getString(R.string.common_genre);
    }

    @Override
    public String getTypeString() {
        return context.getString(R.string.common_type);
    }

    @Override
    public String getStatusString() {
        return context.getString(R.string.common_status);
    }

    @Override
    public String getAdvanceSearchString() {
        return context.getString(R.string.search_advance);
    }

    @Override
    public String getSeasonString() {
        return context.getString(R.string.common_season);
    }

    @Override
    public String getYearString() {
        return context.getString(R.string.common_year);
    }

    @Override
    public String getOrderByString() {
        return context.getString(R.string.common_order_by);
    }

    @Override
    public String getDurationString() {
        return context.getString(R.string.common_duration);
    }

    @Override
    public List<FilterItem> getGenres() {
        List<String> localizedGenres = Arrays.asList(context.getResources().getStringArray((R.array.genres)));
        List<String> genresNames = Arrays.asList(context.getResources().getStringArray(R.array.genres_names));
        return resourceConverter.convertFrom(genresNames, localizedGenres, AnimeGenre.values());
    }

    @Override
    public List<FilterItem> getTypes() {
        List<String> localisedTypes = Arrays.asList(context.getResources().getStringArray(R.array.types));
        List<String> typeNames = Arrays.asList(context.getResources().getStringArray(R.array.types_name));
        return resourceConverter.convertFrom(typeNames, localisedTypes, AnimeType.values());
    }

    @Override
    public List<FilterItem> getStatuses() {
        List<String> localisedStatuses = Arrays.asList(context.getResources().getStringArray(R.array.statuses));
        List<String> statusesNames = Arrays.asList(context.getResources().getStringArray(R.array.statuses_names));
        return resourceConverter.convertFrom(statusesNames, localisedStatuses, AnimeStatus.values());
    }

    @Override
    public List<FilterItem> getSeasons() {
        List<String> localisedSeasons = Arrays.asList(context.getResources().getStringArray(R.array.seasons));
        List<String> seasonsNames = Arrays.asList(context.getResources().getStringArray(R.array.seasons_names));
        return resourceConverter.convertFrom(seasonsNames, localisedSeasons, SearchConstants.SEASONS.values());
    }

    @Override
    public List<FilterItem> getOrder() {
        List<String> localisedSeasons = Arrays.asList(context.getResources().getStringArray(R.array.order));
        List<String> seasonsNames = Arrays.asList(context.getResources().getStringArray(R.array.order_names));
        return resourceConverter.convertFrom(seasonsNames, localisedSeasons, SearchConstants.ORDER_BY.values());
    }

    @Override
    public List<FilterItem> getDurations() {
        List<String> localisedSeasons = Arrays.asList(context.getResources().getStringArray(R.array.duration));
        List<String> seasonsNames = Arrays.asList(context.getResources().getStringArray(R.array.duration_names));
        return resourceConverter.convertFrom(seasonsNames, localisedSeasons, SearchConstants.DURATIONS.values());
    }

    @Override
    public List<FilterItem> getYears() {
        List<FilterItem> years = new ArrayList<>();
        for (int i = 1980; i <= dateTimeUtils.getNowDateTime().getYear(); i++) {
            final String year = String.valueOf(i);
            years.add(new FilterItem(SearchConstants.SEASON, year, year));
        }
        return years;
    }

}
