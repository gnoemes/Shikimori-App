package com.gnoemes.shikimoriapp.data.repository.series.converters.impl;

import com.gnoemes.shikimoriapp.data.repository.series.converters.PlayVideoResponseConverter;
import com.gnoemes.shikimoriapp.entity.main.presentation.Constants;
import com.gnoemes.shikimoriapp.entity.series.data.SibnetVideoResponse;
import com.gnoemes.shikimoriapp.entity.series.domain.PlayVideo;
import com.gnoemes.shikimoriapp.entity.series.domain.VideoFormat;
import com.gnoemes.shikimoriapp.entity.series.domain.VideoHosting;
import com.gnoemes.shikimoriapp.entity.series.domain.VideoTrack;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;

public class PlayVideoResponseConverterImpl implements PlayVideoResponseConverter {

    private static final String URL_QUERY = "div.video-link a";
    private static final String HREF_QUERY = "href";
    private static final String TITLE_QUERY = "a.b-link>span[itemprop]";

    private static final String SMOTRET_ANIME_REGEX = "https?://smotret-anime\\.ru/";
    private static final String SIBNET_REGEX = "https?://video\\.sibnet\\.ru/";
    private static final String VK_REGEX = "https?://vk\\.com/";
    private static final String YOUTUBE_REGEX = "https?://(?:www\\.)?youtube\\.com/";
    private static final String RUTUBE_REGEX = "https?://rutube\\.ru/";
    private static final String OK_REGEX = "https?://ok\\.ru/";
    private static final String SOVET_ROMANTICA_REGEX = "https?://sovetromantica\\.com/";
    private static final String ANIMEDIA_REGEX = "https?://online\\.animedia\\.tv/";
    private static final String MAIL_RU = "https?://mail\\.ru/";

    @Inject
    public PlayVideoResponseConverterImpl() {
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
    public PlayVideo convertDependsOnHosting(long animeId, int episodeId, VideoHosting hosting, String title, Document document) {
        switch (hosting) {
            case YOUTUBE:
                return convertYoutubeSource(animeId, episodeId, document);
            case OK:
                return convertOkSource(animeId, episodeId, document);
            case VK:
                return convertVkSource(animeId, episodeId, title, document);
            case MY_VI:
                return convertMyViSource(animeId, episodeId, document);
            case RUTUBE:
                return convertRutubeSource(animeId, episodeId, document);
            case SIBNET:
                return convertSibnetSource(animeId, episodeId, title, document);
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
        return null;
    }

    private PlayVideo convertSovetRomanticaSource(long animeId, int episodeId, Document document) {
        return null;
    }

    private PlayVideo convertSmotretAnimeSource(long animeId, int episodeId, Document document) {
        return null;
    }

    private PlayVideo convertAnimediaSource(long animeId, int episodeId, Document document) {
        return null;
    }

    private PlayVideo convertMailRuSource(long animeId, int episodeId, Document document) {
        return null;
    }

    private PlayVideo convertSibnetSource(long animeId, int episodeId, String title, Document document) {
        Elements scripts = document.getElementsByTag("script");
        List<VideoTrack> tracks = new ArrayList<>();
        String json = null;


        for (Element script : scripts) {
            if (Pattern.compile("(?:player.src)(.+?)(?:,)").matcher(script.data()).find()) {
                String src = script.data().substring(script.data().indexOf("player.src"));
                json = src.substring(src.indexOf("["), src.indexOf("]") + 1);
            }
        }

        List<SibnetVideoResponse> videoResponses = new Gson().fromJson(json, new TypeToken<List<SibnetVideoResponse>>() {
        }.getType());

        if (videoResponses != null) {
            for (SibnetVideoResponse response : videoResponses) {
                if (response != null) {
                    tracks.add(new VideoTrack((int) Constants.NO_ID, "http://video.sibnet.ru/" + response.getUrl(), convertVideoFormat(response.getType())));
                }
            }
        }

        return new PlayVideo(animeId, episodeId, VideoHosting.SIBNET, title, tracks);
    }

    private VideoFormat convertVideoFormat(String type) {
        for (VideoFormat format : VideoFormat.values()) {
            if (format.isEqualType(type)) {
                return format;
            }
        }
        return null;
    }

    private PlayVideo convertRutubeSource(long animeId, int episodeId, Document document) {
        return null;
    }

    private PlayVideo convertMyViSource(long animeId, int episodeId, Document document) {
        return null;
    }

    private PlayVideo convertVkSource(long animeId, int episodeId, String title, Document document) {
        String QUALITIES_QUERY = "video#video_player>source~[type=video/mp4]";
        List<VideoTrack> tracks = new ArrayList<>();
        Pattern resolutionPattern = Pattern.compile("\\.(\\d+)\\.");

        for (Element e : document.select(QUALITIES_QUERY)) {
            String src = e.attr("src");
//            int res = resolutionPattern.matcher(src).find() ? Integer.parseInt(resolutionPattern.matcher(src).group().replaceAll("\\.", "")) : (int) Constants.NO_ID;
            int res = 720;
            tracks.add(new VideoTrack(res, src, VideoFormat.MP4));
        }

        return new PlayVideo(animeId, episodeId, VideoHosting.VK, title, tracks);
    }

    private PlayVideo convertOkSource(long animeId, int episodeId, Document document) {
        return null;
    }

    private PlayVideo convertYoutubeSource(long animeId, int episodeId, Document document) {
        return null;
    }
}
