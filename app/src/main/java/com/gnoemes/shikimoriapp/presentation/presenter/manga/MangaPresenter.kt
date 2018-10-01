package com.gnoemes.shikimoriapp.presentation.presenter.manga

import com.arellomobile.mvp.InjectViewState
import com.gnoemes.shikimoriapp.domain.app.AnalyticsInteractor
import com.gnoemes.shikimoriapp.domain.app.UserSettingsInteractor
import com.gnoemes.shikimoriapp.domain.manga.MangaInteractor
import com.gnoemes.shikimoriapp.domain.ranobe.RanobeInteractor
import com.gnoemes.shikimoriapp.domain.rates.UserRatesInteractor
import com.gnoemes.shikimoriapp.entity.anime.domain.Genre
import com.gnoemes.shikimoriapp.entity.anime.presentation.DetailsAction
import com.gnoemes.shikimoriapp.entity.anime.presentation.LinkViewModel
import com.gnoemes.shikimoriapp.entity.app.domain.AnalyticsEvent
import com.gnoemes.shikimoriapp.entity.app.domain.Type
import com.gnoemes.shikimoriapp.entity.app.domain.UserSettings
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens
import com.gnoemes.shikimoriapp.entity.main.domain.Constants
import com.gnoemes.shikimoriapp.entity.main.presentation.BottomScreens
import com.gnoemes.shikimoriapp.entity.manga.domain.MangaDetails
import com.gnoemes.shikimoriapp.entity.rates.domain.UserRate
import com.gnoemes.shikimoriapp.entity.related.domain.RelatedNavigationData
import com.gnoemes.shikimoriapp.entity.search.presentation.SearchNavigationData
import com.gnoemes.shikimoriapp.entity.search.presentation.SimilarNavigationData
import com.gnoemes.shikimoriapp.presentation.presenter.anime.converter.LinkViewModelConverter
import com.gnoemes.shikimoriapp.presentation.presenter.anime.provider.DetailsResourceProvider
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter
import com.gnoemes.shikimoriapp.presentation.view.manga.MangaView
import com.gnoemes.shikimoriapp.utils.appendHostIfNeed
import com.gnoemes.shikimoriapp.utils.appendLoadingLogic
import javax.inject.Inject

