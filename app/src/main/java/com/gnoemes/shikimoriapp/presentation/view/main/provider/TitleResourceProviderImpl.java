package com.gnoemes.shikimoriapp.presentation.view.main.provider;

import android.content.Context;

import com.gnoemes.shikimoriapp.R;

import javax.inject.Inject;

public class TitleResourceProviderImpl implements TitleResourceProvider {

    private Context context;

    @Inject
    public TitleResourceProviderImpl(Context context) {
        this.context = context;
    }


    @Override
    public String getCalendarTitle() {
        return context.getString(R.string.calendar_ongoings);
    }

    @Override
    public String getSearchTitle() {
        return context.getString(R.string.common_search);
    }

    @Override
    public String getSeriesTitle() {
        return context.getString(R.string.series_title);
    }
}
