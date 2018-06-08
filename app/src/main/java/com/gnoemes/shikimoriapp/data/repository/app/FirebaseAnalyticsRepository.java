package com.gnoemes.shikimoriapp.data.repository.app;

import com.gnoemes.shikimoriapp.entity.app.domain.AnalyticsEvent;

public interface FirebaseAnalyticsRepository {

    void logEvent(AnalyticsEvent event);
}
