package com.gnoemes.shikimoriapp.presentation.view.alternative.episodes

import android.os.Bundle
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gnoemes.shikimoriapp.R
import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslationType
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodeItem
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodeOptionAction
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodeOptionsItem
import com.gnoemes.shikimoriapp.entity.app.data.SettingsExtras
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem
import com.gnoemes.shikimoriapp.presentation.presenter.alternative.AlternativeEpisodesPresenter
import com.gnoemes.shikimoriapp.presentation.view.alternative.episodes.adapter.EpisodeAlternativeAdapterDelegate
import com.gnoemes.shikimoriapp.presentation.view.alternative.episodes.adapter.EpisodeAlternativeOptionAdapterDelegate
import com.gnoemes.shikimoriapp.presentation.view.alternative.episodes.provider.AlternativeEpisodeResourceProvider
import com.gnoemes.shikimoriapp.presentation.view.common.adapter.BaseListAdapter
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider
import com.gnoemes.shikimoriapp.presentation.view.main.provider.AdapterResourceProvider
import com.gnoemes.shikimoriapp.utils.addBackButton
import com.gnoemes.shikimoriapp.utils.ifNotNull
import com.gnoemes.shikimoriapp.utils.putSetting
import com.gnoemes.shikimoriapp.utils.view.LinearStickyHead
import com.gnoemes.shikimoriapp.utils.view.StickyHeaders
import com.gnoemes.shikimoriapp.utils.withArgs
import kotlinx.android.synthetic.main.fragment_series.*
import kotlinx.android.synthetic.main.layout_default_list.*
import javax.inject.Inject

class AlternativeEpisodesFragment : BaseFragment<AlternativeEpisodesPresenter, AlternativeEpisodesView>(), AlternativeEpisodesView {

    @InjectPresenter
    lateinit var alternativePresenter: AlternativeEpisodesPresenter

    @ProvidePresenter
    fun provideAlternativePresenter(): AlternativeEpisodesPresenter {
        alternativePresenter = presenterProvider.get()
        alternativePresenter.setLocalRouter((parentFragment as? RouterProvider)?.localRouter)

        arguments.ifNotNull {
            alternativePresenter.animeId = it.getLong(AppExtras.ARGUMENT_ANIME_ID)
        }
        return alternativePresenter
    }

    @Inject
    lateinit var resourceProvider: AlternativeEpisodeResourceProvider

    private val episodeAdapter by lazy {
        EpisodeAdapter(
                resourceProvider,
                alternativePresenter::onEpisodeClicked,
                alternativePresenter::onEpisodeOptionAction
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(animeId: Long) = AlternativeEpisodesFragment().withArgs { putLong(AppExtras.ARGUMENT_ANIME_ID, animeId) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        refreshLayout.setOnRefreshListener { getPresenter().loadEpisodes() }
        with(list) {
            layoutManager = LinearStickyHead<EpisodeAdapter>(context)
            adapter = episodeAdapter
        }

        toolbar.addBackButton()
        toolbar.setNavigationOnClickListener { getPresenter().onBackPressed() }

        with(networkErrorView) {
            setText(R.string.common_error_message_without_pull)
            visibility = View.GONE
        }

        getPresenter().loadEpisodes()
    }


    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    override fun getPresenter(): AlternativeEpisodesPresenter = alternativePresenter


    override fun getFragmentLayout(): Int = R.layout.fragment_series

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    override fun showList(episodes: MutableList<BaseItem>?) {
        episodeAdapter.bindItems(episodes)
    }

    override fun showTranslationDialog(types: MutableList<AlternativeTranslationType>) {
        context.ifNotNull {
            MaterialDialog(it)
                    .listItems(R.array.translations_type_alternative, null, null, false) { _: MaterialDialog, index: Int, _: String -> alternativePresenter.onTranslationChoosed(types[index]) }
                    .checkBoxPrompt(R.string.common_remember) { checked -> putSetting(SettingsExtras.REMEMBER_TRANSLATION_TYPE_ALTERNATIVE, checked) }
                    .show()

        }
    }

    override fun hideErrorView() {
        networkErrorView.visibility = View.GONE
    }

    override fun showErrorView() {
        networkErrorView.visibility = View.VISIBLE
    }

    internal class EpisodeAdapter(resourceProvider: AdapterResourceProvider?,
                                  callback: ((EpisodeItem) -> Unit),
                                  optionCallback: ((EpisodeOptionAction, EpisodeItem) -> Unit)
    ) : BaseListAdapter(resourceProvider), StickyHeaders {

        init {
            delegatesManager.addDelegate(EpisodeAlternativeAdapterDelegate(callback))
            delegatesManager.addDelegate(EpisodeAlternativeOptionAdapterDelegate(optionCallback))

        }

        override fun isStickyHeader(position: Int): Boolean {
            return items[position] is EpisodeOptionsItem
        }
    }

}