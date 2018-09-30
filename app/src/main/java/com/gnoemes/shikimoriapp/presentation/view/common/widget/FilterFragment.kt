package com.gnoemes.shikimoriapp.presentation.view.common.widget

import android.os.Bundle
import android.support.transition.AutoTransition
import android.support.transition.TransitionManager
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Checkable
import android.widget.SpinnerAdapter
import com.arellomobile.mvp.MvpAppCompatFragment
import com.gnoemes.shikimoriapp.R
import com.gnoemes.shikimoriapp.entity.app.domain.Type
import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem
import com.gnoemes.shikimoriapp.entity.search.domain.SearchConstants
import com.gnoemes.shikimoriapp.presentation.view.search.filter.FilterResourceConverter
import com.gnoemes.shikimoriapp.presentation.view.search.filter.FilterResourceConverterImpl
import com.gnoemes.shikimoriapp.presentation.view.search.filter.FilterResourceProvider
import com.gnoemes.shikimoriapp.presentation.view.search.filter.FilterResourceProviderImpl
import com.gnoemes.shikimoriapp.utils.*
import com.santalu.respinner.ReSpinner
import com.thoughtbot.expandablecheckrecyclerview.CheckableChildRecyclerViewAdapter
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup
import com.thoughtbot.expandablecheckrecyclerview.models.MultiCheckExpandableGroup
import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import kotlinx.android.synthetic.main.item_expandable.view.*
import kotlinx.android.synthetic.main.item_multichoice.view.*
import kotlinx.android.synthetic.main.view_filter.*
import kotlin.collections.set

class FilterFragment : MvpAppCompatFragment() {

    var state: Bundle = Bundle()
    private var spinner: ReSpinner? = null
    var appliedFilters: HashMap<String, MutableList<FilterItem>> = HashMap()
    internal var callback: FilterCallback? = null

    companion object {
        const val STATE_KEY = "FILTER_STATE_KEY"
        const val CONTENT_TYPE_KEY = "CONTENT_TYPE_KEY"
        fun newInstance(type: Type) = FilterFragment().withArgs { putSerializable(CONTENT_TYPE_KEY, type) }
    }

    private var filterAdapter: FilterAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun getLayout(): Int = R.layout.view_filter

