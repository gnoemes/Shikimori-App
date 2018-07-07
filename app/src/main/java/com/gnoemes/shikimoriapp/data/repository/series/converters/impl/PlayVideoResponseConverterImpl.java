package com.gnoemes.shikimoriapp.data.repository.series.converters.impl;

import com.gnoemes.shikimoriapp.data.repository.series.converters.PlayVideoResponseConverter;
import com.gnoemes.shikimoriapp.entity.series.domain.PlayVideo;
import com.gnoemes.shikimoriapp.entity.series.domain.VideoHosting;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Pattern;

import javax.inject.Inject;

public class PlayVideoResponseConverterImpl implements PlayVideoResponseConverter {

    private static final String URL_QUERY = "div.video-link a";
    private static final String HREF_QUERY = "href";

    private static final String SMOTRET_ANIME_REGEX = "https?://smotret-anime\\.ru/";
    private static final String SIBNET_REGEX = "https?://video\\.sibnet\\.ru/";
    private static final String VK_REGEX = "https?://vk\\.com/";
    private static final String YOUTUBE_REGEX = "https?://(?:www\\.)?youtube\\.com/";
    private static final String RUTUBE_REGEX = "https?://rutube\\.ru/";
    private static final String OK_REGEX = "https?://ok\\.ru/";
    private static final String SOVET_ROMANTICA_REGEX = "https?://sovetromantica\\.com/";
    private static final String ANIMEDIA_REGEX = "https?://online\\.animedia\\.tv/";

    @Inject
    public PlayVideoResponseConverterImpl() {
    }

    @Override
    public PlayVideo apply(Document document, long animeId, int episodeId) {

        Elements elements = document.select(URL_QUERY);
        String url = null;

        for (Element e : elements) {
            url = e.attr(HREF_QUERY);
        }


        return new PlayVideo(animeId, episodeId, convertHosting(url), url);
    }

    private VideoHosting convertHosting(String url) {
        if (Pattern.matches(SMOTRET_ANIME_REGEX, url)) {
            return VideoHosting.SMOTRET_ANIME;
        } else if (Pattern.matches(SIBNET_REGEX, url)) {
            return VideoHosting.SIBNET;
        } else if (Pattern.matches(VK_REGEX, url)) {
            return VideoHosting.VK;
        } else if (Pattern.matches(YOUTUBE_REGEX, url)) {
            return VideoHosting.YOUTUBE;
        } else if (Pattern.matches(OK_REGEX, url)) {
            return VideoHosting.OK;
        } else if (Pattern.matches(RUTUBE_REGEX, url)) {
            return VideoHosting.RUTUBE;
        } else if (Pattern.matches(SOVET_ROMANTICA_REGEX, url)) {
            return VideoHosting.SOVET_ROMANTICA;
        } else if (Pattern.matches(ANIMEDIA_REGEX, url)) {
            return VideoHosting.ANIMEDIA;
        }

        return VideoHosting.UNKNOWN;
    }
}
