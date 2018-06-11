package com.gnoemes.shikimoriapp.presentation.view.main.provider;

import android.content.Context;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeType;

public abstract class BaseAnimeResourceProvider implements AnimeResourceProvider {


    protected abstract Context getContext();

    @Override
    public String getEpisodeStringFormat() {
        return getContext().getString(R.string.calendar_episodes_format);
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

    @Override
    public String getLocalizedType(AnimeType type) {
        switch (type) {
            case TV:
                return getContext().getString(R.string.type_tv_translatable);
            case OVA:
                return getContext().getString(R.string.type_ova);
            case ONA:
                return getContext().getString(R.string.type_ona);
            case MUSIC:
                return getContext().getString(R.string.type_music_translatable);
            case MOVIE:
                return getContext().getString(R.string.type_movie_translatable);
            case SPECIAL:
                return getContext().getString(R.string.type_special_translatable);
            default:
                return null;
        }
    }
}
