package com.gnoemes.shikimoriapp.entity.app.presentation;

public class PlaceHolderItem extends BaseItem {

    private String message;

    public PlaceHolderItem(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
