package com.gnoemes.shikimoriapp.data.repository.series.converters;

import com.gnoemes.shikimoriapp.entity.series.domain.PlayVideo;
import com.gnoemes.shikimoriapp.entity.series.domain.VideoHosting;

import org.jsoup.nodes.Document;

public interface PlayVideoResponseConverter {
    PlayVideo apply(Document document, long animeId, int episodeId);

    PlayVideo convertDependsOnHosting(long animeId, int episodeId, VideoHosting hosting, String title, Document document);
}
