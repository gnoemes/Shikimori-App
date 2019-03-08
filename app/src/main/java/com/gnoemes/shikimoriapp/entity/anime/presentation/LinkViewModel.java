package com.gnoemes.shikimoriapp.entity.anime.presentation;

public class LinkViewModel {

    private String name;
    private String url;

    public LinkViewModel(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
