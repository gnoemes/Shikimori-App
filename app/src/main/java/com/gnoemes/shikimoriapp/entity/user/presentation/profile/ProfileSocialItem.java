package com.gnoemes.shikimoriapp.entity.user.presentation.profile;

import java.util.List;

public class ProfileSocialItem extends BaseProfileItem {

    private List<ImageContent> friends;
    private List<ImageContent> clubs;

    public ProfileSocialItem() {
    }

    public ProfileSocialItem(List<ImageContent> friends, List<ImageContent> clubs) {
        this.friends = friends;
        this.clubs = clubs;
    }

    public List<ImageContent> getFriends() {
        return friends;
    }

    public List<ImageContent> getClubs() {
        return clubs;
    }
}
