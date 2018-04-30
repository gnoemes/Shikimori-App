package com.gnoemes.shikimoriapp.entity.anime.data;

import com.gnoemes.shikimoriapp.entity.anime.series.data.network.SeriesResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeriesDataResponse {

    @SerializedName("data")
    List<SeriesResponse> seriesResponses;

    public List<SeriesResponse> getSeriesResponses() {
        return seriesResponses;
    }
}
