package com.gnoemes.shikimoriapp.presentation.view.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import com.allattentionhere.fabulousfilter.AAH_FabulousFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gnoemes.shikimoriapp.R
import com.gnoemes.shikimoriapp.data.local.preferences.UserSettingsSource
import com.gnoemes.shikimoriapp.entity.anime.domain.Genre
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem
import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem
import com.gnoemes.shikimoriapp.presentation.presenter.search.SearchPresenter
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider
import com.gnoemes.shikimoriapp.presentation.view.search.adapter.SearchAdapter
import com.gnoemes.shikimoriapp.presentation.view.search.filter.FilterDialogFragment
import com.gnoemes.shikimoriapp.presentation.view.search.provider.SearchAnimeResourceProvider
import com.gnoemes.shikimoriapp.utils.*
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader
import com.gnoemes.shikimoriapp.utils.kotlin.gone
import com.gnoemes.shikimoriapp.utils.kotlin.visible
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.layout_default_list.*
import javax.inject.Inject

class SearchFragment : BaseFragment<SearchPresenter, SearchView>(), SearchView, AAH_FabulousFragment.Callbacks, FilterDialogFragment.FilterProvider {

    @InjectPresenter
    lateinit var searchPresenter: SearchPresenter

    @ProvidePresenter
    fun providePresenter(): SearchPresenter {
        searchPresenter = presenterProvider.get()
        parentFragment.ifNotNull {
            searchPresenter.setLocalRouter((it as RouterProvider).localRouter)
        }
        arguments.ifNotNull {
            searchPresenter.setGenre(it.getSerializable(AppExtras.ARGUMENT_GENRE) as? Genre)
        }

        return searchPresenter
    }

    @Inject
    lateinit var userSettings: UserSettingsSource

    @Inject
    lateinit var resourceProvider: SearchAnimeResourceProvider

    @Inject
    lateinit var imageLoader: ImageLoader

    private val searchAdapter by lazy { SearchAdapter(userSettings, resourceProvider, imageLoader, presenter::onItemClicked) }
    private lateinit var searchView: com.lapism.searchview.SearchView
    private var dialogFragment: FilterDialogFragment? = null

