package com.gnoemes.shikimoriapp.presentation.view.manga

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.transition.Fade
import android.support.transition.TransitionManager
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gnoemes.shikimoriapp.R
import com.gnoemes.shikimoriapp.data.local.preferences.UserSettingsSource
import com.gnoemes.shikimoriapp.entity.anime.domain.FranchiseNode
import com.gnoemes.shikimoriapp.entity.anime.presentation.DetailsAction
import com.gnoemes.shikimoriapp.entity.anime.presentation.LinkViewModel
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem
import com.gnoemes.shikimoriapp.entity.manga.domain.MangaDetails
import com.gnoemes.shikimoriapp.entity.manga.presentation.MangaNavigationData
import com.gnoemes.shikimoriapp.entity.rates.domain.UserRate
import com.gnoemes.shikimoriapp.presentation.presenter.manga.MangaPresenter
import com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime.AnimeItemCallback
import com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime.DetailsCharacterAdapter
import com.gnoemes.shikimoriapp.presentation.view.anime.converter.FranchiseNodeToStringConverterImpl
import com.gnoemes.shikimoriapp.presentation.view.anime.provider.RateResourceProvider
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider
import com.gnoemes.shikimoriapp.presentation.view.common.widget.NetworkErrorView
import com.gnoemes.shikimoriapp.presentation.view.manga.adapter.ChaptersAdapter
import com.gnoemes.shikimoriapp.presentation.view.manga.adapter.MangaAdapter
import com.gnoemes.shikimoriapp.presentation.view.manga.converter.MangaDetailsViewModelConverter
import com.gnoemes.shikimoriapp.presentation.view.manga.provider.ChapterAdapterResourceProvider
import com.gnoemes.shikimoriapp.utils.addBackButton
import com.gnoemes.shikimoriapp.utils.dialogContext
import com.gnoemes.shikimoriapp.utils.ifNotNull
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader
import com.gnoemes.shikimoriapp.utils.kotlin.gone
import com.gnoemes.shikimoriapp.utils.kotlin.visible
import com.gnoemes.shikimoriapp.utils.view.ViewStatePagerAdapter
import com.gnoemes.shikimoriapp.utils.withArgs
import kotlinx.android.synthetic.main.fragment_details_page.*
import kotlinx.android.synthetic.main.fragment_manga.view.*
import kotlinx.android.synthetic.main.layout_default_list.view.*
import javax.inject.Inject

class MangaFragment : BaseFragment<MangaPresenter, MangaView>(), MangaView {

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var resourceProvider: RateResourceProvider

    @Inject
    lateinit var adapterResourceProvider: ChapterAdapterResourceProvider

    @Inject
    lateinit var userSettings: UserSettingsSource

    @Inject
    lateinit var converter: MangaDetailsViewModelConverter

    @InjectPresenter
    lateinit var mangaPresenter: MangaPresenter

    @ProvidePresenter
    fun providePresenter(): MangaPresenter {
        mangaPresenter = presenterProvider.get()

        parentFragment.ifNotNull {
            mangaPresenter.setLocalRouter((it as RouterProvider).localRouter)
        }

        arguments.ifNotNull {
            val data = it.getParcelable<MangaNavigationData>(AppExtras.ARGUMENT_MANGA_DATA)
            if (data != null) {
                mangaPresenter.id = data.id
                mangaPresenter.type = data.type
            }
        }

        return mangaPresenter
    }

    private val characterAdapter by lazy { DetailsCharacterAdapter(imageLoader, AnimeItemCallback { action, data -> presenter.onAction(action, data) }) }
    private val mangaAdapter by lazy { MangaAdapter(resourceProvider, characterAdapter) { action: DetailsAction, data: Any? -> presenter.onAction(action, data) } }
    private val chaptersAdapter by lazy { ChaptersAdapter(adapterResourceProvider) }
    private val pagerAdapter by lazy { MangaPagerAdapter(mangaAdapter, chaptersAdapter) }

