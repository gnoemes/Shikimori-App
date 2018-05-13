package com.gnoemes.shikimoriapp.data.local.db.tables;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio3.sqlite.queries.Query;

public class RateSyncTable {
    @NonNull
    public static final String TABLE = "rate_sync";

    @NonNull
    public static final String COLUMN_RATE_ID = "_rate_id";

    @NonNull
    public static final String COLUMN_ANIME_ID = "_anime_id";

    @NonNull
    public static final String COLUMN_EPISODES = "episodes";

    @NonNull
    public static final Query QUERY_ALL = Query.builder()
            .table(TABLE)
            .build();

    @NonNull
    public static String getCreateTableQuery() {
        return "CREATE TABLE " + TABLE + "("
                + COLUMN_RATE_ID + " INTEGER NOT NULL PRIMARY KEY, "
                + COLUMN_ANIME_ID + " INTEGER NOT NULL, "
                + COLUMN_EPISODES + " INTEGER NOT NULL"
                + ");";
    }

    @NonNull
    public static String dropTableQuery() {
        return "DROP TABLE IF EXISTS " + TABLE;
    }
}
