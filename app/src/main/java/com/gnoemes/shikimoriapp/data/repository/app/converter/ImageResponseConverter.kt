package com.gnoemes.shikimoriapp.data.repository.app.converter

import com.gnoemes.shikimoriapp.entity.common.data.ImageResponse
import com.gnoemes.shikimoriapp.entity.common.domain.Image

interface ImageResponseConverter {

    fun convert(image: ImageResponse): Image
}