package com.gnoemes.shikimoriapp.di.app.module.db;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.entity.anime.series.data.db.EpisodeDAO;
import com.gnoemes.shikimoriapp.entity.anime.series.data.db.EpisodeDAOSQLiteTypeMapping;
import com.gnoemes.shikimoriapp.entity.anime.series.data.db.HistoryDao;
import com.gnoemes.shikimoriapp.entity.anime.series.data.db.HistoryDaoSQLiteTypeMapping;
import com.gnoemes.shikimoriapp.entity.rates.data.RateSyncDao;
import com.gnoemes.shikimoriapp.entity.rates.data.RateSyncDaoSQLiteTypeMapping;
import com.gnoemes.shikimoriapp.utils.db.DbOpenHelper;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.pushtorefresh.storio3.sqlite.impl.DefaultStorIOSQLite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public interface DbModule {

    @Provides
    @Singleton
    static StorIOSQLite provideStorIosqLite(@NonNull SQLiteOpenHelper sqLiteOpenHelper) {
        return DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(sqLiteOpenHelper)
                .addTypeMapping(EpisodeDAO.class, new EpisodeDAOSQLiteTypeMapping())
                .addTypeMapping(HistoryDao.class, new HistoryDaoSQLiteTypeMapping())
                .addTypeMapping(RateSyncDao.class, new RateSyncDaoSQLiteTypeMapping())
                .build();
    }

    @Provides
    @Singleton
    static SQLiteOpenHelper provideSqLiteOpenHelper(Context context) {
        return new DbOpenHelper(context);
    }
}
