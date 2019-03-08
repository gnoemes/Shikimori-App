package com.gnoemes.shikimoriapp.data.repository.user.converter

import com.gnoemes.shikimoriapp.entity.user.data.MessageResponse
import com.gnoemes.shikimoriapp.entity.user.domain.Message

interface MessageResponseConverter {

    fun convertList(responses: List<MessageResponse>): List<Message>

    fun convertResponse(response: MessageResponse): Message
}