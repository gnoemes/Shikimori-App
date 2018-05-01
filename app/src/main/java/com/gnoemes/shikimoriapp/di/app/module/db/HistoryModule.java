package com.gnoemes.shikimoriapp.di.app.module.db;

import com.gnoemes.shikimoriapp.data.local.db.HistoryDbSource;
import com.gnoemes.shikimoriapp.data.local.db.impl.HistoryDbSourceImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface HistoryModule {

    @Binds
    HistoryDbSource bindHistoryDbSource(HistoryDbSourceImpl source);
}
