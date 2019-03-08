package com.gnoemes.shikimoriapp.entity.app.domain

enum class UserStatus(val status: String) {
    AUTHORIZED("authorized"),
    GUEST("guest"), ;

    fun isEqualStatus(otherStatus: String): Boolean {
        return status == otherStatus
    }

    override fun toString(): String {
        return this.status
    }
}