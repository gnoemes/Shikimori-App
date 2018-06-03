package com.gnoemes.shikimoriapp.domain.app;

import com.gnoemes.shikimoriapp.entity.app.domain.AnalyticsEvent;

public interface AnalyticsInteractor {

    void logEvent(AnalyticsEvent event);
}
