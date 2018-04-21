package com.gnoemes.shikimoriapp.presentation.view.calendar.adapter.provider;

import android.content.Context;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeType;

import javax.inject.Inject;

public class CalendarAnimeResourceProviderImpl implements CalendarAnimeResourceProvider {

    private Context context;

    @Inject
    public CalendarAnimeResourceProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public String getEpisodeStringFormat() {
        return context.getString(R.string.calendar_episodes_format);
    }

    @Override
    public String getEpisodeInString() {
        return context.getString(R.string.calendar_episode_in);
    }

    @Override
    public int getColorByAnimeType(AnimeType type) {
        switch (type) {
            case TV:
                return R.color.light_teal;
            case OVA:
                return R.color.bittersweet;
            case ONA:
                return R.color.light_cyan;
            case MUSIC:
                return R.color.pink;
            case MOVIE:
                return R.color.red;
            case SPECIAL:
                return R.color.brown;
            default:
                return 0;
        }
    }
}
