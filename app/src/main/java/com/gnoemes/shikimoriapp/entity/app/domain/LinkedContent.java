package com.gnoemes.shikimoriapp.entity.app.domain;

/**
 * Base interface for content with link ability
 */
public abstract class LinkedContent {

    private long linkedId;
    private String linkedName;
    private LinkedType type;
    private String imageUrl;

    public LinkedContent(long linkedId, String linkedName, LinkedType type, String imageUrl) {
        this.linkedId = linkedId;
        this.linkedName = linkedName;
        this.type = type;
        this.imageUrl = imageUrl;
    }

    public long getLinkedId() {
        return linkedId;
    }

    public String getLinkedName() {
        return linkedName;
    }

    public LinkedType getLinkedType() {
        return type;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
