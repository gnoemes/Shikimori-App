package com.gnoemes.shikimoriapp.presentation.view.anime.provider;

import android.content.Context;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.rates.domain.RateStatus;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class RateResourceProviderImpl implements RateResourceProvider {

    private Context context;

    @Inject
    public RateResourceProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public List<String> getAnimeRateStasuses() {
        return Arrays.asList(context.getResources().getStringArray(R.array.anime_rate_stasuses));
    }

    @Override
    public List<String> getMangaRateStasuses() {
        return Arrays.asList(context.getResources().getStringArray(R.array.manga_rate_stasuses));
    }

    @Override
    public List<String> getAnimeRatesWithCount() {
        return Arrays.asList(context.getResources().getStringArray(R.array.anime_rate_stasuses_with_count));
    }

    @Override
    public List<String> getMangaRatesWithCount() {
        return Arrays.asList(context.getResources().getStringArray(R.array.manga_rate_stasuses_with_count));
    }

    @Override
    public String getLocalizedStatus(Type type, RateStatus status) {
        switch (type) {
            case ANIME: {
                switch (status) {
                    case WATCHING:
                        return context.getString(R.string.rate_watching);
                    case REWATCHING:
                        return context.getString(R.string.rate_rewatch);
                    case DROPPED:
                        return context.getString(R.string.rate_dropped);
                    case ON_HOLD:
                        return context.getString(R.string.rate_on_hold);
                    case PLANNED:
                        return context.getString(R.string.rate_planned);
                    case COMPLETED:
                        return context.getString(R.string.rate_completed);
                    default:
                        return context.getString(R.string.no_rate);

                }
            }
            case MANGA:
            case RANOBE: {
                switch (status) {
                    case WATCHING:
                        return context.getString(R.string.rate_reading);
                    case REWATCHING:
                        return context.getString(R.string.rate_rereading);
                    case DROPPED:
                        return context.getString(R.string.rate_dropped);
                    case ON_HOLD:
                        return context.getString(R.string.rate_on_hold);
                    case PLANNED:
                        return context.getString(R.string.rate_planned);
                    case COMPLETED:
                        return context.getString(R.string.rate_readed);
                    default:
                        return context.getString(R.string.no_rate);
                }
            }
            default:
                return context.getString(R.string.no_rate);
        }
    }
}
