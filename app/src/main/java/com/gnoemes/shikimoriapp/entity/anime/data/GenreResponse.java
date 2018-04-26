package com.gnoemes.shikimoriapp.entity.anime.data;

import com.google.gson.annotations.SerializedName;

public class GenreResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("russian")
    private String russianName;
    @SerializedName("kind")
    private String type;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRussianName() {
        return russianName;
    }

    public String getType() {
        return type;
    }
}
