package com.gnoemes.shikimoriapp.entity.app.domain;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.PlayerType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.TranslationDubberSettings;
import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief;

public class UserSettings {

    private UserStatus status;
    private UserBrief userBrief;
    private Boolean isNeedShowWizard;
    private TranslationType translationType;
    private TranslationDubberSettings dubberSettings;
    private PlayerType playerType;

    public UserSettings() {
    }

    public UserSettings(@Nullable UserStatus status, @Nullable UserBrief userBrief,
                        @Nullable Boolean isNeedShowWizard, @Nullable TranslationType translationType,
                        @Nullable TranslationDubberSettings dubberSettings, @Nullable PlayerType playerType) {
        this.status = status;
        this.userBrief = userBrief;
        this.isNeedShowWizard = isNeedShowWizard;
        this.translationType = translationType;
        this.dubberSettings = dubberSettings;
        this.playerType = playerType;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(@Nullable UserStatus status) {
        this.status = status;
    }

    public UserBrief getUserBrief() {
        return userBrief;
    }

    public void setUserBrief(UserBrief userBrief) {
        this.userBrief = userBrief;
    }

    public Boolean getNeedShowWizard() {
        return isNeedShowWizard;
    }

    public void setNeedShowWizard(@Nullable Boolean needShowWizard) {
        isNeedShowWizard = needShowWizard;
    }

    public TranslationType getTranslationType() {
        return translationType;
    }

    public void setTranslationType(@Nullable TranslationType translationType) {
        this.translationType = translationType;
    }

    public TranslationDubberSettings getDubberSettings() {
        return dubberSettings;
    }

    public void setDubberSettings(@Nullable TranslationDubberSettings dubberSettings) {
        this.dubberSettings = dubberSettings;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(@Nullable PlayerType playerType) {
        this.playerType = playerType;
    }

    public static class Builder {
        private UserStatus status;
        private UserBrief userBrief;
        private Boolean isNeedShowWizard;
        private TranslationType translationType;
        private TranslationDubberSettings dubberSettings;
        private PlayerType playerType;

        public Builder setStatus(UserStatus status) {
            this.status = status;
            return this;
        }

        public Builder setUserBrief(UserBrief userBrief) {
            this.userBrief = userBrief;
            return this;
        }

        public Builder setIsNeedShowWizard(Boolean isNeedShowWizard) {
            this.isNeedShowWizard = isNeedShowWizard;
            return this;
        }

        public Builder setTranslationType(TranslationType translationType) {
            this.translationType = translationType;
            return this;
        }

        public Builder setDubberSettings(TranslationDubberSettings dubberSettings) {
            this.dubberSettings = dubberSettings;
            return this;
        }

        public Builder setPlayerType(PlayerType playerType) {
            this.playerType = playerType;
            return this;
        }

        public UserSettings build() {
            return new UserSettings(status, userBrief, isNeedShowWizard, translationType, dubberSettings, playerType);
        }
    }
}
