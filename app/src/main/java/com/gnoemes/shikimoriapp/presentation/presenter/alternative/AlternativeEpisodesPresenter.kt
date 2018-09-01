package com.gnoemes.shikimoriapp.presentation.presenter.alternative

import com.arellomobile.mvp.InjectViewState
import com.gnoemes.shikimoriapp.R
import com.gnoemes.shikimoriapp.domain.anime.AnimeInteractor
import com.gnoemes.shikimoriapp.domain.anime.series.AlternativeSeriesInteractor
import com.gnoemes.shikimoriapp.domain.app.AnalyticsInteractor
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeDetails
import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslationType
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.AlternativeTranslationNavigationData
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodeItem
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodeOptionAction
import com.gnoemes.shikimoriapp.entity.app.domain.*
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens
import com.gnoemes.shikimoriapp.entity.main.domain.Constants
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter
import com.gnoemes.shikimoriapp.presentation.view.alternative.episodes.AlternativeEpisodesView
import com.gnoemes.shikimoriapp.presentation.view.alternative.episodes.converter.AlternativeEpisodeViewModelConverter
import com.gnoemes.shikimoriapp.utils.appendLoadingLogic
import com.gnoemes.shikimoriapp.utils.ifNotNull

@InjectViewState
class AlternativeEpisodesPresenter(
        val animeInteractor: AnimeInteractor,
        val seriesInteractor: AlternativeSeriesInteractor,
        val viewModelConverter: AlternativeEpisodeViewModelConverter,
        val analyticsInteractor: AnalyticsInteractor
) : BaseNetworkPresenter<AlternativeEpisodesView>() {

    var animeId: Long? = null
    var rateId: Long = Constants.NO_ID
    private lateinit var selectedEpisode: EpisodeItem

    override fun initData() {
        viewState.setTitle(R.string.episodes_alternative)
        loadEpisodes()
    }

    fun loadEpisodes() {
        animeInteractor.loadAnimeDetails(animeId!!)
                .map { setAnime(it) }
                .flatMap { seriesInteractor.getEpisodes(animeId!!) }
                .appendLoadingLogic(viewState)
                .map(viewModelConverter)
                .subscribe({ viewState.showList(it) }, this::processErrors)
                .addToDisposables()
    }

    private fun setAnime(animeDetails: AnimeDetails): AnimeDetails {
        animeDetails.userRate.ifNotNull { rateId = it.id }
        return animeDetails
    }

    fun onTranslationChoosed(type: AlternativeTranslationType) {
        router.navigateTo(Screens.ALTERNATIVE_TRANSLATIONS, AlternativeTranslationNavigationData(animeId!!, selectedEpisode.id, rateId, type))
    }

    fun onEpisodeClicked(episode: EpisodeItem) {
        selectedEpisode = episode
        val types = mutableListOf(*AlternativeTranslationType.values())
        viewState.showTranslationDialog(types)
    }

    fun onEpisodeOptionAction(action: EpisodeOptionAction, item: EpisodeItem) {
        when (action) {
            EpisodeOptionAction.WATCH_ONLINE -> {
                analyticsInteractor.logEvent(AnalyticsEvent.WATCH_ONLINE_MASTER_CLICKED)
                onEpisodeClicked(item)
            }
            else -> {
            }
        }
    }

    override fun processErrors(throwable: Throwable) {
        when (throwable) {
            is BaseException ->
                when (throwable.tag) {
                    NetworkException.TAG -> viewState.showErrorView()
                    ServiceCodeException.TAG -> if ((throwable as ServiceCodeException).serviceCode == HttpStatusCode.NOT_FOUND) {
                        //not implemented
                        //404 returns on increment episode on unexisting rate
                    } else {
                        super.processErrors(throwable)
                    }
                }
            else -> super.processErrors(throwable)
        }

    }


}



