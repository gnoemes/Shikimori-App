package com.gnoemes.shikimoriapp.presentation.view.calendar

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gnoemes.shikimoriapp.R
import com.gnoemes.shikimoriapp.entity.calendar.presentation.CalendarItemViewModel
import com.gnoemes.shikimoriapp.presentation.presenter.calendar.CalendarPresenter
import com.gnoemes.shikimoriapp.presentation.view.calendar.adapter.CalendarAdapter
import com.gnoemes.shikimoriapp.presentation.view.calendar.adapter.provider.CalendarAnimeResourceProvider
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider
import com.gnoemes.shikimoriapp.utils.date.converter.DateTimeConverter
import com.gnoemes.shikimoriapp.utils.ifNotNull
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader
import com.gnoemes.shikimoriapp.utils.kotlin.gone
import com.gnoemes.shikimoriapp.utils.kotlin.visible
import com.gnoemes.shikimoriapp.utils.themeDrawable
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.layout_default_list.*
import javax.inject.Inject

class CalendarFragment : BaseFragment<CalendarPresenter, CalendarView>(), CalendarView {

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var dateTimeConverter: DateTimeConverter

    @Inject
    lateinit var resourceProvider: CalendarAnimeResourceProvider

    @InjectPresenter
    lateinit var calendarPresenter: CalendarPresenter

    @ProvidePresenter
    fun providePresenter(): CalendarPresenter {
        calendarPresenter = presenterProvider.get()

        parentFragment.ifNotNull {
            presenter.setLocalRouter((it as RouterProvider).localRouter)
        }

        return calendarPresenter
    }

    companion object {
        @JvmStatic
        fun newInstance() = CalendarFragment()
    }

    private val calendarAdapter by lazy { CalendarAdapter(dateTimeConverter, resourceProvider, imageLoader, CalendarAdapter.AnimeListener { presenter.onAnimeClicked(it) }) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    @SuppressLint("ResourceType")
    private fun initViews() {
        with(list) {
            layoutManager = LinearLayoutManager(context)
            adapter = calendarAdapter
        }

        toolbar.ifNotNull { toolbar ->
            with(toolbar) {
                val icon = context?.themeDrawable(R.drawable.ic_tune, R.attr.colorText)

                inflateMenu(R.menu.menu_calendar)
                menu?.getItem(0)?.icon = icon
                setNavigationOnClickListener { presenter.onBackPressed() }
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.item_my_ongoings -> presenter.onFilterClicked()
                        else -> Unit
                    }
                    false
                }
            }
        }

        view_network_error.gone()


        with(refresh_layout) {
            setColorSchemeColors(Color.RED)
            setOnRefreshListener { presenter.loadCalendar() }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    override fun getPresenter(): CalendarPresenter = calendarPresenter

    @LayoutRes
    override fun getFragmentLayout(): Int = R.layout.fragment_calendar


    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    override fun showCalendarData(items: MutableList<CalendarItemViewModel>?) {
        list.visible()
        calendarAdapter.bindItems(items)
    }

    override fun hideList() = list.gone()

    override fun showNetworkErrorView() = view_network_error.visible()

    override fun hideNetworkErrorView() = view_network_error.gone()

    override fun onShowLoading() {
        refresh_layout.isRefreshing = true
    }

    override fun onHideLoading() {
        refresh_layout.isRefreshing = false
    }


    ///////////////////////////////////////////////////////////////////////////
    // INNER CLASS
    ///////////////////////////////////////////////////////////////////////////

//    inner class MangaPagerAdapter(
//            private val calendarAdapter: CalendarAdapter,
////            private val chaptersAdapter: ChaptersAdapter
//    ) : ViewStatePagerAdapter() {
//
//    }

}