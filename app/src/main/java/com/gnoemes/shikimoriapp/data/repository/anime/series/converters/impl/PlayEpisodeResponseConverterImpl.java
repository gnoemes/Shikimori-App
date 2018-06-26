package com.gnoemes.shikimoriapp.data.repository.anime.series.converters.impl;

import android.text.TextUtils;
import android.util.Log;

import com.gnoemes.shikimoriapp.BuildConfig;
import com.gnoemes.shikimoriapp.data.repository.anime.series.converters.PlayEpisodeResponseConverter;
import com.gnoemes.shikimoriapp.entity.anime.series.data.network.PlayEpisodeResponse;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.PlayEpisode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PlayEpisodeResponseConverterImpl implements PlayEpisodeResponseConverter {

    private Gson gson;

    @Inject
    public PlayEpisodeResponseConverterImpl(Gson gson) {
        this.gson = gson;
    }

    @Override
    public List<PlayEpisode> convertListOfResponsesWithSubs(String json, String subtitles) {
        List<PlayEpisodeResponse> responses = gson.fromJson(json, new TypeToken<List<PlayEpisodeResponse>>() {
        }.getType());
        List<PlayEpisode> items = new ArrayList<>();

        for (PlayEpisodeResponse response : responses) {
            items.add(convertResponseWithSubs(response, subtitles));
        }

        return items;
    }

    private PlayEpisode convertResponseWithSubs(PlayEpisodeResponse response, String subtitles) {
        Log.i("PLAY_EPISODE_CONVERTER", "subs: " + subtitles + "\n urls: " + response.getUrls().toString());
        return new PlayEpisode(response.getResolution(),
                !TextUtils.isEmpty(subtitles),
                response.getUrls(),
                BuildConfig.SmotretAnimeBaseUrl + subtitles.replaceFirst("/", ""));
    }
}
