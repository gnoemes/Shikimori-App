package com.gnoemes.shikimoriapp.presentation.view.calendar.adapter.provider;

import android.content.Context;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.BaseAnimeResourceProvider;

import javax.inject.Inject;

public class CalendarAnimeResourceProviderImpl extends BaseAnimeResourceProvider implements CalendarAnimeResourceProvider {

    private Context context;

    @Inject
    public CalendarAnimeResourceProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public String getEpisodeInString() {
        return context.getString(R.string.calendar_episode_in);
    }

    @Override
    protected Context getContext() {
        return context;
    }
}
