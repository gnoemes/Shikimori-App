package com.gnoemes.shikimoriapp.utils.date.provider;

import android.content.Context;

import com.gnoemes.shikimoriapp.R;

import javax.inject.Inject;

public class DateTimeResourceProviderImpl implements DateTimeResourceProvider {

    private Context context;

    @Inject
    public DateTimeResourceProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public String getTodayMessage() {
        return context.getString(R.string.common_today);
    }

    @Override
    public String getTomorrowMessage() {
        return context.getString(R.string.common_tomorrow);
    }

    @Override
    public String getCalendarDateFormat() {
        return context.getString(R.string.calendar_date_format);
    }

}