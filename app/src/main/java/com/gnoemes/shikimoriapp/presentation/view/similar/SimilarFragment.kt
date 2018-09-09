package com.gnoemes.shikimoriapp.presentation.view.similar

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gnoemes.shikimoriapp.R
import com.gnoemes.shikimoriapp.data.local.preferences.UserSettingsSource
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem
import com.gnoemes.shikimoriapp.presentation.presenter.similar.SimilarPresenter
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider
import com.gnoemes.shikimoriapp.presentation.view.search.adapter.SearchAdapter
import com.gnoemes.shikimoriapp.utils.*
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader
import com.gnoemes.shikimoriapp.utils.kotlin.gone
import com.gnoemes.shikimoriapp.utils.kotlin.visible
import kotlinx.android.synthetic.main.fragment_similar.*
import kotlinx.android.synthetic.main.layout_default_list.*
import javax.inject.Inject

class SimilarFragment : BaseFragment<SimilarPresenter, SimilarView>(), SimilarView {

    @InjectPresenter
    lateinit var similarPresenter: SimilarPresenter

    @ProvidePresenter
    fun providePresenter(): SimilarPresenter {
        similarPresenter = presenterProvider.get()

        parentFragment.ifNotNull {
            similarPresenter.setLocalRouter((it as RouterProvider).localRouter)
        }

        arguments.ifNotNull {
            presenter.setAnimeId(it.getLong(AppExtras.ARGUMENT_ANIME_ID))
        }

        return similarPresenter
    }

    @Inject
    lateinit var settings: UserSettingsSource
    @Inject
    lateinit var imageLoader: ImageLoader

    private val searchAdapter by lazy { SearchAdapter(settings, imageLoader, presenter::onAnimeClicked) }

    companion object {
        @JvmStatic
        fun newInstance(id: Long) = SimilarFragment().withArgs { putLong(AppExtras.ARGUMENT_ANIME_ID, id) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val layout = GridLayoutManager(context, context!!.calculateColumns(R.dimen.image_big_width))
        with(list) {
            layoutManager = layout
            adapter = searchAdapter
            itemAnimator = DefaultItemAnimator()
        }

        view_network_error.gone()
        with(view_empty) {
            gone()
            setText(R.string.similar_empty)
        }

        with(refresh_layout) {
            setColorSchemeColors(context.colorAttr(R.attr.colorAccent))
            setOnRefreshListener(presenter::onRefresh)
        }
        toolbar.ifNotNull { it ->
            it.setNavigationOnClickListener { presenter::onBackPressed }
            it.addBackButton()
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    override fun getPresenter(): SimilarPresenter = similarPresenter

    override fun getFragmentLayout(): Int = R.layout.fragment_similar

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    override fun showList(items: MutableList<BaseItem>) {
        searchAdapter.bindItems(items)
        list.visible()
    }

    override fun hideList() = list.gone()

    override fun showNetworkError() = view_network_error.visible()

    override fun hideNetworkError() = view_network_error.gone()

    override fun showEmptyView() = view_empty.visible()

    override fun hideEmptyView() = view_empty.gone()

    override fun onShowLoading() {
        refresh_layout.isRefreshing = true
    }

    override fun onHideLoading() {
        refresh_layout.isRefreshing = false
    }
}