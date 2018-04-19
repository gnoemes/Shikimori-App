package com.gnoemes.shikimoriapp.entity.app.domain;

public class NetworkException extends BaseException {
    public static final String TAG = "NetworkException";

    private String message;

    public NetworkException(String message) {
        super(TAG);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
