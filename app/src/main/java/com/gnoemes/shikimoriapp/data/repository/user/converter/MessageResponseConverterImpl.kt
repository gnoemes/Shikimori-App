package com.gnoemes.shikimoriapp.data.repository.user.converter

import com.gnoemes.shikimoriapp.data.repository.app.converter.LinkedContentResponseConverter
import com.gnoemes.shikimoriapp.entity.user.data.MessageResponse
import com.gnoemes.shikimoriapp.entity.user.domain.Message
import javax.inject.Inject

class MessageResponseConverterImpl @Inject constructor(
        private val userConverter: UserBriefResponseConverter,
        private val linkedContentResponseConverter: LinkedContentResponseConverter
) : MessageResponseConverter {

    override fun convertList(responses: List<MessageResponse>): List<Message> = responses.map { convertResponse(it) }

    override fun convertResponse(response: MessageResponse): Message =
            Message(
                    response.id,
                    response.type,
                    response.read,
                    response.body,
                    response.htmlBody,
                    response.dateCreated,
                    linkedContentResponseConverter.convertResponse(response.linked),
                    userConverter.apply(response.userFrom),
                    userConverter.apply(response.userTo)
            )

}
