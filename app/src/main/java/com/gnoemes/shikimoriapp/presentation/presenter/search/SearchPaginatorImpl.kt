package com.gnoemes.shikimoriapp.presentation.presenter.search

import com.gnoemes.shikimoriapp.domain.search.SearchInteractor
import com.gnoemes.shikimoriapp.entity.app.domain.LinkedContent
import com.gnoemes.shikimoriapp.entity.app.domain.Type
import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem
import com.gnoemes.shikimoriapp.presentation.presenter.common.ViewController
import com.gnoemes.shikimoriapp.utils.ifNotNull
import io.reactivex.disposables.Disposable

class SearchPaginatorImpl(private val interactor: SearchInteractor,
                          private val viewController: ViewController<LinkedContent>
) : SearchPaginator {
    private var defaultPage = 1
    private var currentPage = 1

    private var currentState: State<LinkedContent> = EMPTY()
    private var currentData: MutableList<LinkedContent> = mutableListOf()
    private var currentType: Type = Type.ANIME
    var currentFilters: HashMap<String, MutableList<FilterItem>>? = null
    private var disposable: Disposable? = null

    interface State<T> {
        fun restart() = Unit
        fun refresh() = Unit
        fun loadNewPage() = Unit
        fun release() = Unit
        fun newData(list: List<T>) = Unit
        fun error(throwable: Throwable) = Unit
    }

    private fun loadPage(page: Int) {
        when (currentType) {
            Type.ANIME -> loadAnime(page)
            Type.MANGA -> loadManga(page)
            Type.RANOBE -> loadRanobe(page)
        }
    }

    private fun loadRanobe(page: Int) {
        disposable = interactor.loadRanobeListWithFilters(currentFilters, page)
                .subscribe(currentState::newData, currentState::error)
    }

    private fun loadManga(page: Int) {
        disposable = interactor.loadMangaListWithFilters(currentFilters, page)
                .subscribe(currentState::newData, currentState::error)
    }

    private fun loadAnime(page: Int) {
        disposable = interactor.loadAnimeListWithFilters(currentFilters, page)
                .subscribe(currentState::newData, currentState::error)
    }

    private fun unsubscribe() {
        disposable.ifNotNull {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }

    override fun setFilters(filters: HashMap<String, MutableList<FilterItem>>) {
        this.currentFilters = filters
    }

    override fun setType(type: Type) {
        currentType = type
    }

    override fun isFirstPage(): Boolean = currentPage == defaultPage
    override fun loadNewPage() = currentState.loadNewPage()
    override fun restart() = currentState.restart()
    override fun refresh() = currentState.refresh()
    override fun release() = currentState.release()
    private fun increasePage() {
        currentPage += 1
    }

    private inner class EMPTY : State<LinkedContent> {
        override fun refresh() {
            currentState = EMPTY_PROGRESS()
            viewController.showEmptyProgress(true)
            loadPage(defaultPage)
        }

        override fun release() {
            currentState = RELEASED()
            unsubscribe()
        }
    }

    private inner class EMPTY_PROGRESS : State<LinkedContent> {
        override fun restart() {
            loadPage(defaultPage)
        }

        override fun newData(list: List<LinkedContent>) {
            if (!list.isEmpty()) {
                currentState = DATA()
                currentData.clear()
                currentData = list.toMutableList()
                currentPage = defaultPage
                viewController.showList(true, currentData)
                viewController.showEmptyView(false)
                viewController.showEmptyProgress(false)
                viewController.showRefreshProgress(false)
            } else {
                currentState = EMPTY_DATA()
                viewController.showEmptyProgress(false)
                viewController.showRefreshProgress(false)
                viewController.showEmptyView(true)
            }
        }

        override fun error(throwable: Throwable) {
            currentState = EMPTY_ERROR()
            viewController.showEmptyProgress(false)
            viewController.showEmptyError(true, throwable)
            viewController.showRefreshProgress(false)
        }

        override fun release() {
            currentState = RELEASED()
            unsubscribe()
        }
    }

    private inner class EMPTY_ERROR : State<LinkedContent> {
        override fun restart() {
            currentState = EMPTY_PROGRESS()
            viewController.showEmptyError(false, null)
            viewController.showEmptyProgress(true)
            loadPage(defaultPage)
        }

        override fun refresh() {
            currentState = EMPTY_PROGRESS()
            viewController.showEmptyError(false, null)
            viewController.showEmptyProgress(true)
            loadPage(defaultPage)
        }

        override fun release() {
            currentState = RELEASED()
            unsubscribe()
        }
    }

    private inner class EMPTY_DATA : State<LinkedContent> {
        override fun restart() {
            currentState = EMPTY_PROGRESS()
            viewController.showEmptyView(false)
            viewController.showRefreshProgress(false)
            viewController.showEmptyProgress(true)
            loadPage(defaultPage)

        }

        override fun refresh() {
            currentState = EMPTY_PROGRESS()
            viewController.showEmptyView(false)
            viewController.showEmptyProgress(true)
            loadPage(defaultPage)

        }

        override fun release() {
            currentState = RELEASED()
            unsubscribe()
        }
    }

    private inner class DATA : State<LinkedContent> {
        override fun restart() {
            currentState = EMPTY_PROGRESS()
            viewController.showList(false, null)
            viewController.showRefreshProgress(false)
            viewController.showEmptyProgress(true)
            loadPage(defaultPage)
        }

        override fun refresh() {
            currentState = REFRESH()
            viewController.showRefreshProgress(true)
            loadPage(defaultPage)
        }

        override fun loadNewPage() {
            currentState = PAGE_PROGRESS()
            viewController.showPageProgress(true)
            increasePage()
            loadPage(currentPage)

        }

        override fun release() {
            currentState = RELEASED()
            unsubscribe()
        }

        override fun error(throwable: Throwable) {
            viewController.showError(throwable)
        }
    }

    private inner class REFRESH : State<LinkedContent> {
        override fun restart() {
            currentState = EMPTY_PROGRESS()
            viewController.showList(false, null)
            viewController.showRefreshProgress(false)
            viewController.showEmptyProgress(true)
            loadPage(defaultPage)
        }

        override fun newData(list: List<LinkedContent>) {
            if (!list.isEmpty()) {
                currentState = DATA()
                currentData.clear()
                currentData = list.toMutableList()
                currentPage = defaultPage
                viewController.showRefreshProgress(false)
                viewController.showEmptyError(false, null)
                viewController.showList(true, currentData)
                viewController.showEmptyView(false)
            } else {
                currentState = EMPTY_DATA()
                currentData.clear()
                viewController.showList(false, null)
                viewController.showRefreshProgress(false)
                viewController.showEmptyView(true)
            }
        }

        override fun error(throwable: Throwable) {
            currentState = DATA()
            viewController.showRefreshProgress(false)
            viewController.showError(throwable)
        }

        override fun release() {
            currentState = RELEASED()
            unsubscribe()
        }
    }

    private inner class PAGE_PROGRESS : State<LinkedContent> {
        override fun restart() {
            currentState = EMPTY_PROGRESS()
            viewController.showList(false, null)
            viewController.showPageProgress(false)
            viewController.showEmptyProgress(true)
            loadPage(defaultPage)
        }

        override fun newData(list: List<LinkedContent>) {
            if (!list.isEmpty()) {
                currentState = DATA()
                currentData = list.toMutableList()
                viewController.showPageProgress(false)
                viewController.showList(true, currentData)
                viewController.showEmptyView(false)
            } else {
                currentState = ALL_DATA()
                viewController.showPageProgress(false)
            }

        }

        override fun refresh() {
            currentState = REFRESH()
            viewController.showPageProgress(false)
            viewController.showRefreshProgress(true)
            loadPage(defaultPage)
        }


        override fun error(throwable: Throwable) {
            currentState = DATA()
            viewController.showPageProgress(false)
            viewController.showError(throwable)
        }

        override fun release() {
            currentState = RELEASED()
            unsubscribe()
        }
    }

    private inner class ALL_DATA : State<LinkedContent> {
        override fun restart() {
            currentState = EMPTY_PROGRESS()
            viewController.showList(false, null)
            viewController.showEmptyProgress(true)
            loadPage(defaultPage)

        }

        override fun refresh() {
            currentState = REFRESH()
            viewController.showRefreshProgress(true)
            loadPage(defaultPage)

        }

        override fun release() {
            currentState = RELEASED()
            unsubscribe()
        }
    }

    private inner class RELEASED : State<LinkedContent>

}