    companion object {
        @JvmStatic
        fun newInstance(data: MangaNavigationData): MangaFragment = MangaFragment().withArgs { putParcelable(AppExtras.ARGUMENT_MANGA_DATA, data) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(fragmentLayout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private val onOffsetChangedListener = AppBarLayout.OnOffsetChangedListener { _, verticalOffset -> subtitle.alpha = (1 - Math.abs(verticalOffset.toDouble() / 1.5 / 100.0)).toFloat() }

    override fun onDestroyView() {
        view_pager.adapter = null
        app_bar.removeOnOffsetChangedListener(onOffsetChangedListener)
        image_background.setOnClickListener(null)
        imageLoader.clearImage(image_background)
        super.onDestroyView()
    }

    private fun initViews() {

        view_pager.adapter = pagerAdapter

        toolbar?.addBackButton()
        toolbar?.setNavigationOnClickListener { presenter.onBackPressed() }

        image_background.setOnClickListener { presenter.onBackgroundImageClicked() }
        app_bar.addOnOffsetChangedListener(onOffsetChangedListener)

        view_network_error.setText(R.string.common_error_message_without_pull)
        view_network_error.gone()

        presenter.onChaptersRefresh()
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    override fun getPresenter(): MangaPresenter = mangaPresenter

    override fun getFragmentLayout(): Int = R.layout.fragment_details_page

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    override fun onShowLoading() {
        TransitionManager.beginDelayedTransition(coordinator_layout, Fade())
        progress_loading.visible()
        app_bar.gone()
        view_pager.gone()
    }

    override fun onHideLoading() {
        TransitionManager.beginDelayedTransition(coordinator_layout, Fade())
        progress_loading.gone()
        app_bar.visible()
        view_pager.visible()
    }

    override fun setManga(details: MangaDetails) {
        imageLoader.setImageWithFit(image_background, details.image.original)
        collapsing_toolbar.title = if (userSettings.getRomadziNaming()) details.name else details.nameRu
        subtitle.text = if (userSettings.getRomadziNaming()) details.nameRu else details.name
        pagerAdapter.setItems(converter.convert(details))
    }

    override fun setChapters(items: List<BaseItem>) {
        pagerAdapter.setChapters(items)
    }

    override fun showErrorView() {
        view_network_error.visible()
        view_pager.gone()
        app_bar.setExpanded(false)
    }

    override fun hideErrorView() {
        view_network_error.gone()
        view_pager.visible()
        app_bar.setExpanded(true)
    }

    override fun setPage(pos: Int) = view_pager.setCurrentItem(pos, true)

    override fun onShowRefresh() = pagerAdapter.onShowRefresh()

    override fun onHideRefresh() = pagerAdapter.onHideRefresh()

    override fun showLinksDialog(links: List<LinkViewModel>) {
        context.ifNotNull { context ->
            MaterialDialog(context.dialogContext())
                    .show {
                        title(R.string.common_links)
                        listItems(items = links.map { it.name }) { _, pos, _ -> presenter.onLinkClicked(links[pos]) }
                    }

        }
    }

    override fun showChronologyDialog(nodes: List<FranchiseNode>) {
        context.ifNotNull { context ->
            val converter = FranchiseNodeToStringConverterImpl(context)
            MaterialDialog(context.dialogContext())
                    .show {
                        title(R.string.chronology)
                        listItems(items = converter.convertList(nodes)) { _, pos, _ -> presenter.onMangaClicked(nodes[pos].id) }
                    }
        }
    }

    override fun showRatesDialog(rate: UserRate?) {
        //TODO manga rates
    }

    override fun showClearHistoryDialog() {
        context.ifNotNull { context ->
            MaterialDialog(context.dialogContext())
                    .show {
                        message(R.string.clear_episodes_content)
                        positiveButton(R.string.yes) { presenter.onClearHistory() }
                        negativeButton(R.string.common_cancel)
                    }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // INNER CLASS
    ///////////////////////////////////////////////////////////////////////////

    inner class MangaPagerAdapter(
            private val mangaAdapter: MangaAdapter,
            private val chaptersAdapter: ChaptersAdapter
    ) : ViewStatePagerAdapter() {

        private val screens = arrayOf(R.layout.fragment_manga, R.layout.fragment_series)

        private var mangaView: ViewGroup? = null
        private var chaptersView: ViewGroup? = null

        override fun getCount(): Int = screens.size

        override fun createView(container: ViewGroup?, position: Int): View {
            val inflater = LayoutInflater.from(container?.context)!!
            val layout = inflater.inflate(screens[position], container, false) as ViewGroup
            when (position) {
                0 -> createMangaPage(layout)
                1 -> createChaptersPage(layout)
            }

            return layout
        }

        private fun createChaptersPage(layout: ViewGroup) {
            chaptersView = layout
            chaptersView.ifNotNull {
                with(it) {
                    refresh_layout.setOnRefreshListener { presenter.onChaptersRefresh() }
                    this.findViewById<NetworkErrorView>(R.id.view_network_error)?.gone()

                    with(list) {
                        itemAnimator = DefaultItemAnimator()
                        layoutManager = LinearLayoutManager(context)
                        adapter = chaptersAdapter
                    }
                }
            }
        }

        private fun createMangaPage(layout: ViewGroup) {
            mangaView = layout
            mangaView.ifNotNull {
                with(it.mangaDetailsList) {
                    itemAnimator = DefaultItemAnimator()
                    adapter = mangaAdapter
                    layoutManager = LinearLayoutManager(context)
                }
            }
        }

        fun setItems(items: List<BaseItem>) {
            mangaAdapter.bindItems(items)
        }

        fun onShowRefresh() {
            chaptersView?.refresh_layout?.visible()
        }

        fun onHideRefresh() {
            chaptersView?.refresh_layout?.gone()
        }

        fun setChapters(items: List<BaseItem>) {
            chaptersAdapter.bindItems(items)
        }

    }
}