package com.gnoemes.shikimoriapp.presentation.presenter.search

import android.text.TextUtils
import com.arellomobile.mvp.InjectViewState
import com.gnoemes.shikimoriapp.domain.app.AnalyticsInteractor
import com.gnoemes.shikimoriapp.domain.search.SearchInteractor
import com.gnoemes.shikimoriapp.entity.anime.domain.Anime
import com.gnoemes.shikimoriapp.entity.anime.domain.Genre
import com.gnoemes.shikimoriapp.entity.app.domain.*
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem
import com.gnoemes.shikimoriapp.entity.manga.domain.Manga
import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem
import com.gnoemes.shikimoriapp.entity.search.domain.SearchConstants
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter
import com.gnoemes.shikimoriapp.presentation.presenter.common.ViewController
import com.gnoemes.shikimoriapp.presentation.presenter.search.converter.AnimeViewModelConverter
import com.gnoemes.shikimoriapp.presentation.presenter.search.converter.CharacterConverter
import com.gnoemes.shikimoriapp.presentation.presenter.search.converter.MangaViewModelConverter
import com.gnoemes.shikimoriapp.presentation.presenter.search.converter.PersonConverter
import com.gnoemes.shikimoriapp.presentation.view.search.SearchView
import com.gnoemes.shikimoriapp.presentation.view.search.filter.FilterResourceProvider
import com.gnoemes.shikimoriapp.utils.appendLoadingLogic
import com.gnoemes.shikimoriapp.utils.ifNotNull
import javax.inject.Inject

