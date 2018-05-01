package com.gnoemes.shikimoriapp.entity.anime.series.data.db;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.local.db.tables.HistoryTable;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteType;

@StorIOSQLiteType(table = HistoryTable.TABLE)
public class HistoryDao {

    @StorIOSQLiteColumn(name = HistoryTable.COLUMN_ID, key = true)
    Long id;

    @StorIOSQLiteColumn(name = HistoryTable.COLUMN_ANIME_ID)
    Long animeId;

    @StorIOSQLiteColumn(name = HistoryTable.COLUMN_EPISODE)
    Long episodeId;

    public HistoryDao() {
    }

    private HistoryDao(Long animeId, Long episodeId) {
        this.animeId = animeId;
        this.episodeId = episodeId;
    }

    @NonNull
    public static HistoryDao newEpisode(Long animeId, Long episodeId) {
        return new HistoryDao(animeId, episodeId);
    }

    public Long getId() {
        return id;
    }

    public Long getAnimeId() {
        return animeId;
    }

    public Long getEpisodeId() {
        return episodeId;
    }
}
