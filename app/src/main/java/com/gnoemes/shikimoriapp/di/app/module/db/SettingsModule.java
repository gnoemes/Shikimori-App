package com.gnoemes.shikimoriapp.di.app.module.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.gnoemes.shikimoriapp.data.local.preferences.UserPreferenceSource;
import com.gnoemes.shikimoriapp.data.local.preferences.UserPreferenceSourceImpl;
import com.gnoemes.shikimoriapp.data.repository.app.UserSettingsRepository;
import com.gnoemes.shikimoriapp.data.repository.app.impl.UserSettingsRepositoryImpl;
import com.gnoemes.shikimoriapp.di.app.qualifiers.SettingsQualifier;
import com.gnoemes.shikimoriapp.domain.app.UserSettingsInteractor;
import com.gnoemes.shikimoriapp.domain.app.UserSettingsInteractorImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public interface SettingsModule {

    @Provides
    @SettingsQualifier
    @Singleton
    static SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Binds
    @Singleton
    UserPreferenceSource bindUserPreferenceSource(UserPreferenceSourceImpl source);

    @Binds
    @Singleton
    UserSettingsRepository bindUserSettingsRepository(UserSettingsRepositoryImpl repository);

    @Binds
    UserSettingsInteractor bindUserSettingsInteractor(UserSettingsInteractorImpl settingsInteractor);
}
