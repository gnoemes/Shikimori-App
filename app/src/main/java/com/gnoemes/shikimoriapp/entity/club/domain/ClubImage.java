package com.gnoemes.shikimoriapp.entity.club.domain;

public class ClubImage {
    private String original;
    private String preview;
    private String x96;
    private String x73;
    private String x48;

    public ClubImage(String original, String preview, String x96, String x73, String x48) {
        this.original = original;
        this.preview = preview;
        this.x96 = x96;
        this.x73 = x73;
        this.x48 = x48;
    }

    public String getOriginal() {
        return original;
    }

    public String getPreview() {
        return preview;
    }

    public String getX96() {
        return x96;
    }

    public String getX73() {
        return x73;
    }

    public String getX48() {
        return x48;
    }
}