@InjectViewState
class MangaPresenter @Inject constructor(
        private val mangaInteractor: MangaInteractor,
        private val ranobeInteractor: RanobeInteractor,
        private val ratesInteractor: UserRatesInteractor,
        private val settingsInteractor: UserSettingsInteractor,
        private val resourceProvider: DetailsResourceProvider,
        private val linkViewModelConverter: LinkViewModelConverter,
        private val analyticsInteractor: AnalyticsInteractor
) : BaseNetworkPresenter<MangaView>() {

    var id: Long? = null
    var rateId: Long = Constants.NO_ID
    private var currentManga: MangaDetails? = null
    private var userSettings: UserSettings? = null
    lateinit var type: Type

    override fun initData() {
        loadMangaOrRanobe()
        loadChapters()
        loadUserSettings()
    }

    private fun loadUserSettings() {
        settingsInteractor.userSettings
                .forEach { userSettings = it }
                .addToDisposables()
    }

    private fun loadMangaOrRanobe() {
        when (type) {
            Type.MANGA -> loadManga()
            Type.RANOBE -> loadRanobe()
            else -> Unit
        }
    }

    private fun loadRanobe() {
        ranobeInteractor.getDetails(id!!)
                .appendLoadingLogic(viewState)
                .doOnSubscribe { viewState.hideErrorView() }
                .map {
                    currentManga = it
                    if (it.userRate != null) rateId = it.userRate.id!!
                    it
                }
                .subscribe({ viewState.setManga(it) }, this::processErrors)
                .addToDisposables()
    }

    private fun loadManga() {
        mangaInteractor.getDetails(id!!)
                .appendLoadingLogic(viewState)
                .doOnSubscribe { viewState.hideErrorView() }
                .map {
                    currentManga = it
                    if (it.userRate != null) rateId = it.userRate.id!!
                    it
                }
                .subscribe({ viewState.setManga(it) }, this::processErrors)
                .addToDisposables()

    }

    private fun loadChapters() {
        //TODO implement
        viewState.setChapters(emptyList())
    }


    fun onAction(action: DetailsAction, data: Any?) {
        when (action) {
            DetailsAction.LINKS -> loadLinks()
            DetailsAction.GENRE -> onGenreClicked(data as Genre)
            DetailsAction.SIMILAR -> onSimilarClicked()
            DetailsAction.CHRONOLOGY -> onChronology()
            DetailsAction.RELATED -> onRelated()
            DetailsAction.OPEN_IN_BROWSER -> onOpenInBrowserClicked()
            DetailsAction.WATCH_ONLINE -> onReadOnline()
            DetailsAction.CLEAR_HISTORY -> onClearHistoryClicked()
            DetailsAction.CHARACTER -> onCharacterClicked(data as Long)
            DetailsAction.ADD_TO_LIST -> onAddListClicked(data as? UserRate)
            else -> Unit
        }
    }

    private fun onAddListClicked(userRate: UserRate?) {
        analyticsInteractor.logEvent(AnalyticsEvent.RATE_OPENED)
        viewState.showRatesDialog(userRate)
    }

    private fun onClearHistoryClicked() {
        viewState.showClearHistoryDialog()
    }

    private fun onReadOnline() {
        viewState.setPage(1)
    }

    private fun onOpenInBrowserClicked() {
        router.navigateTo(Screens.WEB, currentManga?.url?.appendHostIfNeed())
    }

    private fun onRelated() {
        router.navigateTo(Screens.RELATED, RelatedNavigationData(id!!, type))
    }

    private fun onChronology() {
        if (type == Type.MANGA) {
            mangaInteractor.getFranchiseNodes(id!!)
                    .appendLoadingLogic(viewState)
                    .subscribe({ viewState.showChronologyDialog(it) }, this::processErrors)
                    .addToDisposables()
        } else {
            ranobeInteractor.getFranchiseNodes(id!!)
                    .appendLoadingLogic(viewState)
                    .subscribe({ viewState.showChronologyDialog(it) }, this::processErrors)
                    .addToDisposables()
        }
    }

    private fun onSimilarClicked() {
        analyticsInteractor.logEvent(AnalyticsEvent.SIMILAR_CLICKED)
        router.navigateTo(Screens.SIMILAR, SimilarNavigationData(id!!, type))
    }

    private fun onGenreClicked(genre: Genre) {
        analyticsInteractor.logEvent(AnalyticsEvent.GENRE_SEARCH)
        router.navigateTo(BottomScreens.SEARCH, SearchNavigationData(genre, type))
    }

    private fun loadLinks() {
        if (type == Type.MANGA) {
            mangaInteractor.getLinks(id!!)
                    .appendLoadingLogic(viewState)
                    .map(linkViewModelConverter)
                    .subscribe({ viewState.showLinksDialog(it) }, this::processErrors)
                    .addToDisposables()
        } else {
            ranobeInteractor.getLinks(id!!)
                    .appendLoadingLogic(viewState)
                    .map(linkViewModelConverter)
                    .subscribe({ viewState.showLinksDialog(it) }, this::processErrors)
                    .addToDisposables()
        }
    }

    fun onBackgroundImageClicked() {
        //TODO add screenshot support
    }


    fun onChaptersRefresh() = loadChapters()

    fun onLinkClicked(linkViewModel: LinkViewModel) = router.navigateTo(Screens.WEB, linkViewModel.url)

    fun onClearHistory() {
    }

    fun onDeleteRate(id: Long) {
        ratesInteractor.deleteRate(id)
                .subscribe({
                    loadMangaOrRanobe()
                    router.showSystemMessage(String.format(resourceProvider.deleteMessage, currentManga?.nameRu))
                }, this::processErrors)
                .addToDisposables()
    }

    fun onSaveRate(rate: UserRate) {
        if (rate.id == Constants.NO_ID) {
            if (userSettings?.userBrief != null) {
                createRate(rate)
            } else {
                router.showSystemMessage(resourceProvider.needAuthMessage)
            }
        } else {
            updateRate(rate)
        }
    }

    private fun updateRate(rate: UserRate) {
        ratesInteractor.updateRate(rate)
                .subscribe({ loadMangaOrRanobe() }, this::processErrors)
                .addToDisposables()
    }

    private fun createRate(rate: UserRate) {
        ratesInteractor.createRate(id!!, type, rate, userSettings?.userBrief?.id!!)
                .subscribe({
                    loadMangaOrRanobe()
                    router.showSystemMessage(String.format(resourceProvider.createMessage, currentManga?.nameRu))
                }, this::processErrors)
                .addToDisposables()
    }

}
