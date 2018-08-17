package com.gnoemes.shikimoriapp.data.repository.series.converters.hostings;

import com.gnoemes.shikimoriapp.entity.series.domain.PlayVideo;
import com.gnoemes.shikimoriapp.entity.series.domain.VideoFormat;

import org.jsoup.nodes.Document;

public interface BaseVideoResponseConverter {

    PlayVideo convertResponse(long animeId, int episodeId, String title, Document document);

    default VideoFormat convertVideoFormat(String type) {
        for (VideoFormat format : VideoFormat.values()) {
            if (format.isEqualType(type)) {
                return format;
            }
        }
        return null;
    }
}
