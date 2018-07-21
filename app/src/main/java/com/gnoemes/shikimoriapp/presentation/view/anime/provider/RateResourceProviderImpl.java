package com.gnoemes.shikimoriapp.presentation.view.anime.provider;

import android.content.Context;

import com.gnoemes.shikimoriapp.R;
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
    public List<String> getRateStasuses() {
        return Arrays.asList(context.getResources().getStringArray(R.array.rate_stasuses));
    }

    @Override
    public List<String> getRatesWithCount() {
        return Arrays.asList(context.getResources().getStringArray(R.array.rate_stasuses_with_count));
    }

    @Override
    public String getLocalizedStatus(RateStatus status) {
        switch (status) {
            case WATCHING:
                return context.getString(R.string.rate_watching);
            case REWATCHING:
                return context.getString(R.string.rate_rewatch);
            case FAVORITE:
                return context.getString(R.string.common_favorite);
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
}
