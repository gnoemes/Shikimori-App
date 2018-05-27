package com.gnoemes.shikimoriapp.entity.user.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActivityPointResponse {

    @SerializedName("name")
    private List<Long> dates;
    @SerializedName("value")
    private int value;

    public List<Long> getDates() {
        return dates;
    }

    public int getValue() {
        return value;
    }
}
