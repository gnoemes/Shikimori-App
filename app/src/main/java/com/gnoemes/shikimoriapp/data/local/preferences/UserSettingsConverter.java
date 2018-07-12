package com.gnoemes.shikimoriapp.data.local.preferences;

import com.gnoemes.shikimoriapp.entity.app.domain.UserStatus;

public interface UserSettingsConverter {

    UserStatus convertStatus(String status);
}
