package com.gnoemes.shikimoriapp.presentation.view.profile.adapter;

import com.gnoemes.shikimoriapp.entity.user.presentation.profile.ProfileAction;

public interface ProfileCallback {
    void onAction(ProfileAction action, long id);
}
