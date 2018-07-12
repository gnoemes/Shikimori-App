package com.gnoemes.shikimoriapp.entity.app.domain;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief;

public class UserSettings {

    private UserStatus status;
    private UserBrief userBrief;
    private Boolean isLightTheme;

    public UserSettings() {
    }

    public UserSettings(@Nullable UserStatus status,
                        @Nullable UserBrief userBrief,
                        @Nullable Boolean isLightTheme) {
        this.status = status;
        this.userBrief = userBrief;
        this.isLightTheme = isLightTheme;
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

    public Boolean getTheme() {
        return isLightTheme;
    }

    public void setLightTheme(Boolean lightTheme) {
        isLightTheme = lightTheme;
    }

    public static class Builder {
        private UserStatus status;
        private UserBrief userBrief;
        private Boolean isLightTheme;

        public Builder setStatus(UserStatus status) {
            this.status = status;
            return this;
        }

        public Builder setUserBrief(UserBrief userBrief) {
            this.userBrief = userBrief;
            return this;
        }

        public Builder setLightTheme(Boolean lightTheme) {
            isLightTheme = lightTheme;
            return this;
        }

        public UserSettings build() {
            return new UserSettings(status, userBrief, isLightTheme);
        }
    }
}
