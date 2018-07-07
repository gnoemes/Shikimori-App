package com.gnoemes.shikimoriapp.data.repository.series.converters.impl;

import android.text.TextUtils;
import android.util.Log;

import com.gnoemes.shikimoriapp.data.repository.series.converters.SeriesResponseConverter;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.entity.series.domain.Episode;
import com.gnoemes.shikimoriapp.entity.series.domain.Series;
import com.gnoemes.shikimoriapp.entity.series.domain.VideoHosting;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class SeriesResponseConverterImpl implements SeriesResponseConverter {

    private static final String ERRORS_QUERY = "div.b-errors p";
    private static final String EPISODES_QUERY = "div.c-anime_video_episodes>[data-episode],div.b-show_more-more>[data-episode]";
    private static final String EPISODE_ID_QUERY = "data-episode";
    private static final String EPISODE_TRANSLATIONS_QUERY = "episode-kinds";
    private static final String EPISODE_HOSTINGS_QUERY = "episode-hostings";

    @Inject
    public SeriesResponseConverterImpl() {
    }


    @Override
    public Series apply(long animeId, Document doc) {
        List<Episode> episodes = new ArrayList<>();

        Element errorElem = doc.select(ERRORS_QUERY).first();
        String error = null;

        if (errorElem != null) {
            error = errorElem.text();
        }

        Elements episodeElements = doc.select(EPISODES_QUERY);

        for (Element e : episodeElements) {
            episodes.add(convertEpisode(animeId, e));
        }


        return new Series(error, !TextUtils.isEmpty(error), episodes);
    }

    private Episode convertEpisode(long animeId, Element e) {
        int episodeId = Integer.parseInt(e.attr(EPISODE_ID_QUERY));

        String[] translations = e.getElementsByClass(EPISODE_TRANSLATIONS_QUERY)
                .text()
                .replaceAll(" ", "")
                .split(",");

        List<TranslationType> translationTypes = convertTranslations(translations);

        String rawHostings = e.getElementsByClass(EPISODE_HOSTINGS_QUERY)
                .text();

        List<VideoHosting> videoHostings = convertHostings(rawHostings
                .replaceAll(" ", "")
                .split(","));

        Log.i("DEVE", "convertEpisode: id " + episodeId +
                "\n translations" + Arrays.toString(translations)
                + "\nhostings" + videoHostings.toString());

        return new Episode(episodeId, animeId, translationTypes, videoHostings, rawHostings, false);
    }

    private List<VideoHosting> convertHostings(String[] hostings) {
        List<VideoHosting> list = new ArrayList<>();
        for (String s : hostings) {
            if (VideoHosting.ANIMEDIA.isEqualType(s)) {
                list.add(VideoHosting.ANIMEDIA);
            } else if (VideoHosting.OK.isEqualType(s)) {
                list.add(VideoHosting.OK);
            } else if (VideoHosting.SIBNET.isEqualType(s)) {
                list.add(VideoHosting.SIBNET);
            } else if (VideoHosting.RUTUBE.isEqualType(s)) {
                list.add(VideoHosting.RUTUBE);
            } else if (VideoHosting.SOVET_ROMANTICA.isEqualType(s)) {
                list.add(VideoHosting.SOVET_ROMANTICA);
            } else if (VideoHosting.MAIL_RU.isEqualType(s)) {
                list.add(VideoHosting.MAIL_RU);
            } else if (VideoHosting.VK.isEqualType(s)) {
                list.add(VideoHosting.VK);
            } else if (VideoHosting.SMOTRET_ANIME.isEqualType(s)) {
                list.add(VideoHosting.SMOTRET_ANIME);
            } else if (VideoHosting.MY_VI.isEqualType(s)) {
                list.add(VideoHosting.MY_VI);
            }
        }
        return list;
    }

    private List<TranslationType> convertTranslations(String[] translations) {
        List<TranslationType> types = new ArrayList<>();
        for (String s : translations) {
            if (TranslationType.SUB_RU.isEqualType(s)) {
                types.add(TranslationType.SUB_RU);
            } else if (TranslationType.SUB_RU.isEqualType(s)) {
                types.add(TranslationType.SUB_RU);
            } else if (TranslationType.RAW.isEqualType(s)) {
                types.add(TranslationType.RAW);
            } else if (TranslationType.VOICE_RU.isEqualType(s)) {
                types.add(TranslationType.VOICE_RU);
            }
        }
        return types;
    }
}
