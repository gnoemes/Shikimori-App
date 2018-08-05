package com.gnoemes.shikimoriapp.presentation.presenter.history.provider;

import android.content.Context;

import com.gnoemes.shikimoriapp.R;

import javax.inject.Inject;

public class HistoryResourceProviderImpl implements HistoryResourceProvider {

    private Context context;

    @Inject
    public HistoryResourceProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public String getPlaceholderMessage() {
        return context.getString(R.string.related_not_found);
    }
}
