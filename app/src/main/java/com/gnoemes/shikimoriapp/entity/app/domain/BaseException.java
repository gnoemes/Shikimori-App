package com.gnoemes.shikimoriapp.entity.app.domain;

public abstract class BaseException extends Exception {
    private String tag;

    public BaseException(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
