package com.gnoemes.shikimoriapp.entity.rates.data;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.local.db.tables.RateSyncTable;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteType;

@StorIOSQLiteType(table = RateSyncTable.TABLE)
public class RateSyncDao {

    @StorIOSQLiteColumn(name = RateSyncTable.COLUMN_RATE_ID, key = true)
    Long rateId;

    @StorIOSQLiteColumn(name = RateSyncTable.COLUMN_ANIME_ID)
    Long animeId;

    @StorIOSQLiteColumn(name = RateSyncTable.COLUMN_EPISODES)
    Integer episodes;

    public RateSyncDao() {
    }

    private RateSyncDao(Long rateId, Long animeId, Integer episodes) {
        this.rateId = rateId;
        this.animeId = animeId;
        this.episodes = episodes;
    }

    @NonNull
    public static RateSyncDao newSync(Long rateId, Long animeId, Integer episodes) {
        return new RateSyncDao(rateId, animeId, episodes);
    }

    public Long getRateId() {
        return rateId;
    }

    public Long getAnimeId() {
        return animeId;
    }

    public Integer getEpisodes() {
        return episodes;
    }
}
