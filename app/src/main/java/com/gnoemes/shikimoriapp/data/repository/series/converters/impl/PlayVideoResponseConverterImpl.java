package com.gnoemes.shikimoriapp.data.repository.series.converters.impl;

import com.gnoemes.shikimoriapp.data.repository.series.converters.PlayVideoResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.series.converters.hostings.SibnetVideoResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.series.converters.hostings.VkVideoResponseConverter;
import com.gnoemes.shikimoriapp.entity.main.domain.Constants;
import com.gnoemes.shikimoriapp.entity.series.domain.PlayVideo;
import com.gnoemes.shikimoriapp.entity.series.domain.VideoFormat;
import com.gnoemes.shikimoriapp.entity.series.domain.VideoHosting;
import com.gnoemes.shikimoriapp.entity.series.domain.VideoTrack;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

import javax.inject.Inject;

public class PlayVideoResponseConverterImpl implements PlayVideoResponseConverter {

    private static final String URL_QUERY = "div.video-link a";
    private static final String HREF_QUERY = "href";
    private static final String TITLE_QUERY = "a.b-link>span[itemprop]";

    private static final String SMOTRET_ANIME_REGEX = "https?://smotretanime\\.ru/";
    private static final String SIBNET_REGEX = "https?://video\\.sibnet\\.ru/";
    private static final String VK_REGEX = "https?://vk\\.com/";
    private static final String YOUTUBE_REGEX = "https?://(?:www\\.)?youtube\\.com/";
    private static final String RUTUBE_REGEX = "https?://rutube\\.ru/";
    private static final String OK_REGEX = "https?://ok\\.ru/";
    private static final String SOVET_ROMANTICA_REGEX = "https?://sovetromantica\\.com/";
    private static final String ANIMEDIA_REGEX = "https?://online\\.animedia\\.tv/";
    private static final String MAIL_RU = "https?://my\\.mail\\.ru/";

    private SibnetVideoResponseConverter sibnetVideoResponseConverter;
    private VkVideoResponseConverter vkVideoResponseConverter;


    @Inject
    public PlayVideoResponseConverterImpl(SibnetVideoResponseConverter sibnetVideoResponseConverter,
                                          VkVideoResponseConverter vkVideoResponseConverter) {
        this.sibnetVideoResponseConverter = sibnetVideoResponseConverter;
        this.vkVideoResponseConverter = vkVideoResponseConverter;
    }

    @Override
    public PlayVideo apply(Document document, long animeId, int episodeId) {

        Elements elements = document.select(URL_QUERY);
        String url = null;
        Element titleElem = document.select(TITLE_QUERY).last();
        String title = titleElem == null ? null : titleElem.text();

        for (Element e : elements) {
            url = e.attr(HREF_QUERY);
        }

        return new PlayVideo(animeId, episodeId, convertHosting(url), title, url);
    }

    @Override
    public String convertSibnetDashToMp4Link(String url) {
        return sibnetVideoResponseConverter.convertDashToMp4Link(url);
    }

    @Override
    public PlayVideo convertMp4FromDashSibnetResponse(PlayVideo video, String url) {
        List<VideoTrack> trackList = new ArrayList<>();

        trackList.add(new VideoTrack((int) Constants.NO_ID, sibnetVideoResponseConverter.convertDashToMp4Link(url), VideoFormat.MP4));

        return new PlayVideo(video.getAnimeId(), video.getEpisodeId(), video.getHosting(), video.getTitle(), trackList);
    }

    private VideoHosting convertHosting(String url) {
        if (Pattern.compile(SMOTRET_ANIME_REGEX).matcher(url).find()) {
            return VideoHosting.SMOTRET_ANIME;
        } else if (Pattern.compile(SIBNET_REGEX).matcher(url).find()) {
            return VideoHosting.SIBNET;
        } else if (Pattern.compile(VK_REGEX).matcher(url).find()) {
            return VideoHosting.VK;
        } else if (Pattern.compile(YOUTUBE_REGEX).matcher(url).find()) {
            return VideoHosting.YOUTUBE;
        } else if (Pattern.compile(OK_REGEX).matcher(url).find()) {
            return VideoHosting.OK;
        } else if (Pattern.compile(RUTUBE_REGEX).matcher(url).find()) {
            return VideoHosting.RUTUBE;
        } else if (Pattern.compile(SOVET_ROMANTICA_REGEX).matcher(url).find()) {
            return VideoHosting.SOVET_ROMANTICA;
        } else if (Pattern.compile(ANIMEDIA_REGEX).matcher(url).find()) {
            return VideoHosting.ANIMEDIA;
        } else if (Pattern.compile(MAIL_RU).matcher(url).find()) {
            return VideoHosting.MAIL_RU;
        }

        return VideoHosting.UNKNOWN;
    }

    @Override
    public PlayVideo convertDependsOnHosting(long animeId, int episodeId, VideoHosting hosting, String playVideoTitle, String sourceUrl, Document document) {
        switch (hosting) {
            case YOUTUBE:
                return convertYoutubeSource(animeId, episodeId, document);
            case OK:
                return convertOkSource(animeId, episodeId, document);
            case VK:
                return vkVideoResponseConverter.convertResponse(animeId, episodeId, playVideoTitle, sourceUrl, document);
            case MY_VI:
                return convertMyViSource(animeId, episodeId, document);
            case RUTUBE:
                return convertRutubeSource(animeId, episodeId, document);
            case SIBNET:
                return sibnetVideoResponseConverter.convertResponse(animeId, episodeId, playVideoTitle, sourceUrl, document);
            case MAIL_RU:
                return convertMailRuSource(animeId, episodeId, document);
            case ANIMEDIA:
                return convertAnimediaSource(animeId, episodeId, document);
            case SMOTRET_ANIME:
                return convertSmotretAnimeSource(animeId, episodeId, document);
            case SOVET_ROMANTICA:
                return convertSovetRomanticaSource(animeId, episodeId, document);
            default:
                return convertUnknownSource(animeId, episodeId);
        }
    }

    private PlayVideo convertUnknownSource(long animeId, int episodeId) {
        throw new NoSuchElementException();
    }

    private PlayVideo convertSovetRomanticaSource(long animeId, int episodeId, Document document) {
        throw new NoSuchElementException();
    }

    private PlayVideo convertSmotretAnimeSource(long animeId, int episodeId, Document document) {
        throw new NoSuchElementException();
    }

    private PlayVideo convertAnimediaSource(long animeId, int episodeId, Document document) {
        throw new NoSuchElementException();
    }

    private PlayVideo convertMailRuSource(long animeId, int episodeId, Document document) {
        throw new NoSuchElementException();
    }

    private PlayVideo convertRutubeSource(long animeId, int episodeId, Document document) {
        throw new NoSuchElementException();
    }

    private PlayVideo convertMyViSource(long animeId, int episodeId, Document document) {
        throw new NoSuchElementException();
    }

    private PlayVideo convertOkSource(long animeId, int episodeId, Document document) {
        throw new NoSuchElementException();
    }

    private PlayVideo convertYoutubeSource(long animeId, int episodeId, Document document) {
        throw new NoSuchElementException();
    }
}
