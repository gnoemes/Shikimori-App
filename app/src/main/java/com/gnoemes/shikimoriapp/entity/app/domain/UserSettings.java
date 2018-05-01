package com.gnoemes.shikimoriapp.entity.app.domain;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.PlayerType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.TranslationDubberSettings;

public class UserSettings {

    private UserStatus status;
    private Long id;
    private Boolean isNeedShowWizard;
    private TranslationType translationType;
    private TranslationDubberSettings dubberSettings;
    private PlayerType playerType;

    public UserSettings() {
    }

    public UserSettings(@Nullable UserStatus status, @Nullable Long id,
                        @Nullable Boolean isNeedShowWizard, @Nullable TranslationType translationType,
                        @Nullable TranslationDubberSettings dubberSettings, @Nullable PlayerType playerType) {
        this.status = status;
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
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
        private Long id;
        private Boolean isNeedShowWizard;
        private TranslationType translationType;
        private TranslationDubberSettings dubberSettings;
        private PlayerType playerType;

        public Builder setStatus(UserStatus status) {
            this.status = status;
            return this;
        }

        public Builder setId(Long id) {
            this.id = id;
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
            return new UserSettings(status, id, isNeedShowWizard, translationType, dubberSettings, playerType);
        }
    }
}
