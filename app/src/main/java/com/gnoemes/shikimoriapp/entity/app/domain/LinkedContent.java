package com.gnoemes.shikimoriapp.entity.app.domain;

/**
 * Base interface for content with link ability
 */
public abstract class LinkedContent {

    private long linkedId;
    private LinkedType type;
    private String imageUrl;

    public LinkedContent(long linkedId, LinkedType type, String imageUrl) {
        this.linkedId = linkedId;
        this.type = type;
        this.imageUrl = imageUrl;
    }

    public long getLinkedId() {
        return linkedId;
    }

    public LinkedType getLinkedType() {
        return type;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
