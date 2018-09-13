package com.gnoemes.shikimoriapp.presentation.view.manga

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.gnoemes.shikimoriapp.entity.anime.domain.FranchiseNode
import com.gnoemes.shikimoriapp.entity.anime.presentation.LinkViewModel
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem
import com.gnoemes.shikimoriapp.entity.manga.domain.MangaDetails
import com.gnoemes.shikimoriapp.entity.rates.domain.UserRate
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragmentView

@StateStrategyType(AddToEndSingleStrategy::class)
interface MangaView : BaseFragmentView {

    @StateStrategyType(AddToEndStrategy::class)
    fun setManga(details: MangaDetails)

    @StateStrategyType(AddToEndStrategy::class)
    fun setChapters(items: List<BaseItem>)

    @StateStrategyType(SkipStrategy::class)
    fun setPage(pos: Int)

    @StateStrategyType(SkipStrategy::class)
    fun showErrorView()

    @StateStrategyType(SkipStrategy::class)
    fun hideErrorView()

    @StateStrategyType(AddToEndStrategy::class)
    fun onShowRefresh()

    @StateStrategyType(AddToEndStrategy::class)
    fun onHideRefresh()

    @StateStrategyType(SkipStrategy::class)
    fun showLinksDialog(links: List<LinkViewModel>)

    @StateStrategyType(SkipStrategy::class)
    fun showChronologyDialog(nodes: List<FranchiseNode>)

    @StateStrategyType(SkipStrategy::class)
    fun showRatesDialog(rate: UserRate?)

    @StateStrategyType(SkipStrategy::class)
    fun showClearHistoryDialog()
}