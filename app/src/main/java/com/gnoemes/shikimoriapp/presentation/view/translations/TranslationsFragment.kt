package com.gnoemes.shikimoriapp.presentation.view.translations

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gnoemes.shikimoriapp.R
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.PlayerType
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.TranslationNavigationData
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.TranslationViewModel
import com.gnoemes.shikimoriapp.entity.app.data.SettingsExtras
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras
import com.gnoemes.shikimoriapp.entity.series.domain.VideoTrack
import com.gnoemes.shikimoriapp.presentation.presenter.translations.TranslationsPresenter
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider
import com.gnoemes.shikimoriapp.presentation.view.translations.adapter.TranslationItemCallback
import com.gnoemes.shikimoriapp.presentation.view.translations.adapter.TranslationsAdapter
import com.gnoemes.shikimoriapp.utils.*
import com.gnoemes.shikimoriapp.utils.kotlin.gone
import com.gnoemes.shikimoriapp.utils.kotlin.visible
import kotlinx.android.synthetic.main.fragment_translations.*
import kotlinx.android.synthetic.main.layout_default_list.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class TranslationsFragment : BaseFragment<TranslationsPresenter, TranslationsView>(), TranslationsView {

    @InjectPresenter
    lateinit var translationsPresenter: TranslationsPresenter

    @ProvidePresenter
    fun providePresenter(): TranslationsPresenter {
        translationsPresenter = presenterProvider.get()
        parentFragment.ifNotNull {
            translationsPresenter.localRouter = (it as RouterProvider).localRouter
        }

        arguments.ifNotNull {
            val data = it.getParcelable<TranslationNavigationData>(AppExtras.ARGUMENT_TRANSLATION_DATA)!!
            presenter.setEpisodeId(data.episodeId)
            presenter.setCurrentTranslationType(data.type)
            presenter.animeId = data.animeId
            presenter.rateId = data.rateId
        }

        return translationsPresenter
    }

    private val translationsAdapter by lazy {
        TranslationsAdapter(object : TranslationItemCallback {
            override fun onTranslationClicked(translation: TranslationViewModel?) = presenter.onTranslationClicked(translation)
            override fun onDownloadTranslation(translation: TranslationViewModel?) = presenter.onDownloadTranslation(translation)
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(data: TranslationNavigationData) = TranslationsFragment().withArgs { putParcelable(AppExtras.ARGUMENT_TRANSLATION_DATA, data) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    @SuppressLint("ResourceType")
    private fun initViews() {
        with(view_empty) {
            setText(R.string.translations_empty)
            setCallback { presenter.onFindAll() }
            gone()
        }

        view_network_error.gone()

        refresh_layout.setOnRefreshListener { presenter.loadTranslations() }

        with(list) {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(context)
            adapter = translationsAdapter
        }

        val icon = context?.themeDrawable(R.drawable.ic_settings, R.attr.colorText)

        toolbar?.addBackButton()
        toolbar?.inflateMenu(R.menu.menu_translations)
        toolbar?.menu?.getItem(0)?.icon = icon
        toolbar?.setNavigationOnClickListener { presenter.onBackPressed() }
        toolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_settings -> presenter.onSettingsClicked()
                else -> Unit
            }
            false
        }
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onPermissionDenied() {
        presenter.onPermissionDenied()
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onNeverAskAgain() {
        presenter.onNeverAskAgain()
    }

    @NeedsPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onPermissionGranted() {
        presenter.onPermissionGranted()
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    override fun getPresenter(): TranslationsPresenter = translationsPresenter

    override fun getFragmentLayout(): Int = R.layout.fragment_translations

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    override fun checkPermissions() {
        onPermissionGrantedWithPermissionCheck()
    }

    override fun showTranslations(translations: List<TranslationViewModel>) {
        list.visible()
        translationsAdapter.bindItems(translations)
    }

    override fun showSettingsDialog() {
        safeDialog { context ->
            MaterialDialog(context)
                    .show {
                        listItems(R.array.translations_type) { _, index, _ -> presenter.onTypeClicked(TranslationType.values().asList()[index]) }
                    }
        }
    }

    override fun showPlayerDialog(players: MutableList<PlayerType>?) {
        safeDialog { context ->
            MaterialDialog(context)
                    .show {
                        listItems(if (players?.size == 1) R.array.players_single else R.array.players) { _, pos, _ -> presenter.onPlay(players?.get(pos)) }
                    }
        }
    }

    override fun showQualityDialog(tracks: MutableList<VideoTrack>?) {
        context.ifNotNull { context ->
            val items = tracks?.map { it.resolution.toString() }
            MaterialDialog(context.dialogContext())
                    .show {
                        title(R.string.quality_title)
                        listItems(items = items) { _, pos, _ -> presenter.onQualityForExternalChoosed(tracks?.get(pos)?.url) }
                    }
        }
    }

    override fun showDownloadPathDialog() {
        safeDialog { context ->
            MaterialDialog(context)
                    .show {
                        listItems(R.array.download_paths) { _, index, _ ->
                            presenter.onDownloadPlaceTypeChoosed()
                            putSetting(SettingsExtras.DOWNLOAD_LOCATION_TYPE, index)
                        }
                    }
        }
    }

    override fun showEmptyView() {
        view_empty.visible()
        list.gone()
    }

    override fun hideEmptyView() = view_empty.gone()

    override fun showErrorView() {
        list.gone()
        view_network_error.visible()
    }

    override fun hideErrorView() = view_network_error.gone()

    override fun onShowLoading() {
        refresh_layout.isRefreshing = true
    }

    override fun onHideLoading() {
        refresh_layout.isRefreshing = false
    }


}