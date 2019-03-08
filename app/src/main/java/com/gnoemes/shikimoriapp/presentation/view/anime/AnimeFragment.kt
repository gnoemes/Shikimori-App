package com.gnoemes.shikimoriapp.presentation.view.anime

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.transition.Fade
import android.support.transition.TransitionManager
import android.support.v4.view.ViewPager
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.checkbox.checkBoxPrompt
import com.afollestad.materialdialogs.list.listItems
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gnoemes.shikimoriapp.R
import com.gnoemes.shikimoriapp.data.local.preferences.UserSettingsSource
import com.gnoemes.shikimoriapp.entity.anime.domain.FranchiseNode
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeDetailsPage
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeDetailsViewModel
import com.gnoemes.shikimoriapp.entity.anime.presentation.LinkViewModel
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.BaseEpisodeItem
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType
import com.gnoemes.shikimoriapp.entity.app.data.SettingsExtras
import com.gnoemes.shikimoriapp.entity.app.domain.Type
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem
import com.gnoemes.shikimoriapp.entity.rates.domain.UserRate
import com.gnoemes.shikimoriapp.presentation.presenter.anime.AnimePresenter
import com.gnoemes.shikimoriapp.presentation.presenter.anime.converter.AnimeDetailsViewModelConverter
import com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime.AnimeAdapter
import com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime.AnimeItemCallback
import com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime.DetailsCharacterAdapter
import com.gnoemes.shikimoriapp.presentation.view.anime.adapter.comments.CommentsAdapter
import com.gnoemes.shikimoriapp.presentation.view.anime.adapter.episodes.EpisodeAdapter
import com.gnoemes.shikimoriapp.presentation.view.anime.adapter.episodes.EpisodeOptionCallback
import com.gnoemes.shikimoriapp.presentation.view.anime.adapter.episodes.EpisodePickCallback
import com.gnoemes.shikimoriapp.presentation.view.anime.converter.FranchiseNodeToStringConverterImpl
import com.gnoemes.shikimoriapp.presentation.view.anime.provider.RateResourceProvider
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RateDialogFragment
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider
import com.gnoemes.shikimoriapp.presentation.view.common.widget.NetworkErrorView
import com.gnoemes.shikimoriapp.utils.*
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader
import com.gnoemes.shikimoriapp.utils.kotlin.gone
import com.gnoemes.shikimoriapp.utils.kotlin.visible
import com.gnoemes.shikimoriapp.utils.view.DefaultItemCallback
import com.gnoemes.shikimoriapp.utils.view.LinearStickyHead
import com.gnoemes.shikimoriapp.utils.view.ViewStatePagerAdapter
import kotlinx.android.synthetic.main.fragment_anime.view.*
import kotlinx.android.synthetic.main.fragment_comments.view.*
import kotlinx.android.synthetic.main.fragment_details_page.*
import kotlinx.android.synthetic.main.layout_default_list.view.*
import javax.inject.Inject

class AnimeFragment : BaseFragment<AnimePresenter, AnimeView>(), AnimeView {

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var converter: AnimeDetailsViewModelConverter

    @Inject
    lateinit var rateResourceProvider: RateResourceProvider

    @Inject
    lateinit var userSettings: UserSettingsSource

    private val pagerAdapter: AnimePagerAdapter by lazy { AnimePagerAdapter(commentsAdapter, animeAdapter, episodeAdapter) }
    private val commentsAdapter by lazy { CommentsAdapter(imageLoader, DefaultItemCallback { presenter.onUserClicked(it) }) }
    private val episodeAdapter by lazy { EpisodeAdapter(EpisodePickCallback { item -> presenter.onEpisodeClicked(item) }, EpisodeOptionCallback(presenter::onEpisodeOptionAction)) }
    private val characterAdapter by lazy { DetailsCharacterAdapter(imageLoader, AnimeItemCallback(presenter::onAction)) }
    private val animeAdapter by lazy { AnimeAdapter(rateResourceProvider, characterAdapter, AnimeItemCallback(presenter::onAction)) }

    private var isCommentsPage: Boolean = false

    @InjectPresenter
    lateinit var animePresenter: AnimePresenter

    @ProvidePresenter
    fun providePresenter(): AnimePresenter {
        animePresenter = presenterProvider.get()
        parentFragment.ifNotNull {
            animePresenter.localRouter = (it as RouterProvider).localRouter
        }

        arguments.ifNotNull {
            presenter.setAnimeId(it.getLong(AppExtras.ARGUMENT_ANIME_ID))
        }

        return animePresenter
    }

