package com.gnoemes.shikimoriapp.presentation.presenter.alternative

import com.gnoemes.shikimoriapp.domain.anime.series.AlternativeSeriesInteractor
import com.gnoemes.shikimoriapp.domain.download.DownloadInteractor
import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslationType
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.AlternativeTranslationNavigationData
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.AlternativeTranslationViewModel
import com.gnoemes.shikimoriapp.entity.app.domain.BaseException
import com.gnoemes.shikimoriapp.entity.app.domain.ContentException
import com.gnoemes.shikimoriapp.entity.app.domain.NetworkException
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter
import com.gnoemes.shikimoriapp.presentation.view.alternative.translations.AlternativeTranslationsView
import com.gnoemes.shikimoriapp.presentation.view.alternative.translations.converter.AlternativeTranslationViewModelConverter
import com.gnoemes.shikimoriapp.presentation.view.main.provider.TitleResourceProvider
import com.gnoemes.shikimoriapp.utils.appendLoadingLogic

class AlternativeTranslationsPresenter(private val seriesInteractor: AlternativeSeriesInteractor,
                                       private val downloadInteractor: DownloadInteractor,
                                       private val resourceProvider: TitleResourceProvider,
                                       private val converter: AlternativeTranslationViewModelConverter
) : BaseNetworkPresenter<AlternativeTranslationsView>() {

    private lateinit var currentType: AlternativeTranslationType
    private lateinit var currentTranslation: AlternativeTranslationViewModel

    lateinit var data: AlternativeTranslationNavigationData

    override fun initData() {
        loadTranslations()
        viewState.setTitle(resourceProvider.translationsTitle)
    }

    fun loadTranslations() {
        seriesInteractor.getEpisodeTranslations(currentType, data.animeId, data.episodeId)
                .doOnSubscribe { viewState.hideEmptyView() }
                .doOnSubscribe { viewState.hideErrorView() }
                .appendLoadingLogic(viewState)
                .map(converter)
                .subscribe(this::showData, this::processErrors)
                .addToDisposables()
    }

    private fun showData(models: List<AlternativeTranslationViewModel>) {
        if (models.isEmpty()) {
            viewState.showEmptyView()
        } else {
            viewState.showTranslations(models)
        }
    }

    /**
     * Show dialog with translations type
     */
    fun onSettingsClicked() {
        viewState.showSettingsDialog()
    }

    /**
     * Sets type to ALL and call [.loadTranslations]
     */
    fun onFindAll() {
        currentType = AlternativeTranslationType.ALL
        loadTranslations()
    }


    /**
     * Sets type from user's choice and call [.loadTranslations]
     */
    fun onTypeClicked(type: AlternativeTranslationType) {
        currentType = type
        loadTranslations()
    }

    fun onTranslationClicked(translation: AlternativeTranslationViewModel) {
        this.currentTranslation = translation
        setEpisodeWatched(data.animeId, data.episodeId)
        router.navigateTo(Screens.WEB_PLAYER, translation.url)
    }

    fun onDownloadTranslation(translation: AlternativeTranslationViewModel) {
        currentTranslation = translation
        viewState.checkPermissions()
    }

    private fun downloadTranslation() {
        val disposable = downloadInteractor.downloadFromSmotretAnime(currentTranslation.id)
                .doOnSubscribe { viewState.onShowLoading() }
                .doOnError { viewState.onHideLoading() }
                .doOnComplete { viewState.onHideLoading() }
                .doOnComplete { router.showSystemMessage("Загрузка началась.\nУспешность загрузки с этого ресурса не гарантируется") }
                .subscribe({}, this::processErrors)
        unsubscribeOnDestroy(disposable)
    }

    private fun setEpisodeWatched(animeId: Long, episodeId: Long) {
        seriesInteractor.setEpisodeWatched(animeId, episodeId, data.rateId)
                .subscribe({}, this::processErrors)
                .addToDisposables()
    }

    override fun processErrors(throwable: Throwable) {
        if (throwable is BaseException) {
            when (throwable.tag) {
                NetworkException.TAG -> viewState.showErrorView()
                ContentException.TAG -> router.showSystemMessage("Произошла ошибка во время загрузки видео")
                else -> super.processErrors(throwable)
            }
        } else {
            super.processErrors(throwable)
        }
    }

    fun onPermissionDenied() {
        router.showSystemMessage("Для загрузки видео необходимо разрешение")
    }

    fun onNeverAskAgain() {
        router.showSystemMessage("Для загрузки видео необходимо разрешение")
    }

    fun onPermissionGranted() {
        downloadTranslation()
    }

}