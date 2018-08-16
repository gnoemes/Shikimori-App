package com.gnoemes.shikimoriapp.data.repository.series.converters.hostings.impl;

import com.gnoemes.shikimoriapp.data.repository.series.converters.hostings.SibnetVideoResponseConverter;
import com.gnoemes.shikimoriapp.entity.main.domain.Constants;
import com.gnoemes.shikimoriapp.entity.series.data.SibnetVideoResponse;
import com.gnoemes.shikimoriapp.entity.series.domain.PlayVideo;
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

public class SibnetVideoResponseConverterImpl implements SibnetVideoResponseConverter {

    @Inject
    public SibnetVideoResponseConverterImpl() {
    }

    @Override
    public PlayVideo convertResponse(long animeId, int episodeId, String title, Document document) {
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

    @Override
    public String convertDashToMp4Link(String url) {
        return url.replace("/manifest.mpd", ".mp4");
    }
}
