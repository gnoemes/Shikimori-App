package com.gnoemes.shikimoriapp.entity.user.domain

import com.gnoemes.shikimoriapp.entity.app.domain.LinkedContent
import org.joda.time.DateTime

data class Message(
        val id: Long,
        val type: MessageType,
        val read: Boolean,
        val body: String,
        val htmlBody: String,
        val dateCreated: DateTime,
        val linked: LinkedContent?,
        val userFrom: UserBrief,
        val userTo: UserBrief
)