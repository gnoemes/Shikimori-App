package com.gnoemes.shikimoriapp.presentation.view.search.filter;

import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem;

import java.util.List;

public interface FilterResourceProvider {

    /**
     * Get string "Genre"
     */
    String getGenreString();

    /**
     * Get string "type"
     */
    String getTypeString();

    /**
     * Get string "status"
     */
    String getStatusString();

    /**
     * Get string "Advance search"
     */
    String getAdvanceSearchString();

    /**
     * Get string "Seasons"
     */
    String getSeasonString();

    /**
     * Get string "Year"
     */
    String getYearString();

    /**
     * Get string "Order by"
     */
    String getOrderByString();

    /**
     * Get string "Duration"
     */
    String getDurationString();

    /**
     * Get array of genres
     */
    List<FilterItem> getGenres();

    /**
     * Get array of types
     */
    List<FilterItem> getTypes();

    /**
     * Get array of statuses
     */
    List<FilterItem> getStatuses();

    List<FilterItem> getSeasons();

    List<FilterItem> getOrder();

    List<FilterItem> getDurations();

    List<FilterItem> getYears();
}
