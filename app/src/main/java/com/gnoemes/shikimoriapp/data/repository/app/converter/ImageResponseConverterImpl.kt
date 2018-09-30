package com.gnoemes.shikimoriapp.data.repository.app.converter

import com.gnoemes.shikimoriapp.entity.common.data.ImageResponse
import com.gnoemes.shikimoriapp.entity.common.domain.Image
import com.gnoemes.shikimoriapp.utils.appendHostIfNeed
import javax.inject.Inject

class ImageResponseConverterImpl @Inject constructor() : ImageResponseConverter {

    override fun convert(image: ImageResponse): Image =
            Image(
                    image.original?.appendHostIfNeed(),
                    image.preview?.appendHostIfNeed(),
                    image.x96?.appendHostIfNeed(),
                    image.x48?.appendHostIfNeed()
            )
}