    companion object {
        @JvmStatic
        fun newInstance(id: Long) = AnimeFragment().withArgs { putLong(AppExtras.ARGUMENT_ANIME_ID, id) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(fragmentLayout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        view_pager.adapter = pagerAdapter
        view_pager.addOnPageChangeListener(pageChangeListener)

        toolbar?.addBackButton()
        toolbar?.setNavigationOnClickListener { presenter.onBackPressed() }

        image_background.setOnClickListener { presenter.onBackgroundImageClicked() }
        app_bar.addOnOffsetChangedListener(onOffsetChangedListener)

        with(view_network_error) {
            setText(R.string.common_error_message_without_pull)
            gone()
        }

        presenter.onEpisodesRefresh()
    }

    private val pageChangeListener = object : ViewPager.SimpleOnPageChangeListener() {
        override fun onPageSelected(position: Int) {
            isCommentsPage = AnimeDetailsPage.COMMENTS.isEqualPage(position)
        }
    }

    private val onOffsetChangedListener = AppBarLayout.OnOffsetChangedListener { _, verticalOffset -> subtitle.alpha = (1 - Math.abs(verticalOffset.toDouble() / 1.5 / 100.0)).toFloat() }

    override fun onDestroyView() {
        view_pager.adapter = null
        view_pager.removeOnPageChangeListener(pageChangeListener)
        app_bar.removeOnOffsetChangedListener(onOffsetChangedListener)
        image_background.setOnClickListener(null)
        super.onDestroyView()
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    override fun getPresenter(): AnimePresenter = animePresenter

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

    override fun setAnimeData(details: AnimeDetailsViewModel) {
        imageLoader.setImageWithFit(image_background, details.imageUrl)
        collapsing_toolbar.title = details.title
        subtitle.text = details.subTitle
        pagerAdapter.setData(converter.convertFromViewModel(details))
    }

    override fun showEpisodeList(isEpisodeReversed: Boolean, episodes: List<BaseEpisodeItem>) {
        pagerAdapter.showEpisodeList(isEpisodeReversed, episodes)
    }

    override fun reverseEpisodes() = pagerAdapter.reverseEpisodes()
    override fun setPage(position: Int) = view_pager.setCurrentItem(position, true)
    override fun showComments(baseCommentItems: List<BaseItem>) = pagerAdapter.showComments(baseCommentItems)
    override fun insetMoreComments(baseCommentItems: List<BaseItem>) = pagerAdapter.insertMoreComments(baseCommentItems)
    override fun onShowPageLoading() = pagerAdapter.showPageLoading()
    override fun onHidePageLoading() = pagerAdapter.hidePageLoading()
    override fun onShowRefresh() = pagerAdapter.onShowRefresh()
    override fun onHideRefresh() = pagerAdapter.onHideRefresh()

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

    override fun showPlayWizard(translationTypes: MutableList<TranslationType>?) {
        if (translationTypes != null) {
            val items = translationTypes.map { Utils.firstUpperCase(it.type) }

            if (context != null && activity?.isFinishing == false) {
                MaterialDialog(context!!.dialogContext())
                        .show {
                            listItems(items = items) { _, pos, _ ->
                                presenter.onTranslationTypeChoosed(translationTypes[pos])
                                if (userSettings.getRememberType()) {
                                    putSetting(SettingsExtras.TRANSLATION_TYPE, translationTypes[pos].type)
                                }
                            }
                            checkBoxPrompt(R.string.common_remember) { b -> putSetting(SettingsExtras.TRANSLATION_TYPE_REMEMBER, b) }
                        }
            }
        }
    }

    override fun showLinksDialog(links: List<LinkViewModel>) {
        context.ifNotNull { context ->
            MaterialDialog(context.dialogContext())
                    .show {
                        title(R.string.common_links)
                        listItems(items = links.map { it.name }) { _, pos, _ -> presenter.onLinkPressed(links[pos]) }
                    }

        }
    }

    override fun showChronologyDialog(nodes: List<FranchiseNode>) {
        context.ifNotNull { context ->
            val converter = FranchiseNodeToStringConverterImpl(context)
            MaterialDialog(context.dialogContext())
                    .show {
                        title(R.string.chronology)
                        listItems(items = converter.convertList(nodes)) { _, pos, _ ->
                            if (nodes.isNotEmpty()) {
                                presenter.onAnimeClicked(nodes[pos].id)
                            }
                        }
                    }
        }
    }

    override fun showRatesDialog(rate: UserRate?) {
        val dialog = RateDialogFragment.newInstance(Type.ANIME, rate)
        dialog.callback = object : RateDialogFragment.RateDialogCallback {
            override fun onSaveAnimeRate(rate: UserRate) = presenter.onSaveRate(rate)
            override fun onDeleteAnimeRate(id: Long) = presenter.onDeleteRate(id)
        }
        dialog.show(childFragmentManager, "RATES")
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

    inner class AnimePagerAdapter(
            private val commentsAdapter: CommentsAdapter,
            private val animeADapter: AnimeAdapter,
            private val episodeAdapter: EpisodeAdapter
    ) : ViewStatePagerAdapter() {

        private val screens = arrayOf(R.layout.fragment_comments, R.layout.fragment_anime, R.layout.fragment_series)

        private var commentsView: ViewGroup? = null
        private var animeView: ViewGroup? = null
        private var episodeView: ViewGroup? = null

        override fun getCount(): Int = screens.size

        override fun createView(container: ViewGroup?, position: Int): View {
            val inflater = LayoutInflater.from(container?.context)!!
            val layout = inflater.inflate(screens[position], container, false) as ViewGroup
            when (position) {
                0 -> createCommentsPage(layout)
                1 -> createAnimePage(layout)
                2 -> createSeriesPage(layout)
            }

            return layout
        }

        private fun createCommentsPage(layout: ViewGroup) {
            commentsView = layout
            commentsView.ifNotNull {
                with(it) {
                    with(list_comments) {
                        val manager = LinearLayoutManager(context)
                        itemAnimator = DefaultItemAnimator()
                        layoutManager = manager
                        adapter = commentsAdapter
                        addOnScrollListener(object : RecyclerView.OnScrollListener() {
                            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                                super.onScrolled(recyclerView, dx, dy)

                                val visibleItemPosition = manager.findLastCompletelyVisibleItemPosition() + 6
                                val itemCount = manager.itemCount - 1

                                if (visibleItemPosition >= itemCount) {
                                    presenter.loadNextPage()
                                }

                            }
                        })
                    }
                }
            }
        }

        private fun createAnimePage(layout: ViewGroup) {
            animeView = layout
            animeView.ifNotNull {
                with(it) {
                    with(anime_details_list) {
                        itemAnimator = DefaultItemAnimator()
                        layoutManager = LinearLayoutManager(context)
                        adapter = animeADapter
                    }
                }
            }
        }

        private fun createSeriesPage(layout: ViewGroup) {
            episodeView = layout
            episodeView.ifNotNull {
                with(it) {
                    refresh_layout.setOnRefreshListener { presenter.onEpisodesRefresh() }
                    this.findViewById<NetworkErrorView>(R.id.view_network_error)?.gone()
                    if (episodeAdapter.items.isEmpty()) {
                        refresh_layout.isRefreshing = true
                    }
                    with(list) {
                        itemAnimator = DefaultItemAnimator()
                        layoutManager = LinearStickyHead<EpisodeAdapter>(context)
                        addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                        adapter = episodeAdapter
                    }
                }
            }
        }

        fun setData(animeItems: List<BaseItem>) {
            animeADapter.bindItems(animeItems)
        }

        fun showEpisodeList(isEpisodeReversed: Boolean, episodes: List<BaseEpisodeItem>) {
            episodeAdapter.bindItems(isEpisodeReversed, episodes)
        }


        internal fun onHideRefresh() {
            episodeView?.refresh_layout?.isRefreshing = false
        }

        internal fun onShowRefresh() {
            episodeView?.refresh_layout?.isRefreshing = true
        }

        internal fun showComments(baseCommentItems: List<BaseItem>) {
            commentsAdapter.bindItems(baseCommentItems)
        }

        internal fun insertMoreComments(baseCommentItems: List<BaseItem>) {
            commentsAdapter.insertMore(baseCommentItems)
        }

        internal fun hidePageLoading() {
            commentsAdapter.hideProgress()
        }

        internal fun showPageLoading() {
            commentsAdapter.showProgress()
        }

        fun reverseEpisodes() {
            episodeAdapter.reverseItems()
        }

    }


}