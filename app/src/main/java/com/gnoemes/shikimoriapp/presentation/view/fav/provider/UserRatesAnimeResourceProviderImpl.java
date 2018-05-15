package com.gnoemes.shikimoriapp.presentation.view.fav.provider;

import android.content.Context;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.BaseAnimeResourceProvider;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class UserRatesAnimeResourceProviderImpl extends BaseAnimeResourceProvider
        implements UserRatesAnimeResourceProvider {

    private Context context;

    @Inject
    public UserRatesAnimeResourceProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public String getEpisodeRateStringFormat() {
        return getContext().getString(R.string.rate_episodes_format);
    }

    @Override
    public String getWachingString() {
        return getContext().getString(R.string.rate_watching);
    }

    @Override
    public String getPlannedString() {
        return getContext().getString(R.string.rate_planned);
    }

    @Override
    public String getReWatchingString() {
        return getContext().getString(R.string.rate_rewatch);
    }

    @Override
    public String getCompletedString() {
        return getContext().getString(R.string.rate_completed);
    }

    @Override
    public String getOnHoldString() {
        return getContext().getString(R.string.rate_on_hold);
    }

    @Override
    public String getDroppedString() {
        return getContext().getString(R.string.rate_dropped);
    }

    @Override
    public String getFavString() {
        return getContext().getString(R.string.common_favorite);
    }

    @Override
    public List<String> getRateStasuses() {
        return Arrays.asList(context.getResources().getStringArray(R.array.rate_stasuses));
    }

    @Override
    protected Context getContext() {
        return context;
    }
}
