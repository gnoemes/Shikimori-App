package com.gnoemes.shikimoriapp.data.repository.screenshots

import com.gnoemes.shikimoriapp.data.network.AnimesApi
import com.gnoemes.shikimoriapp.data.repository.anime.converter.ScreenshotResponseConverter
import com.gnoemes.shikimoriapp.entity.screenshots.domain.Screenshot
import io.reactivex.Single
import javax.inject.Inject

class ScreenshotRepositoryImpl @Inject constructor(
        private val animeApi: AnimesApi,
        private val converter: ScreenshotResponseConverter
) : ScreenshotRepository {


    override fun getAnimeScreenshots(id: Long): Single<MutableList<Screenshot>> =
            animeApi.getScreenshots(id)
                    .map(converter)
}