package com.gnoemes.shikimoriapp.domain.app;

import com.gnoemes.shikimoriapp.data.repository.app.FirebaseAnalyticsRepository;
import com.gnoemes.shikimoriapp.entity.app.domain.AnalyticsEvent;

import javax.inject.Inject;

public class AnalyticsInteractorImpl implements AnalyticsInteractor {

    private FirebaseAnalyticsRepository repository;

    @Inject
    public AnalyticsInteractorImpl(FirebaseAnalyticsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void logEvent(AnalyticsEvent event) {
        repository.logEvent(event);
    }
}
