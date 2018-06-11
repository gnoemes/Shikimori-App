package com.gnoemes.shikimoriapp.presentation.view.related.adapter;

import android.content.Context;

import com.gnoemes.shikimoriapp.presentation.view.main.provider.BaseAnimeResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.related.provider.RelatedAnimeResourceProvider;

import javax.inject.Inject;

public class RelatedAnimeResourceProviderImpl extends BaseAnimeResourceProvider implements RelatedAnimeResourceProvider {

    private Context context;

    @Inject
    public RelatedAnimeResourceProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    protected Context getContext() {
        return context;
    }
}
