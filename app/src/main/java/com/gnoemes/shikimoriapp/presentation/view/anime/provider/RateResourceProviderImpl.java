package com.gnoemes.shikimoriapp.presentation.view.anime.provider;

import android.content.Context;

import com.gnoemes.shikimoriapp.R;

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
}
