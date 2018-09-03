package com.gnoemes.shikimoriapp.entity.anime.domain;

public class Link {

    private long id;
    private String name;
    private String url;

    public Link(long id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
