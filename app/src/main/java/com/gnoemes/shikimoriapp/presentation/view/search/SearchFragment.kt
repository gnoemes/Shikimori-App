package com.gnoemes.shikimoriapp.presentation.view.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.transition.Fade
import android.support.transition.TransitionManager
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import com.allattentionhere.fabulousfilter.AAH_FabulousFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gnoemes.shikimoriapp.R
import com.gnoemes.shikimoriapp.data.local.preferences.UserSettingsSource
import com.gnoemes.shikimoriapp.entity.app.domain.Type
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras.ARGUMENT_SEARCH_DATA
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem
import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem
import com.gnoemes.shikimoriapp.entity.search.presentation.SearchNavigationData
import com.gnoemes.shikimoriapp.presentation.presenter.search.SearchPresenter
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider
import com.gnoemes.shikimoriapp.presentation.view.search.adapter.SearchAdapter
import com.gnoemes.shikimoriapp.presentation.view.search.filter.FilterDialogFragment
import com.gnoemes.shikimoriapp.utils.*
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader
import com.gnoemes.shikimoriapp.utils.kotlin.gone
import com.gnoemes.shikimoriapp.utils.kotlin.visible
import com.santalu.respinner.ReSpinner
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
            val data = it.getParcelable(ARGUMENT_SEARCH_DATA) as? SearchNavigationData
            searchPresenter.genre = data?.genre
            searchPresenter.type = data?.type ?: Type.ANIME
        }

        return searchPresenter
    }

    @Inject
    lateinit var userSettings: UserSettingsSource

    @Inject
    lateinit var imageLoader: ImageLoader

    private val searchAdapter by lazy { SearchAdapter(userSettings, imageLoader, presenter::onContentClicked) }
    private lateinit var searchView: com.lapism.searchview.SearchView
    private lateinit var spinner: ReSpinner
    private var dialogFragment: FilterDialogFragment? = null

    companion object {
        @JvmStatic
        fun newInstance(data: SearchNavigationData?) = SearchFragment().withArgs { putParcelable(AppExtras.ARGUMENT_SEARCH_DATA, data) }
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
        viewEmptyList.gone()
        viewEmptyList.setText(R.string.search_only_name)
        btn_filter.setOnClickListener { presenter.onFilterClicked() }
    }

    private fun initSearchView() {
        toolbar.ifNotNull {
            spinner = ReSpinner(context)
            spinner.adapter = ArrayAdapter<String>(it.context, R.layout.item_spinner_normal, it.context.resources.getStringArray(R.array.search_types))
            val backgroundDrawable = spinner.background
            backgroundDrawable.tint(it.context.colorAttr(R.attr.colorAccent))
            spinner.background = backgroundDrawable
            spinner.setOnItemClickListener { _, _, pos, _ ->
                when (pos) {
                    0 -> presenter.onChangeType(Type.ANIME)
                    1 -> presenter.onChangeType(Type.MANGA)
                    2 -> presenter.onChangeType(Type.RANOBE)
                    3 -> presenter.onChangeType(Type.CHARACTER)
                    4 -> presenter.onChangeType(Type.PERSON)
                }
            }

            searchView = com.lapism.searchview.SearchView(it.context)
            it.title = null
            it.addView(spinner)
            it.addView(searchView)
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
                toolbar.ifNotNull { toolbar ->
                    TransitionManager.beginDelayedTransition(toolbar, Fade())
                    spinner.visible()
                    if (spinner.parent != toolbar) toolbar.setTitle(R.string.common_search)
                    toggleMenu(true)!!
                            .setOnMenuItemClickListener {
                                searchView.open(true)
                                false
                            }
                }
                return false
            }

            override fun onOpen(): Boolean {
                toolbar.ifNotNull { _ ->
                    spinner.gone()
                    toggleMenu(false)!!
                            .setOnMenuItemClickListener {
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
                presenter.reactiveQuery = newText
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
            presenter.onFiltersSelected(result as HashMap<String, List<FilterItem>>)
        }
    }

    override fun getAppliedFilters(): HashMap<String, List<FilterItem>> = presenter.filters

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    override fun showFilterDialog(filters: HashMap<String, MutableList<FilterItem>>?) {

        if (dialogFragment == null || !dialogFragment!!.isAdded) {
            dialogFragment = FilterDialogFragment.newInstance()
            dialogFragment?.setParentFab(btn_filter)
            dialogFragment?.show(childFragmentManager, dialogFragment?.tag)
        }
    }

    override fun showList(items: MutableList<BaseItem>) {
        list.visible()
        searchAdapter.bindItems(items)
    }

    override fun setSpinnerPosition(type: Type) {
        val pos = when (type) {
            Type.ANIME -> 0
            Type.MANGA -> 1
            Type.RANOBE -> 2
            Type.CHARACTER -> 3
            Type.PERSON -> 4
            else -> 0
        }
        spinner.setSelection(pos, false)
    }

    override fun hideSpinner() {
        toolbar?.removeView(spinner)
        toolbar?.setTitle(R.string.common_search)
    }

    override fun showEmptyListView() = viewEmptyList.visible()

    override fun hideEmptyListView() = viewEmptyList.gone()

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
        toolbar?.setNavigationOnClickListener { presenter.onBackPressed() }
    }
}