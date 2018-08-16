package com.gnoemes.shikimoriapp.data.repository.series.converters.hostings.impl;

import android.util.Log;

import com.gnoemes.shikimoriapp.data.repository.series.converters.hostings.VkVideoResponseConverter;
import com.gnoemes.shikimoriapp.entity.main.domain.Constants;
import com.gnoemes.shikimoriapp.entity.series.domain.PlayVideo;
import com.gnoemes.shikimoriapp.entity.series.domain.VideoFormat;
import com.gnoemes.shikimoriapp.entity.series.domain.VideoHosting;
import com.gnoemes.shikimoriapp.entity.series.domain.VideoTrack;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

public class VkVideoResponseConverterImpl implements VkVideoResponseConverter {

    @Inject
    public VkVideoResponseConverterImpl() {
    }

    @Override
    public PlayVideo convertResponse(long animeId, int episodeId, String title, Document document) {
        String QUALITIES_QUERY = "video#video_player>source[type=video/mp4]";
        List<VideoTrack> tracks = new ArrayList<>();
        Pattern resolutionPattern = Pattern.compile("\\.(\\d+)\\.");

        for (Element e : document.select(QUALITIES_QUERY)) {
            String src = e.attr("src");
            Matcher matcher = resolutionPattern.matcher(src);
            int res = matcher.find()
                    ? Integer.parseInt(src.substring(matcher.start(), matcher.end()).replaceAll("\\.", ""))
                    : (int) Constants.NO_ID;
            tracks.add(new VideoTrack(res, src, VideoFormat.MP4));
            Log.i("VideoSourceConverter", "vkConverter src: " + src + "\nvkConverter res: " + res);
        }

        return new PlayVideo(animeId, episodeId, VideoHosting.VK, title, tracks);
    }

}
