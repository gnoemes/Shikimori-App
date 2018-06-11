package com.gnoemes.shikimoriapp.entity.manga.domain;

public class MangaImage {
    private String imageOriginalUrl;
    private String imagePreviewUrl;
    private String imageX96Url;
    private String imageX48Url;

    public MangaImage(String imageOriginalUrl,
                      String imagePreviewUrl,
                      String imageX96Url,
                      String imageX48Url) {
        this.imageOriginalUrl = imageOriginalUrl;
        this.imagePreviewUrl = imagePreviewUrl;
        this.imageX96Url = imageX96Url;
        this.imageX48Url = imageX48Url;
    }

    public String getImageOriginalUrl() {
        return imageOriginalUrl;
    }

    public String getImagePreviewUrl() {
        return imagePreviewUrl;
    }

    public String getImageX96Url() {
        return imageX96Url;
    }

    public String getImageX48Url() {
        return imageX48Url;
    }
}
