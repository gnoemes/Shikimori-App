package com.gnoemes.shikimoriapp.data.repository.series.converters.impl;

import com.gnoemes.shikimoriapp.data.repository.series.converters.SeriesResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.series.converters.TranslationResponseConverter;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Translation;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationQuality;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.entity.series.domain.VideoHosting;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TranslationResponseConverterImpl implements TranslationResponseConverter {

    private static final String ALL_QUERY = "div.video-variant-group[data-kind=%s]";
    private static final String TRANSLATIONS_QUERY = "div.b-video_variant[data-video_id]";
    private static final String VIDEO_ID_QUERY = "data-video_id";
    private static final String REJECTED_QUERY = "rejected";
    private static final String BROKEN_QUERY = "broken";
    private static final String BANNED_QUERY = "banned_hosting";
    private static final String QUALITY_QUERY = "video-quality";
    private static final String TRANSLATION_TYPE_QUERY = "video-kind";
    private static final String VIDEO_HOSTING_QUERY = "video-hosting";
    private static final String AUTHOR_QUERY = "video-author";

    private SeriesResponseConverter seriesResponseConverter;

    @Inject
    public TranslationResponseConverterImpl(SeriesResponseConverter seriesResponseConverter) {
        this.seriesResponseConverter = seriesResponseConverter;
    }

    @Override
    public List<Translation> convert(long animeId, int episodeId, TranslationType type, Document document) {
        List<Translation> translations = new ArrayList<>();

        int episodesSize = seriesResponseConverter.apply(animeId, document).getEpisodesSize();

        String ss = type == TranslationType.SUB_RU ? "subtitles" : type == TranslationType.VOICE_RU ? "fandub" : "raw";
        String kek = String.format(ALL_QUERY, ss);
        Element all = document.select(kek).first();

        if (all != null) {
            Elements group = all.select(TRANSLATIONS_QUERY);
            for (Element e : group) {
                translations.add(convertTranslation(animeId, episodeId, e, episodesSize));
            }
        }

        return translations;
    }

    private Translation convertTranslation(long animeId, int episodeId, Element e, int episodesSize) {

        long videoId = Long.parseLong(e.attr(VIDEO_ID_QUERY));
        boolean isRejected = e.getElementsByClass(REJECTED_QUERY).first() != null;
        boolean isBroken = e.getElementsByClass(BROKEN_QUERY).first() != null;
        boolean isBanned = e.getElementsByClass(BANNED_QUERY).first() != null;

        TranslationQuality quality = convertQuality(e.getElementsByClass(QUALITY_QUERY).toString().replaceAll("(<span class=\")", "").replaceAll("(\"></span>)", "").replaceFirst("video-quality", ""));
        TranslationType translationType = convertType(e.getElementsByClass(TRANSLATION_TYPE_QUERY).text());
        VideoHosting hosting = convertHosting(e.getElementsByClass(VIDEO_HOSTING_QUERY).text());
        String author = e.getElementsByClass(AUTHOR_QUERY).text();

        return new Translation(animeId, episodeId, videoId, translationType, quality, hosting, author, !isRejected && !isBroken && !isBanned, episodesSize);
    }

    private VideoHosting convertHosting(String text) {
        for (VideoHosting hosting : VideoHosting.values()) {
            if (hosting.isEqualType(text)) {
                return hosting;
            }
        }

        return VideoHosting.UNKNOWN;
    }

    private TranslationType convertType(String text) {
        for (TranslationType type : TranslationType.values()) {
            if (type.isEqualType(text.trim().toLowerCase())) {
                return type;
            }
        }
        return TranslationType.ALL;
    }

    private TranslationQuality convertQuality(String text) {
        for (TranslationQuality quality : TranslationQuality.values()) {
            if (quality.equalQuality(text.trim().toLowerCase())) {
                return quality;
            }
        }
        return TranslationQuality.TV;
    }
}
