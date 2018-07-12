package com.gnoemes.shikimoriapp.data.local.preferences;

import com.gnoemes.shikimoriapp.entity.app.domain.UserStatus;

import javax.inject.Inject;

public class UserSettingsConverterImpl implements UserSettingsConverter {

    @Inject
    public UserSettingsConverterImpl() {
    }

    @Override
    public UserStatus convertStatus(String status) {
        for (UserStatus userStatus : UserStatus.values()) {
            if (userStatus.isEqualStatus(status)) {
                return userStatus;
            }
        }
        return null;
    }
}
