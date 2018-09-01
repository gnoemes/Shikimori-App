package com.gnoemes.shikimoriapp.data.repository.screenshots

import com.gnoemes.shikimoriapp.entity.screenshots.domain.Screenshot
import io.reactivex.Single

interface ScreenshotRepository {

    fun getAnimeScreenshots(id: Long): Single<MutableList<Screenshot>>

}