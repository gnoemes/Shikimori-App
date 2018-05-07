package com.gnoemes.shikimoriapp.entity.comments.presentation;

public class CommentContentViewModel {
    private String text;
    private boolean isQuote;

    public CommentContentViewModel(String text, boolean isQuote) {
        this.text = text;
        this.isQuote = isQuote;
    }

    public String getText() {
        return text;
    }

    public boolean isQuote() {
        return isQuote;
    }
}
