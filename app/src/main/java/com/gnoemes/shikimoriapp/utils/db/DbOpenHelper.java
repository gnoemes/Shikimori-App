package com.gnoemes.shikimoriapp.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.data.db.tables.EpisodeTable;

public class DbOpenHelper extends SQLiteOpenHelper {

    public DbOpenHelper(@NonNull Context context) {
        super(context, "shikimori_db", null, 11);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EpisodeTable.getCreateTableQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
