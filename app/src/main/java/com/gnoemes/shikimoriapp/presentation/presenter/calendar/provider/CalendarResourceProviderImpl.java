package com.gnoemes.shikimoriapp.presentation.presenter.calendar.provider;

import android.content.Context;

import com.gnoemes.shikimoriapp.R;

import javax.inject.Inject;

public class CalendarResourceProviderImpl implements CalendarResourceProvider {

    private Context context;

    @Inject
    public CalendarResourceProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public String getTitle() {
        return context.getString(R.string.calendar_ongoings);
    }
}
