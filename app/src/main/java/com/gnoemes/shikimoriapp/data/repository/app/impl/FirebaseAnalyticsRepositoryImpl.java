package com.gnoemes.shikimoriapp.data.repository.app.impl;

import android.content.Context;
import android.os.Bundle;

import com.gnoemes.shikimoriapp.data.repository.app.FirebaseAnalyticsRepository;
import com.gnoemes.shikimoriapp.entity.app.domain.AnalyticsEvent;
import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Inject;

public class FirebaseAnalyticsRepositoryImpl implements FirebaseAnalyticsRepository {

    private FirebaseAnalytics analytics;

    @Inject
    public FirebaseAnalyticsRepositoryImpl(Context context) {
        analytics = FirebaseAnalytics.getInstance(context);
    }

    @Override
    public void logEvent(AnalyticsEvent event) {
        Bundle bundle = new Bundle();

        analytics.logEvent(event.getEvent(), bundle);
    }
}
