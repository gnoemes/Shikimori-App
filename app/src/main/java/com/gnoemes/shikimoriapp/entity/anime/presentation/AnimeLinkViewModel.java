package com.gnoemes.shikimoriapp.entity.anime.presentation;

public class AnimeLinkViewModel {

    private String name;
    private String url;

    public AnimeLinkViewModel(String name, String url) {
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
