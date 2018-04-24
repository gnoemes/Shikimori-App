package com.gnoemes.shikimoriapp.presentation.view.search.provider;

import android.content.Context;

import com.gnoemes.shikimoriapp.presentation.view.main.provider.BaseAnimeResourceProvider;

import javax.inject.Inject;

public class SearchAnimeResourceProviderImpl extends BaseAnimeResourceProvider implements SearchAnimeResourceProvider {

    private Context context;

    @Inject
    public SearchAnimeResourceProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    protected Context getContext() {
        return context;
    }


}