    private fun initData() {
        spinner = ReSpinner(context)
        val backgroundDrawable = spinner?.background
        backgroundDrawable?.tint(context!!.colorAttr(R.attr.colorAccent))
        spinner?.background = backgroundDrawable
        with(toolbar) {
            setTitle(R.string.filter_sort_title)
            addView(spinner)
        }
        with(list) {
            adapter = FilterAdapter(emptyList())
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        apply.setOnClickListener { callback?.onResult(appliedFilters) }
        reset.setOnClickListener { callback?.onResult(HashMap()) }

        val resourceConverter: FilterResourceConverter = FilterResourceConverterImpl()
        val resourceProvider: FilterResourceProvider = FilterResourceProviderImpl(context!!, resourceConverter)

        val type = arguments?.let { it.getSerializable(CONTENT_TYPE_KEY) as Type } ?: Type.ANIME
        when (type) {
            Type.ANIME -> {
                initAnimeFilters(resourceProvider.animeFilters, appliedFilters)
            }
            Type.MANGA -> {
                initMangaFilters(resourceProvider.mangaFilters, appliedFilters)
            }
            Type.RANOBE -> {
                initMangaFilters(resourceProvider.ranobeFilters, appliedFilters)
            }
            else -> Unit
        }
    }

    private fun initAnimeFilters(filters: HashMap<String, MutableList<FilterItem>>, appliedFilters: HashMap<String, MutableList<FilterItem>>? = null) {
        appliedFilters.ifNotNull {
            this.appliedFilters = it
        }
        val items = convertToCheckGroup(filters)
        filterAdapter = FilterAdapter(items)
        list.adapter = filterAdapter

        spinner?.adapter = getSpinnerAdapter(R.array.anime_order)
        val value = appliedFilters?.get(SearchConstants.ORDER)?.find { it.action == SearchConstants.ORDER }?.value
        val selection = SearchConstants.ORDER_BY.values().find { it.equalsType(value) }
        clearAndAddToSelected(SearchConstants.ORDER, FilterItem(SearchConstants.ORDER, selection?.toString()
                ?: SearchConstants.ORDER_BY.POPULARITY.toString(), null))
        spinner?.setSelection(getAnimeOrderSelectionPosition(selection), false)
        spinner?.setOnItemClickListener { _, _, pos, _ ->
            when (pos) {
                0 -> clearAndAddToSelected(SearchConstants.ORDER, FilterItem(SearchConstants.ORDER, SearchConstants.ORDER_BY.NAME.toString(), null))
                1 -> clearAndAddToSelected(SearchConstants.ORDER, FilterItem(SearchConstants.ORDER, SearchConstants.ORDER_BY.AIRED_ON.toString(), null))
                2 -> clearAndAddToSelected(SearchConstants.ORDER, FilterItem(SearchConstants.ORDER, SearchConstants.ORDER_BY.TYPE.toString(), null))
                3 -> clearAndAddToSelected(SearchConstants.ORDER, FilterItem(SearchConstants.ORDER, SearchConstants.ORDER_BY.ID.toString(), null))
                4 -> clearAndAddToSelected(SearchConstants.ORDER, FilterItem(SearchConstants.ORDER, SearchConstants.ORDER_BY.STATUS.toString(), null))
                5 -> clearAndAddToSelected(SearchConstants.ORDER, FilterItem(SearchConstants.ORDER, SearchConstants.ORDER_BY.RANDOM.toString(), null))
                6 -> clearAndAddToSelected(SearchConstants.ORDER, FilterItem(SearchConstants.ORDER, SearchConstants.ORDER_BY.EPISODES.toString(), null))
                7 -> clearAndAddToSelected(SearchConstants.ORDER, FilterItem(SearchConstants.ORDER, SearchConstants.ORDER_BY.RANKED.toString(), null))
                8 -> clearAndAddToSelected(SearchConstants.ORDER, FilterItem(SearchConstants.ORDER, SearchConstants.ORDER_BY.POPULARITY.toString(), null))
            }
        }

        filterAdapter?.onRestoreInstanceState(state)
        state.clear()
    }

    private fun initMangaFilters(filters: HashMap<String, MutableList<FilterItem>>, appliedFilters: HashMap<String, MutableList<FilterItem>>? = null) {
        appliedFilters.ifNotNull {
            this.appliedFilters = it
        }
        val items = convertToCheckGroup(filters)
        filterAdapter = FilterAdapter(items)
        list.adapter = filterAdapter

        spinner?.adapter = getSpinnerAdapter(R.array.manga_order)
        val value = appliedFilters?.get(SearchConstants.ORDER)?.find { it.action == SearchConstants.ORDER }?.value
        val selection = SearchConstants.ORDER_BY.values().find { it.equalsType(value) }
        clearAndAddToSelected(SearchConstants.ORDER, FilterItem(SearchConstants.ORDER, selection?.toString()
                ?: SearchConstants.ORDER_BY.POPULARITY.toString(), null))
        spinner?.setSelection(getMangaOrderSelectionPosition(selection), false)
        spinner?.setOnItemClickListener { _, _, pos, _ ->
            when (pos) {
                0 -> clearAndAddToSelected(SearchConstants.ORDER, FilterItem(SearchConstants.ORDER, SearchConstants.ORDER_BY.NAME.toString(), null))
                1 -> clearAndAddToSelected(SearchConstants.ORDER, FilterItem(SearchConstants.ORDER, SearchConstants.ORDER_BY.AIRED_ON.toString(), null))
                2 -> clearAndAddToSelected(SearchConstants.ORDER, FilterItem(SearchConstants.ORDER, SearchConstants.ORDER_BY.ID.toString(), null))
                3 -> clearAndAddToSelected(SearchConstants.ORDER, FilterItem(SearchConstants.ORDER, SearchConstants.ORDER_BY.STATUS.toString(), null))
                4 -> clearAndAddToSelected(SearchConstants.ORDER, FilterItem(SearchConstants.ORDER, SearchConstants.ORDER_BY.RANDOM.toString(), null))
                5 -> clearAndAddToSelected(SearchConstants.ORDER, FilterItem(SearchConstants.ORDER, SearchConstants.ORDER_BY.VOLUMES.toString(), null))
                6 -> clearAndAddToSelected(SearchConstants.ORDER, FilterItem(SearchConstants.ORDER, SearchConstants.ORDER_BY.CHAPTERS.toString(), null))
                7 -> clearAndAddToSelected(SearchConstants.ORDER, FilterItem(SearchConstants.ORDER, SearchConstants.ORDER_BY.RANKED.toString(), null))
                8 -> clearAndAddToSelected(SearchConstants.ORDER, FilterItem(SearchConstants.ORDER, SearchConstants.ORDER_BY.POPULARITY.toString(), null))
            }
        }

        filterAdapter?.onRestoreInstanceState(state)
        state.clear()
    }

    private fun convertToCheckGroup(filters: HashMap<String, MutableList<FilterItem>>): List<CheckGroup> {
        return filters.map { CheckGroup(it.key, it.value) }
    }

    private fun clearAndAddToSelected(key: String, filterItem: FilterItem) {
        appliedFilters[key]?.clear()
        addToSelected(key, filterItem)
    }

    private fun addToSelected(key: String, value: FilterItem) {
        if (appliedFilters[key] != null && !appliedFilters[key]?.contains(value)!!) {
            appliedFilters[key]?.add(value)
        } else {
            val temp = ArrayList<FilterItem>()
            temp.add(value)
            appliedFilters[key] = temp
        }
    }

    private fun removeFromSelected(page: String, value: FilterItem) {
        if (appliedFilters[page]?.size == 1) {
            appliedFilters.remove(page)
        } else {
            appliedFilters[page]?.remove(value)
        }
    }

    private fun getSpinnerAdapter(stringArray: Int): SpinnerAdapter? {
        return ArrayAdapter(context!!, R.layout.item_spinner_small, context!!.resources.getStringArray(stringArray))
    }

    private fun getAnimeOrderSelectionPosition(selection: SearchConstants.ORDER_BY?): Int {
        return when (selection) {
            SearchConstants.ORDER_BY.NAME -> 0
            SearchConstants.ORDER_BY.AIRED_ON -> 1
            SearchConstants.ORDER_BY.TYPE -> 2
            SearchConstants.ORDER_BY.ID -> 3
            SearchConstants.ORDER_BY.STATUS -> 4
            SearchConstants.ORDER_BY.RANDOM -> 5
            SearchConstants.ORDER_BY.EPISODES -> 6
            SearchConstants.ORDER_BY.RANKED -> 7
            else -> 8
        }
    }

    private fun getMangaOrderSelectionPosition(selection: SearchConstants.ORDER_BY?): Int {
        return when (selection) {
            SearchConstants.ORDER_BY.NAME -> 0
            SearchConstants.ORDER_BY.AIRED_ON -> 1
            SearchConstants.ORDER_BY.ID -> 2
            SearchConstants.ORDER_BY.STATUS -> 3
            SearchConstants.ORDER_BY.RANDOM -> 4
            SearchConstants.ORDER_BY.VOLUMES -> 5
            SearchConstants.ORDER_BY.CHAPTERS -> 6
            SearchConstants.ORDER_BY.RANKED -> 7
            else -> 8
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        filterAdapter?.onSaveInstanceState(state)
        super.onSaveInstanceState(outState)
    }

    private inner class FilterAdapter(checkGroups: List<CheckGroup>) : CheckableChildRecyclerViewAdapter<CategoryViewHolder, ItemViewHolder>(checkGroups) {

        override fun onBindCheckChildViewHolder(holder: ItemViewHolder?, flatPosition: Int, group: CheckedExpandableGroup?, childIndex: Int) {
            val item = group?.items?.get(childIndex) as? FilterItem
            item.ifNotNull {
                holder?.itemView?.titleView?.text = it.localizedText
                holder?.itemView?.controlView?.isChecked = appliedFilters.containsKey(it.action) && appliedFilters[it.action]?.contains(item) ?: false
                holder?.setItem(it)
            }
        }

        override fun onBindGroupViewHolder(holder: CategoryViewHolder?, flatPosition: Int, group: ExpandableGroup<*>?) {
            val item = group as? CheckGroup
            holder?.itemView?.title?.text = item?.categoryName
        }

        override fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder = CategoryViewHolder(parent.inflate(R.layout.item_expandable))
        override fun onCreateCheckChildViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder = ItemViewHolder(parent.inflate(R.layout.item_multichoice))
    }

    private inner class CategoryViewHolder(view: View) : GroupViewHolder(view) {

        override fun collapse() {
            TransitionManager.beginDelayedTransition(view?.container!!, AutoTransition())
            itemView.arrow.rotation = 90f
        }

        override fun expand() {
            TransitionManager.beginDelayedTransition(view?.container!!, AutoTransition())
            itemView.arrow.rotation = 270f
        }
    }

    private inner class ItemViewHolder(view: View) : CheckableChildViewHolder(view) {
        private lateinit var item: FilterItem
        override fun getCheckable(): Checkable = itemView.controlView
        override fun onClick(v: View?) {
            itemView.controlView.toggle()
            if (itemView.controlView.isChecked) {
                addToSelected(item.action, item)
            } else {
                removeFromSelected(item.action, item)
            }
        }

        fun setItem(item: FilterItem) {
            this.item = item
        }
    }


    internal data class CheckGroup(
            val categoryName: String,
            val categoryItems: List<FilterItem>
    ) : MultiCheckExpandableGroup(categoryName, categoryItems)


    internal interface FilterCallback {
        fun onResult(filters: HashMap<String, MutableList<FilterItem>>)
    }
}