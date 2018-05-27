package com.gnoemes.shikimoriapp.entity.user.domain;

import com.gnoemes.shikimoriapp.entity.app.domain.FavoriteType;

public class Favorite {

    private long id;
    private FavoriteType type;
    private String name;
    private String russianName;
    private String imageUrl;
    private String url;

    public Favorite(long id, FavoriteType type, String name, String russianName, String imageUrl, String url) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.russianName = russianName;
        this.imageUrl = imageUrl;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public FavoriteType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getRussianName() {
        return russianName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }
}
