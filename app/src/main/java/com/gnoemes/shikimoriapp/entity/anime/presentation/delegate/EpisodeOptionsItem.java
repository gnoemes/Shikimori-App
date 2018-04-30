package com.gnoemes.shikimoriapp.entity.anime.presentation.delegate;

public class EpisodeOptionsItem extends BaseEpisodeItem {

    private long episodeId;
    private boolean isFirst;
    private String episode;

    public EpisodeOptionsItem(long episodeId, boolean isFirst, String episode) {
        this.episodeId = episodeId;
        this.isFirst = isFirst;
        this.episode = episode;
    }

    public long getEpisodeId() {
        return episodeId;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public String getEpisode() {
        return episode;
    }
}
