package com.gnoemes.shikimoriapp.entity.video.domain;

public class DownloadVideo {

    private String fileName;
    private String animeName;
    private String downloadLink;

    public DownloadVideo(String fileName, String animeName, String downloadLink) {
        this.fileName = fileName;
        this.animeName = animeName;
        this.downloadLink = downloadLink;
    }

    public String getFileName() {
        return fileName;
    }

    public String getAnimeName() {
        return animeName;
    }

    public String getDownloadLink() {
        return downloadLink;
    }
}
