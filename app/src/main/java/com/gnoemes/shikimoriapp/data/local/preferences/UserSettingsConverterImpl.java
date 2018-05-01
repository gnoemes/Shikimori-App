package com.gnoemes.shikimoriapp.data.local.preferences;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.PlayerType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.TranslationDubberSettings;
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

    @Override
    public TranslationType convertTranslationType(String type) {
        for (TranslationType translationType : TranslationType.values()) {
            if (translationType.isEqualType(type)) {
                return translationType;
            }
        }
        return null;
    }

    @Override
    public TranslationDubberSettings convertTranslationDubberSettings(String type) {
        for (TranslationDubberSettings dubberSettings : TranslationDubberSettings.values()) {
            if (dubberSettings.isEqualType(type)) {
                return dubberSettings;
            }
        }
        return null;
    }

    @Override
    public PlayerType convertPlayerType(String type) {
        for (PlayerType playerType : PlayerType.values()) {
            if (playerType.isEqualType(type)) {
                return playerType;
            }
        }
        return null;
    }
}
