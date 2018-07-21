package com.gnoemes.shikimoriapp.presentation.view.profile.provider;

import android.content.Context;

import com.gnoemes.shikimoriapp.R;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class ProfileResourceProviderImpl implements ProfileResourceProvider {

    private Context context;

    @Inject
    public ProfileResourceProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public List<String> getRates() {
        return Arrays.asList(context.getResources().getStringArray(R.array.rate_stasuses_with_count_and_title));
    }
}
