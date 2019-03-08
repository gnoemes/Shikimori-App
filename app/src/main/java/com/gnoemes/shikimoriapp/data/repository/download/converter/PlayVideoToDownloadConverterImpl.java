package com.gnoemes.shikimoriapp.data.repository.download.converter;

import android.content.Context;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.series.domain.PlayVideo;
import com.gnoemes.shikimoriapp.entity.video.domain.DownloadVideo;

import javax.inject.Inject;

public class PlayVideoToDownloadConverterImpl implements PlayVideoToDownloadConverter {

    private Context context;

    @Inject
    public PlayVideoToDownloadConverterImpl(Context context) {
        this.context = context;
    }

    @Override
    public DownloadVideo apply(PlayVideo video) {
        if (video.getTracks() == null || video.getTracks().isEmpty()) {
            return new DownloadVideo(null, null, null);
        }

        String fileName = String.format(context.getString(R.string.episode_list_format), video.getEpisodeId());
        return new DownloadVideo(fileName, video.getTitle(), video.getTracks().get(0).getUrl());
    }
}
