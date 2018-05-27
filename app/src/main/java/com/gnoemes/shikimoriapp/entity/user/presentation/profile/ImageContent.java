package com.gnoemes.shikimoriapp.entity.user.presentation.profile;

public class ImageContent {
    private long id;
    private String url;

    public ImageContent(long id, String url) {
        this.id = id;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
