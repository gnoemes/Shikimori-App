package com.gnoemes.shikimoriapp.entity.menu.presentration;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.app.domain.UserStatus;

public class MenuProfileViewModel extends BaseMenuItem {

    private UserStatus status;
    @Nullable
    private String userName;
    @Nullable
    private String avatarUrl;

    public MenuProfileViewModel(UserStatus status,
                                @Nullable String userName,
                                @Nullable String avatarUrl) {
        this.status = status;
        this.userName = userName;
        this.avatarUrl = avatarUrl;
    }

    public UserStatus getStatus() {
        return status;
    }

    @Nullable
    public String getUserName() {
        return userName;
    }

    @Nullable
    public String getAvatarUrl() {
        return avatarUrl;
    }
}
