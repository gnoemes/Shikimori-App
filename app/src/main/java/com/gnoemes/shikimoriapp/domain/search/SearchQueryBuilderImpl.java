package com.gnoemes.shikimoriapp.domain.search;

import android.support.v4.util.ArrayMap;

import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem;
import com.gnoemes.shikimoriapp.entity.search.domain.SearchConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.inject.Inject;

import io.reactivex.Single;

public class SearchQueryBuilderImpl implements SearchQueryBuilder {

    private static final String DIVIDER = ",";
    private static final String SEASON_DIVIDER = "_";

    @Inject
    public SearchQueryBuilderImpl() {
    }

    @Override
    public Single<Map<String, String>> createQueryFromFilters(HashMap<String, List<FilterItem>> filters, int page, int limit) {
        Map<String, String> queryMap = new ArrayMap<>();

        if (filters == null || filters.isEmpty()) {
            return Single.fromCallable(() -> getDefaultRequest(page, limit));
        }

        List<FilterItem> genreQuery = new ArrayList<>();
        List<FilterItem> typeQuery = new ArrayList<>();
        List<FilterItem> statusQuery = new ArrayList<>();
        List<FilterItem> orderQuery = new ArrayList<>();
        List<FilterItem> seasonQuery = new ArrayList<>();
        List<FilterItem> durationQuery = new ArrayList<>();
        List<FilterItem> searchQuery = new ArrayList<>();


        for (Map.Entry<String, List<FilterItem>> entry : filters.entrySet()) {

            for (FilterItem filterItem : entry.getValue()) {

                switch (filterItem.getAction()) {
                    case SearchConstants.GENRE:
                        genreQuery.add(filterItem);
                        break;
                    case SearchConstants.TYPE:
                        typeQuery.add(filterItem);
                        break;
                    case SearchConstants.STATUS:
                        statusQuery.add(filterItem);
                        break;
                    case SearchConstants.SEASON:
                        seasonQuery.add(filterItem);
                        break;
                    case SearchConstants.ORDER:
                        orderQuery.add(filterItem);
                        break;
                    case SearchConstants.DURATION:
                        durationQuery.add(filterItem);
                        break;
                    case SearchConstants.SEARCH:
                        searchQuery.add(filterItem);
                        break;
                }
            }
        }

        putToQuery(queryMap, genreQuery);
        putToQuery(queryMap, typeQuery);
        putToQuery(queryMap, statusQuery);
        putToQuery(queryMap, orderQuery);
        putSeasonToQuery(queryMap, seasonQuery);
        putToQuery(queryMap, durationQuery);
        putToQuery(queryMap, searchQuery);

        queryMap.put(SearchConstants.PAGE, String.valueOf(page));
        queryMap.put(SearchConstants.LIMIT, String.valueOf(limit));

        return Single.just(queryMap);
    }

    @Override
    public Single<Map<String, String>> createSearchQuery(String search, int page, int limit) {
        return null;
    }

    private Map<String, String> getDefaultRequest(int page, int limit) {
        Map<String, String> queryMap = new ArrayMap<>();
        queryMap.put(SearchConstants.PAGE, String.valueOf(page));
        queryMap.put(SearchConstants.LIMIT, String.valueOf(limit));
        queryMap.put(SearchConstants.ORDER, SearchConstants.ORDER_BY.POPULARITY.toString());
        return queryMap;
    }

    private void putToQuery(Map<String, String> map, List<FilterItem> filters) {
        if (filters != null && !filters.isEmpty()) {
            StringBuilder genreString = new StringBuilder();
            for (FilterItem item : filters) {
                genreString.append(item.getValue())
                        .append(DIVIDER);
            }

            genreString.deleteCharAt(genreString.length() - 1);
            map.put(filters.get(0).getAction(), genreString.toString());
        }
    }

    private void putSeasonToQuery(Map<String, String> map, List<FilterItem> filters) {
        if (filters != null && !filters.isEmpty()) {
            StringBuilder genreString = new StringBuilder();
            for (FilterItem item : filters) {
                String season = item.getValue();
                if (!Pattern.matches("\\d+", season)) {
                    genreString.append(season);
                    genreString.append(SEASON_DIVIDER);
                } else {
                    genreString.append(season);
                    genreString.append(DIVIDER);
                }
            }

            genreString.deleteCharAt(genreString.length() - 1);
            map.put(filters.get(0).getAction(), genreString.toString());
        }
    }
}
