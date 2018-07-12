package com.gnoemes.shikimoriapp.entity.anime.series.data.db;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.local.db.tables.EpisodeTable;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteType;

@StorIOSQLiteType(table = EpisodeTable.TABLE)
public class EpisodeDAO {

    @StorIOSQLiteColumn(name = EpisodeTable.COLUMN_ID, key = true)
    Integer id;

    @StorIOSQLiteColumn(name = EpisodeTable.COLUMN_ANIME_ID)
    Long animeId;

    @StorIOSQLiteColumn(name = EpisodeTable.COLUMN_WATCHED)
    Integer watched;

    public EpisodeDAO() {
    }

    private EpisodeDAO(Integer id, Long animeId,
                       Integer watched) {
        this.id = id;
        this.animeId = animeId;
        this.watched = watched;
    }

    @NonNull
    public static EpisodeDAO newEpisode(Integer id, Long animeId, boolean watched) {
        return new EpisodeDAO(id, animeId, watched ? 1 : 0);
    }

    public Integer getId() {
        return id;
    }

    public Long getAnimeId() {
        return animeId;
    }

    public Integer getWatched() {
        return watched;
    }

}
