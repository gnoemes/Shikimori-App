package com.gnoemes.shikimoriapp.entity.app.domain;

public class TitleException extends BaseException {
    public static final String TAG = "TitleException";

    private String title;

    private String message;

    public TitleException(String title, String message) {
        super(TAG);
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
