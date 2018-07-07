package com.gnoemes.shikimoriapp.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.local.db.tables.EpisodeTable;
import com.gnoemes.shikimoriapp.data.local.db.tables.HistoryTable;
import com.gnoemes.shikimoriapp.data.local.db.tables.RateSyncTable;

public class DbOpenHelper extends SQLiteOpenHelper {

    public DbOpenHelper(@NonNull Context context) {
        super(context, "shikimori_db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EpisodeTable.getCreateTableQuery());
        db.execSQL(HistoryTable.getCreateTableQuery());
        db.execSQL(RateSyncTable.getCreateTableQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion <= 2) {
            db.execSQL(EpisodeTable.dropTableQuery());
            db.execSQL(EpisodeTable.getCreateTableQuery());
            db.execSQL(HistoryTable.dropTableQuery());
            db.execSQL(HistoryTable.getCreateTableQuery());
            db.execSQL(RateSyncTable.dropTableQuery());
            db.execSQL(RateSyncTable.getCreateTableQuery());
        }
    }
}
