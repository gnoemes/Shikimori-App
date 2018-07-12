package com.gnoemes.shikimoriapp.data.local.db.impl;

import android.util.Log;

import com.gnoemes.shikimoriapp.data.local.db.RateSyncDbSource;
import com.gnoemes.shikimoriapp.data.local.db.tables.RateSyncTable;
import com.gnoemes.shikimoriapp.entity.rates.data.RateSyncDao;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.pushtorefresh.storio3.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio3.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio3.sqlite.queries.Query;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class RateSyncDbSourceImpl implements RateSyncDbSource {

    private StorIOSQLite storIOSQLite;

    @Inject
    public RateSyncDbSourceImpl(StorIOSQLite storIOSQLite) {
        this.storIOSQLite = storIOSQLite;
    }

    @Override
    public Completable saveRateEpisodes(long rateId, long animeId, int episodes) {
        return Completable.fromAction(() -> {
            RateSyncDao dao = RateSyncDao.newSync(rateId, animeId, episodes);
            PutResult putResult = storIOSQLite
                    .put()
                    .object(dao)
                    .prepare()
                    .executeAsBlocking();
            Log.i("DEVE", "saveRateEpisodes: " + putResult.numberOfRowsUpdated());
        });
    }

    @Override
    public Single<Integer> getRateEpisodes(long animeId) {
        return storIOSQLite
                .get()
                .object(RateSyncDao.class)
                .withQuery(Query.builder()
                        .table(RateSyncTable.TABLE)
                        .where(RateSyncTable.COLUMN_ANIME_ID + " = ?")
                        .whereArgs(animeId)
                        .build())
                .prepare()
                .asRxSingle()
                .map(rateSyncDaoOptional -> {
                    if (rateSyncDaoOptional.isPresent()) {
                        return rateSyncDaoOptional.get().getEpisodes();
                    }
                    return 0;
                });
    }

    @Override
    public Completable clearHistory(long animeId) {
        return Completable.fromAction(() -> {
            DeleteResult deleteResult = storIOSQLite
                    .delete()
                    .byQuery(DeleteQuery.builder()
                            .table(RateSyncTable.TABLE)
                            .where(RateSyncTable.COLUMN_ANIME_ID + " = ?")
                            .whereArgs(animeId)
                            .build())
                    .prepare()
                    .executeAsBlocking();
            Log.i("DEVE", "clearHistoryRate: " + deleteResult.numberOfRowsDeleted());
        });
    }
}
