package com.gnoemes.shikimoriapp.data.repository.common

import com.gnoemes.shikimoriapp.entity.common.data.ImageResponse
import com.gnoemes.shikimoriapp.entity.common.domain.Image

interface ImageResponseConverter {
    fun convert(img: ImageResponse): Image
}