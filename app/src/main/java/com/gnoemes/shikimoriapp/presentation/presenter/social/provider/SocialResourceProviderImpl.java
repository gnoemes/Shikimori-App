package com.gnoemes.shikimoriapp.presentation.presenter.social.provider;

import android.content.Context;

import com.gnoemes.shikimoriapp.R;

import javax.inject.Inject;

public class SocialResourceProviderImpl implements SocialResourceProvider {

    private Context context;

    @Inject
    public SocialResourceProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public String getForumTitle() {
        return context.getString(R.string.common_forum);
    }

    @Override
    public String getNewsTitle() {
        return context.getString(R.string.common_news);
    }

    @Override
    public String getMyClubsTitle() {
        return context.getString(R.string.forum_my_feed);
    }
}