    companion object {
        @JvmStatic
        fun newInstance(data: Genre?) = SearchFragment().withArgs { putSerializable(AppExtras.ARGUMENT_GENRE, data) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        dialogFragment.ifNotNull {
            outState.putBoolean(AppExtras.ARGUMENT_DIALOG, it.isAdded)
            it.dismiss()
            dialogFragment = null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            val dialog = savedInstanceState.getBoolean(AppExtras.ARGUMENT_DIALOG)
            if (dialog) {
                dialogFragment = FilterDialogFragment.newInstance()
                dialogFragment.ifNotNull {
                    it.setParentFab(btn_filter)
                    it.show(childFragmentManager, it.tag)
                }
            }
        }
        initList()
        initSearchView()
    }

    private fun initList() {
        val layout = GridLayoutManager(context, context!!.calculateColumns(R.dimen.image_big_width))
        with(list) {
            layoutManager = layout
            adapter = searchAdapter
            itemAnimator = DefaultItemAnimator()
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val visibleItemPosition = layout.findLastCompletelyVisibleItemPosition() + 6
                    val itemCount = layout.itemCount - 1

                    if (visibleItemPosition >= itemCount) {
                        presenter.loadNextPage()
                    }

                }
            })
        }

        with(refresh_layout) {
            setColorSchemeColors(context.colorAttr(R.attr.colorAccent))
            setOnRefreshListener(presenter::onRefresh)
        }

        btn_filter.setOnClickListener { presenter.onFilterPressed() }
    }

    private fun initSearchView() {
        if (toolbar != null) {
            searchView = com.lapism.searchview.SearchView(toolbar!!.context)
            toolbar!!.addView(searchView)
        }

        view_search_empty.setText(R.string.search_nothing)
        with(searchView) {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setHint(R.string.common_search)
            setVoice(false)
            setShadow(false)
            version = com.lapism.searchview.SearchView.VERSION_MENU_ITEM
            setVersionMargins(com.lapism.searchview.SearchView.VERSION_MARGINS_MENU_ITEM)
            shouldHideOnKeyboardClose = true
        }
        searchView.setOnOpenCloseListener(object : com.lapism.searchview.SearchView.OnOpenCloseListener {
            override fun onClose(): Boolean {
                if (toolbar != null) {
                    toolbar!!.setTitle(R.string.common_search)
                    toggleMenu(true)!!
                            .setOnMenuItemClickListener { item ->
                                searchView.open(true)
                                false
                            }
                }
                return false
            }

            override fun onOpen(): Boolean {
                if (toolbar != null) {
                    toolbar!!.title = null
                    toggleMenu(false)!!
                            .setOnMenuItemClickListener { item ->
                                presenter.setSearchQuery()
                                searchView.close(false)
                                false
                            }
                }
                return false
            }
        })
        searchView.setOnQueryTextListener(object : com.lapism.searchview.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                presenter.setSearchQuery(query)
                presenter.onRefresh()
                searchView.close(true)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                presenter.setSearchReactive(newText)
                return false
            }
        })

        toolbar!!.inflateMenu(R.menu.menu_search)
        toggleMenu(true)
    }

    @SuppressLint("ResourceType")
    private fun toggleMenu(search: Boolean): MenuItem? {
        if (toolbar != null && context != null) {
            val menuItem = toolbar!!.menu.getItem(0)
            menuItem.icon = context?.themeDrawable(if (search) R.drawable.ic_search else R.drawable.ic_check, R.attr.colorText)
            return menuItem
        }
        return null
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    override fun getPresenter(): SearchPresenter = searchPresenter

    override fun getFragmentLayout(): Int = R.layout.fragment_search

    ///////////////////////////////////////////////////////////////////////////
    // CALLBACKS
    ///////////////////////////////////////////////////////////////////////////

    override fun onResult(result: Any) {
        dialogFragment.ifNotNull {
            if (result.toString().equals("swiped_down", ignoreCase = true)) {
                it.dismiss()
            }
        }

        if (result is HashMap<*, *>) {
            presenter.onFiltersSelected(result as HashMap<String, MutableList<FilterItem>>)
        }
    }

    override fun getAppliedFilters(): HashMap<String, MutableList<FilterItem>> = presenter.filters

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    override fun showFilterDialog(filters: HashMap<String, MutableList<FilterItem>>?) {
        val dialog = dialogFragment.takeIf { it == null || !it.isAdded }

        if (dialog == null) {
            dialogFragment = FilterDialogFragment.newInstance()
            dialogFragment?.setParentFab(btn_filter)
            dialogFragment?.show(childFragmentManager, dialogFragment?.tag)
        }
    }

    override fun showList(items: MutableList<BaseItem>) {
        list.visible()
        searchAdapter.bindItems(items)
    }

    override fun insetMore(items: MutableList<BaseItem>) = searchAdapter.insertMore(items)

    override fun hideList() = list.gone()

    override fun clearList() = searchAdapter.clearList()

    override fun showNetworkError() = view_network_error.visible()

    override fun hideNetworkError() = view_network_error.gone()

    override fun showEmptyView() = view_search_empty.visible()

    override fun hideEmptyView() = view_search_empty.gone()

    override fun onShowPageLoading() = searchAdapter.showPageLoading()

    override fun onHidePageLoading() = searchAdapter.hidePageLoading()

    override fun onShowLoading() {
        refresh_layout.isRefreshing = true
    }

    override fun onHideLoading() {
        refresh_layout.isRefreshing = false
    }

    override fun addBackButton() {
        toolbar?.addBackButton()
    }
}
