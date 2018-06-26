package com.gnoemes.shikimoriapp.data.repository.anime.series.converters.impl;

import com.gnoemes.shikimoriapp.data.repository.anime.series.converters.PlayEpisodeConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.series.converters.PlayEpisodeResponseConverter;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.PlayEpisode;
import com.gnoemes.shikimoriapp.utils.net.parser.JsoupHtmlHelper;

import java.util.List;

import javax.inject.Inject;

public class PlayEpisodeConverterImpl implements PlayEpisodeConverter {

    private static final String ATTR_SUBTITLES = "data-vtt";
    private static final String ATTR_SOURCES = "data-sources";

    private PlayEpisodeResponseConverter responseConverter;

    @Inject
    public PlayEpisodeConverterImpl(PlayEpisodeResponseConverter responseConverter) {
        this.responseConverter = responseConverter;
    }

    @Override
    public List<PlayEpisode> apply(String html) {

        String subs = JsoupHtmlHelper
                .withHtml(html)
                .withAttr(ATTR_SUBTITLES)
                .findSingleValueByAttr()
                .getContent();

        String sources = JsoupHtmlHelper
                .withHtml(html)
                .withAttr(ATTR_SOURCES)
                .findSingleValueByAttr()
                .getContent();

        return responseConverter.convertListOfResponsesWithSubs(sources, subs);
    }
}
