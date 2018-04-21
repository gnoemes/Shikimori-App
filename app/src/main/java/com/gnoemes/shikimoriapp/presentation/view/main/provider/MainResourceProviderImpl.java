package com.gnoemes.shikimoriapp.presentation.view.main.provider;

import android.content.Context;

import com.gnoemes.shikimoriapp.R;

import javax.inject.Inject;

public class MainResourceProviderImpl implements MainResourceProvider {

    private Context context;

    @Inject
    public MainResourceProviderImpl(Context context) {
        this.context = context;
    }

    /**
     * Get exit message
     *
     * @return String exit message
     */
    @Override
    public String getExitMessage() {
        return context.getString(R.string.main_exit_message);
    }
}
