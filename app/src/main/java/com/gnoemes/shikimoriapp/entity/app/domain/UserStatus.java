package com.gnoemes.shikimoriapp.entity.app.domain;

public enum UserStatus {
    AUTHORIZED("authorized"),
    GUEST("guest"),;

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }

    public boolean isEqualStatus(String otherStatus) {
        return status.equals(otherStatus);
    }

    @Override
    public String toString() {
        return this.status;
    }
}
