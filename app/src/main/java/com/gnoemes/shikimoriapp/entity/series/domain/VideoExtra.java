package com.gnoemes.shikimoriapp.entity.series.domain;

import android.support.annotation.Nullable;

import java.util.List;

public class VideoExtra {
    @Nullable
    private List<VideoQuality> qualities;

    public VideoExtra(@Nullable List<VideoQuality> qualities) {
        this.qualities = qualities;
    }

    @Nullable
    public List<VideoQuality> getQualities() {
        return qualities;
    }
}
