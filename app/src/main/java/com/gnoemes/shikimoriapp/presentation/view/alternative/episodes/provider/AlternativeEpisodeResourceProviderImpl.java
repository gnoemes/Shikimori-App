package com.gnoemes.shikimoriapp.presentation.view.alternative.episodes.provider;

import android.content.Context;

import com.gnoemes.shikimoriapp.R;

import javax.inject.Inject;

public class AlternativeEpisodeResourceProviderImpl implements AlternativeEpisodeResourceProvider {

    private Context context;

    @Inject
    public AlternativeEpisodeResourceProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public String getPlaceholderMessage() {
        return context.getString(R.string.episode_nothing);
    }
}
