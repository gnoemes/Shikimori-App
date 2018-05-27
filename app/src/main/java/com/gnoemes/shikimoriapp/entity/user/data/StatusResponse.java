package com.gnoemes.shikimoriapp.entity.user.data;

import com.google.gson.annotations.SerializedName;

public class StatusResponse {

    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("size")
    private int size;
    @SerializedName("type")
    private String type;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }
}
