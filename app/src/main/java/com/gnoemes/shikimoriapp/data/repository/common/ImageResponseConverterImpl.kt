package com.gnoemes.shikimoriapp.data.repository.common

import com.gnoemes.shikimoriapp.entity.common.data.ImageResponse
import com.gnoemes.shikimoriapp.entity.common.domain.Image
import com.gnoemes.shikimoriapp.utils.appendHostIfNeed
import javax.inject.Inject


class ImageResponseConverterImpl @Inject constructor() : ImageResponseConverter {

    override fun convert(img: ImageResponse): Image = Image(
            img.imageOriginalUrl.appendHostIfNeed(),
            img.imageOriginalUrl.appendHostIfNeed(),
            img.imageX96Url.appendHostIfNeed(),
            img.imageX48Url.appendHostIfNeed())
}