@InjectViewState
class SearchPresenter @Inject constructor(
        private val interactor: SearchInteractor,
        private val analyticsInteractor: AnalyticsInteractor,
        private val animeConverter: AnimeViewModelConverter,
        private val mangaConverter: MangaViewModelConverter,
        private val characterConverter: CharacterConverter,
        private val personConverter: PersonConverter,
        private val resourceProvider: FilterResourceProvider
) : BaseNetworkPresenter<SearchView>() {

    private var paginator: SearchPaginator? = null
    var filters = HashMap<String, MutableList<FilterItem>>()

    var reactiveQuery: String? = null
    var type: Type = Type.ANIME
    var genre: Genre? = null

    private val supportsPagination = arrayListOf(Type.ANIME, Type.MANGA, Type.RANOBE)

    override fun initData() {
        viewState.hideEmptyView()
        viewState.hideNetworkError()
        paginator = SearchPaginatorImpl(interactor, viewController)

        if (genre == null) {
            initPaginator()
        } else {
            viewState.addBackButton()
            viewState.hideSpinner()
            onGenreSearch()
            initPaginator()
        }
    }

    private fun initPaginator() {
        paginator = SearchPaginatorImpl(interactor, viewController)
        paginator?.setType(type)
        paginator?.setFilters(filters)
        paginator?.refresh()
    }

    private fun onGenreSearch() {
        val id = if (type == Type.ANIME) genre?.animeId else genre?.mangaId
        filters[SearchConstants.GENRE] = mutableListOf(FilterItem(SearchConstants.GENRE, id, genre?.russianName))
    }

    fun loadNextPage() {
        paginator?.setFilters(filters)
        paginator?.loadNewPage()
    }

    fun onRefresh() {
        if (supportsPagination.contains(type)) {
            destroyPaginator()
            initPaginator()
        } else {
            when (type) {
                Type.CHARACTER -> loadCharacters()
                Type.PERSON -> loadPersons()
                else -> Unit
            }
        }
    }

    private fun loadPersons() {
        interactor.loadPersonListWithFilters(filters)
                .map { personConverter.convertListFrom(it) }
                .doOnSubscribe { viewState.hideEmptyView() }
                .doOnSubscribe { viewState.hideEmptyListView() }
                .appendLoadingLogic(viewState)
                .subscribe(this::setData, this::processErrors)
                .addToDisposables()
    }

    private fun loadCharacters() {
        interactor.loadCharacterListWithFilters(filters)
                .map { characterConverter.convertListFrom(it) }
                .doOnSubscribe { viewState.hideEmptyView() }
                .doOnSubscribe { viewState.hideEmptyListView() }
                .appendLoadingLogic(viewState)
                .subscribe(this::setData, this::processErrors)
                .addToDisposables()
    }

    private fun setData(list: List<BaseItem>) {
        if (list.isNotEmpty()) {
            viewState.showList(list)
        } else {
            viewState.showEmptyView()
        }
    }

    fun setSearchQuery(query: String?) {
        if (!TextUtils.isEmpty(query)) {
            analyticsInteractor.logEvent(AnalyticsEvent.MANUAL_SEARCH)
            filters[SearchConstants.SEARCH] = mutableListOf(FilterItem(SearchConstants.SEARCH, query, null))
        } else {
            filters.remove(SearchConstants.SEARCH)
        }
    }

    fun setSearchQuery() {
        setSearchQuery(reactiveQuery)
        onRefresh()
    }

    private fun showList(list: MutableList<LinkedContent>?) {
        paginator.ifNotNull { searchPaginator ->
            if (searchPaginator.isFirstPage()) {
                when (type) {
                    Type.ANIME -> {
                        val items = list?.map { it as Anime }
                        viewState.showList(animeConverter.convertListFrom(items))
                    }
                    Type.MANGA -> {
                        val items = list?.map { it as Manga }
                        viewState.showList(mangaConverter.convertListFrom(items))
                    }
                    Type.RANOBE -> {
                        val items = list?.map { it as Manga }
                        viewState.showList(mangaConverter.convertListFrom(items))
                    }
                    else -> Unit
                }
            } else {
                when (type) {
                    Type.ANIME -> {
                        val items = list?.map { it as Anime }
                        viewState.insetMore(animeConverter.convertListFrom(items))
                    }
                    Type.MANGA -> {
                        val items = list?.map { it as Manga }
                        viewState.insetMore(mangaConverter.convertListFrom(items))
                    }
                    Type.RANOBE -> {
                        val items = list?.map { it as Manga }
                        viewState.insetMore(mangaConverter.convertListFrom(items))
                    }
                    else -> Unit
                }
            }
        }
    }


    override fun processErrors(throwable: Throwable) {
        when (throwable) {
            is BaseException -> {
                when (throwable.tag) {
                    ServiceCodeException.TAG -> processServiceException(throwable)
                    NetworkException.TAG -> with(viewState) { showNetworkError(); clearList() }
                    else -> super.processErrors(throwable)
                }
            }
            else -> super.processErrors(throwable)
        }
    }

    private fun processServiceException(throwable: Throwable) {
        val exception = throwable as ServiceCodeException
        when (exception.serviceCode) {
            HttpStatusCode.UNPROCESSABLE_ENTITY -> {
                viewState.showEmptyView()
                viewState.hideEmptyListView()
                viewState.clearList()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyPaginator()
    }

    private fun destroyPaginator() {
        paginator?.release()
        paginator = null
    }

    fun onFilterClicked() {
        val typeFilters = when (type) {
            Type.ANIME -> resourceProvider.animeFilters
            Type.MANGA -> resourceProvider.mangaFilters
            Type.RANOBE -> resourceProvider.ranobeFilters
            else -> HashMap()
        }

        viewState.showFilterDialog(type, typeFilters, filters)
    }

    fun onFiltersSelected(filters: HashMap<String, MutableList<FilterItem>>) {
        this.filters = filters
        paginator?.setType(type)
        paginator?.setFilters(filters)
        paginator?.restart()
        viewState.closeFilterDialog()
    }

    private fun clearFiltersAndRefresh() {
        onFiltersSelected(HashMap())
    }

    fun onChangeType(type: Type) {
        this.type = type
        viewState.setSpinnerPosition(type)
        viewState.hideEmptyListView()

        if (supportsPagination.contains(type)) {
            clearFiltersAndRefresh()
            viewState.showFilterButton()
        } else {
            with(viewState) {
                clearList()
                showEmptyListView()
                hideFilterButton()
            }
        }
    }

    fun onFilterClosed() {
        viewState.closeFilterDialog()
    }

    private val viewController: ViewController<LinkedContent> = object : ViewController<LinkedContent> {
        override fun showEmptyError(show: Boolean, throwable: Throwable?) {
            if (show) {
                throwable.ifNotNull { processErrors(it) }
            } else {
                with(viewState) {
                    clearList()
                    hideEmptyView()
                    hideNetworkError()
                }
            }
        }

        override fun showEmptyView(show: Boolean) {
            if (show) {
                viewState.showEmptyView()
                viewState.clearList()
            } else {
                viewState.hideEmptyView()
            }
        }

        override fun showList(show: Boolean, list: MutableList<LinkedContent>?) {
            if (show) {
                this@SearchPresenter.showList(list)
            } else {
                viewState.clearList()
            }
        }

        override fun showRefreshProgress(show: Boolean) {
            if (show) {
                viewState.onShowLoading()
            } else {
                viewState.onHideLoading()
            }
        }

        override fun showPageProgress(show: Boolean) {
            if (show) {
                viewState.onShowPageLoading()
            } else {
                viewState.onHidePageLoading()
            }
        }

        override fun showEmptyProgress(show: Boolean) {
            if (show) {
                viewState.onShowLoading()
            } else {
                viewState.onHideLoading()
            }
        }

        override fun showError(throwable: Throwable) {
            processErrors(throwable)
        }
    }
}

