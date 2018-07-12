package com.gnoemes.shikimoriapp.data.local.db.impl;

import android.util.Log;

import com.gnoemes.shikimoriapp.data.local.db.EpisodeDbSource;
import com.gnoemes.shikimoriapp.data.local.db.converters.EpisodeDAOConverter;
import com.gnoemes.shikimoriapp.data.local.db.tables.EpisodeTable;
import com.gnoemes.shikimoriapp.entity.anime.series.data.db.EpisodeDAO;
import com.gnoemes.shikimoriapp.entity.series.domain.Episode;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResults;
import com.pushtorefresh.storio3.sqlite.queries.Query;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class EpisodeDbSourceImpl implements EpisodeDbSource {

    private StorIOSQLite storIOSQLite;
    private EpisodeDAOConverter converter;

    @Inject
    public EpisodeDbSourceImpl(StorIOSQLite storIOSQLite, EpisodeDAOConverter converter) {
        this.storIOSQLite = storIOSQLite;
        this.converter = converter;
    }


    @Override
    public Completable saveEpisodes(List<Episode> episodes) {
        return Completable.fromAction(() -> {
            List<EpisodeDAO> daos = converter.convertTo(episodes);
            PutResults<EpisodeDAO> putResults = storIOSQLite.put()
                    .objects(daos)
                    .prepare()
                    .executeAsBlocking();
            Log.i("DEVE", "saveEpisodes: " + putResults.toString());
        });
    }

    @Override
    public Single<List<Episode>> getEpisodes(long animeId) {
        return Single.fromCallable(() -> storIOSQLite.get()
                .listOfObjects(EpisodeDAO.class)
                .withQuery(Query.builder()
                        .table(EpisodeTable.TABLE)
                        .where(EpisodeTable.COLUMN_ANIME_ID + " = ?")
                        .whereArgs(animeId)
                        .build())
                .prepare()
                .executeAsBlocking())
                .map(episodeDAOS -> {
                    if (episodeDAOS.isEmpty()) {
                        throw new IllegalStateException();
                    }
                    return episodeDAOS;
                })
                .map(daoList -> converter.convertFrom(daoList));
    }

    @Override
    public Completable setEpisodeWatched(int episodeId) {
        return Completable.fromAction(() -> {
            EpisodeDAO dao = EpisodeDAO.newEpisode(episodeId, null, true);
            storIOSQLite.put()
                    .object(dao)
                    .prepare()
                    .executeAsBlocking();
        });
    }
}
