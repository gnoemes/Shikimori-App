package com.gnoemes.shikimoriapp.data.local.db.tables;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio3.sqlite.queries.Query;

public class EpisodeTable {
    @NonNull
    public static final String TABLE = "episodes";

    @NonNull
    public static final String COLUMN_ID = "_id";

    @NonNull
    public static final String COLUMN_ANIME_ID = "_anime_id";

    @NonNull
    public static final String COLUMN_WATCHED = "watched";

    @NonNull
    public static final Query QUERY_ALL = Query.builder()
            .table(TABLE)
            .build();

    @NonNull
    public static String getCreateTableQuery() {
        return "CREATE TABLE " + TABLE + "("
                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, "
                + COLUMN_ANIME_ID + " INTEGER NOT NULL, "
                + COLUMN_WATCHED + " INTEGER "
                + ");";
    }

    @NonNull
    public static String dropTableQuery() {
        return "DROP TABLE IF EXISTS " + TABLE;
    }
}
