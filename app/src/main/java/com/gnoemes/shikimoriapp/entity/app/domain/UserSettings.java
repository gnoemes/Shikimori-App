package com.gnoemes.shikimoriapp.entity.app.domain;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief;

@Deprecated
public class UserSettings {

    private UserStatus status;
    private UserBrief userBrief;
    private Boolean isLightTheme;
    private Boolean isAutoStatus;

    public UserSettings() {
    }

    public UserSettings(@Nullable UserStatus status,
                        @Nullable UserBrief userBrief,
                        @Nullable Boolean isLightTheme,
                        @Nullable Boolean isAutoStatus) {
        this.status = status;
        this.userBrief = userBrief;
        this.isLightTheme = isLightTheme;
        this.isAutoStatus = isAutoStatus;
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

    public Boolean getAutoStatus() {
        return isAutoStatus;
    }

    public void setAutoStatus(Boolean autoStatus) {
        isAutoStatus = autoStatus;
    }

    public static class Builder {
        private UserStatus status;
        private UserBrief userBrief;
        private Boolean isLightTheme;
        private Boolean isAutoStatus;

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

        public void setAutoStatus(Boolean autoStatus) {
            isAutoStatus = autoStatus;
        }

        public UserSettings build() {
            return new UserSettings(status, userBrief, isLightTheme, isAutoStatus);
        }
    }
}
