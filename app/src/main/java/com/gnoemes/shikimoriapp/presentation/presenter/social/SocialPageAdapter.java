package com.gnoemes.shikimoriapp.presentation.presenter.social;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gnoemes.shikimoriapp.entity.forum.domain.ForumType;
import com.gnoemes.shikimoriapp.presentation.presenter.social.provider.SocialResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.forums.ForumsFragment;
import com.gnoemes.shikimoriapp.presentation.view.topic.list.TopicListFragment;

public class SocialPageAdapter extends FragmentStatePagerAdapter {

    private static final int PAGES = 3;
    private SocialResourceProvider resourceProvider;

    public SocialPageAdapter(FragmentManager fm, SocialResourceProvider resourceProvider) {
        super(fm);
        this.resourceProvider = resourceProvider;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TopicListFragment.newInstance(ForumType.NEWS);
            case 1:
                return TopicListFragment.newInstance(ForumType.MY_CLUBS);
            case 2:
                return ForumsFragment.newInstance();
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return resourceProvider.getNewsTitle();
            case 1:
                return resourceProvider.getMyClubsTitle();
            case 2:
                return resourceProvider.getForumTitle();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGES;
    }


}
