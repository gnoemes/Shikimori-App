package com.gnoemes.shikimoriapp.entity.app.domain;

/**
 * Base interface for content with link ability
 */
public abstract class LinkedContent {

    private long id;
    private LinkedType type;
    private String imageUrl;

    public LinkedContent(long id, LinkedType type, String imageUrl) {
        this.id = id;
        this.type = type;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public LinkedType getLinkedType() {
        return type;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
