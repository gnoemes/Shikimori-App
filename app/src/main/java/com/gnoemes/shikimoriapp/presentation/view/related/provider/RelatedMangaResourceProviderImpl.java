package com.gnoemes.shikimoriapp.presentation.view.related.provider;

import android.content.Context;

import com.gnoemes.shikimoriapp.presentation.view.main.provider.BaseMangaResourceProvider;

import javax.inject.Inject;

public class RelatedMangaResourceProviderImpl extends BaseMangaResourceProvider implements RelatedMangaResourceProvider {

    private Context context;

    @Inject
    public RelatedMangaResourceProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    protected Context getContext() {
        return context;
    }
}
