package com.gnoemes.shikimoriapp.entity.anime.series.data.db;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.db.tables.EpisodeTable;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteType;

@StorIOSQLiteType(table = EpisodeTable.TABLE)
public class EpisodeDAO {

    @StorIOSQLiteColumn(name = EpisodeTable.COLUMN_ID, key = true)
    Long id;

    @StorIOSQLiteColumn(name = EpisodeTable.COLUMN_SERIES_ID)
    Long seriesId;

    @StorIOSQLiteColumn(name = EpisodeTable.COLUMN_ANIME_ID)
    Long animeId;

    @StorIOSQLiteColumn(name = EpisodeTable.COLUMN_EPISODE_FULL)
    String episodeFull;

    @StorIOSQLiteColumn(name = EpisodeTable.COLUMN_EPISODE)
    Float episode;

    @StorIOSQLiteColumn(name = EpisodeTable.COLUMN_TYPE)
    String type;

    @StorIOSQLiteColumn(name = EpisodeTable.COLUMN_VIEWS)
    Long views;

    @StorIOSQLiteColumn(name = EpisodeTable.COLUMN_WATCHED)
    Integer watched;

    public EpisodeDAO() {
    }

    private EpisodeDAO(Long id, Long seriesId, Long animeId, String episodeFull, Float episode,
                       String type, Long views, Integer watched) {
        this.id = id;
        this.seriesId = seriesId;
        this.animeId = animeId;
        this.episodeFull = episodeFull;
        this.episode = episode;
        this.type = type;
        this.views = views;
        this.watched = watched;
    }

    @NonNull
    public static EpisodeDAO newEpisode(Long id, Long seriesId, Long animeId,
                                        String episodeFull, Float episode, String type,
                                        Long views, boolean watched) {
        return new EpisodeDAO(id, seriesId, animeId, episodeFull, episode, type, views, watched ? 1 : 0);
    }

    public Long getId() {
        return id;
    }

    public Long getSeriesId() {
        return seriesId;
    }

    public Long getAnimeId() {
        return animeId;
    }

    public Float getEpisode() {
        return episode;
    }

    public String getType() {
        return type;
    }

    public Long getViews() {
        return views;
    }

    public Integer getWatched() {
        return watched;
    }

    public String getEpisodeFull() {
        return episodeFull;
    }
}
