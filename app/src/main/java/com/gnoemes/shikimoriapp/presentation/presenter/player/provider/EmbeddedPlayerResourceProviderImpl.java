package com.gnoemes.shikimoriapp.presentation.presenter.player.provider;

import android.content.Context;

import com.gnoemes.shikimoriapp.R;

import javax.inject.Inject;

public class EmbeddedPlayerResourceProviderImpl implements EmbeddedPlayerResourceProvider {

    private Context context;

    @Inject
    public EmbeddedPlayerResourceProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public String getHostingError() {
        return context.getString(R.string.player_hosting_error);
    }

    @Override
    public String getNetworkError() {
        return context.getString(R.string.player_network_error);
    }
}
