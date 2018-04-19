package com.gnoemes.shikimoriapp.utils.rx;

import android.content.Context;

import com.gnoemes.shikimoriapp.R;

import javax.inject.Inject;

public class ErrorResourceProviderImpl implements ErrorResourceProvider {

    private Context context;

    @Inject
    public ErrorResourceProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public String getUnknownHostException() {
        return context.getString(R.string.error_internet);
    }

    @Override
    public String getSocketTimeoutException() {
        return context.getString(R.string.error_timeout);
    }

    @Override
    public String getJsonSyntaxException() {
        return context.getString(R.string.error_json);
    }

    @Override
    public String getConnectionErrorException() {
        return context.getString(R.string.error_internet);
    }

    @Override
    public String getUnknownException() {
        return context.getString(R.string.error_unknown);
    }
}
