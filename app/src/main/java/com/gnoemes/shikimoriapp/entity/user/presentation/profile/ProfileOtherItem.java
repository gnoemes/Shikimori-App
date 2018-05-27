package com.gnoemes.shikimoriapp.entity.user.presentation.profile;

import com.gnoemes.shikimoriapp.entity.user.domain.Favorite;

import java.util.List;

public class ProfileOtherItem extends BaseProfileItem {

    private List<Favorite> favorites;

    public ProfileOtherItem() {
    }

    public ProfileOtherItem(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }
}
