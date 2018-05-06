package com.gnoemes.shikimoriapp.data.local.db.impl;

import com.gnoemes.shikimoriapp.data.local.db.HistoryDbSource;
import com.gnoemes.shikimoriapp.data.local.db.tables.HistoryTable;
import com.gnoemes.shikimoriapp.entity.anime.series.data.db.HistoryDao;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.pushtorefresh.storio3.sqlite.queries.Query;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class HistoryDbSourceImpl implements HistoryDbSource {

    private StorIOSQLite storIOSQLite;

    @Inject
    public HistoryDbSourceImpl(StorIOSQLite storIOSQLite) {
        this.storIOSQLite = storIOSQLite;
    }

    @Override
    public Completable episodeWatched(long animeId, long episodeId) {
        return storIOSQLite
                .put()
                .object(HistoryDao.newEpisode(animeId, episodeId))
                .prepare()
                .asRxCompletable();
    }

    @Override
    public Single<Boolean> isEpisodeWatched(long episodeId) {
        return Single.fromCallable(() -> storIOSQLite
                .get()
                .numberOfResults()
                .withQuery(Query.builder()
                        .table(HistoryTable.TABLE)
                        .where(HistoryTable.COLUMN_EPISODE + " = ?")
                        .whereArgs(episodeId)
                        .build())
                .prepare()
                .executeAsBlocking()).map(integer -> integer != null && integer != 0);
    }
}