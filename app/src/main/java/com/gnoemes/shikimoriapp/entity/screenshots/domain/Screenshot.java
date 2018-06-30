package com.gnoemes.shikimoriapp.entity.screenshots.domain;

public class Screenshot {
    private String original;
    private String preview;

    public Screenshot(String original, String preview) {
        this.original = original;
        this.preview = preview;
    }

    public String getOriginal() {
        return original;
    }

    public String getPreview() {
        return preview;
    }
}
