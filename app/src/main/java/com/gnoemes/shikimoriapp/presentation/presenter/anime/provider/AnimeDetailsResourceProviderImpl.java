package com.gnoemes.shikimoriapp.presentation.presenter.anime.provider;

import android.content.Context;

import com.gnoemes.shikimoriapp.R;

import javax.inject.Inject;

public class AnimeDetailsResourceProviderImpl implements AnimeDetailsResourceProvider {

    private Context context;

    @Inject
    public AnimeDetailsResourceProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public String getNeedAuthMessage() {
        return context.getString(R.string.need_auth_message);
    }

    @Override
    public String getSuccessMessage() {
        return context.getString(R.string.settings_success_save);
    }

    @Override
    public String getDeleteMessage() {
        return context.getString(R.string.delete_format_message);
    }

    @Override
    public String getCreateMessage() {
        return context.getString(R.string.add_success_format_message);
    }
}
