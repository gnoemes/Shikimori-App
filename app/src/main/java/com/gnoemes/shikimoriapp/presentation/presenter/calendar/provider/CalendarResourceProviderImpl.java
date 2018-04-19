package com.gnoemes.shikimoriapp.presentation.presenter.calendar.provider;

import android.content.Context;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.di.main.scope.ActivityContext;

import javax.inject.Inject;

public class CalendarResourceProviderImpl implements CalendarResourceProvider {

    private Context context;

    @Inject
    public CalendarResourceProviderImpl(@ActivityContext Context context) {
        this.context = context;
    }

    @Override
    public String getTitle() {
        return context.getString(R.string.calendar_ongoings);
    }
}
