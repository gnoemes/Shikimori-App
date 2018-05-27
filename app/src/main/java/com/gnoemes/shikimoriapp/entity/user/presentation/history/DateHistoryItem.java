package com.gnoemes.shikimoriapp.entity.user.presentation.history;

public class DateHistoryItem extends BaseHistoryItem {

    private String date;

    public DateHistoryItem(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
