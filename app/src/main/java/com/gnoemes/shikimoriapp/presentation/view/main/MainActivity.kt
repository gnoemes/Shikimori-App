package com.gnoemes.shikimoriapp.presentation.view.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gnoemes.shikimoriapp.R
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras
import com.gnoemes.shikimoriapp.entity.main.domain.Constants
import com.gnoemes.shikimoriapp.entity.main.presentation.BottomScreens
import com.gnoemes.shikimoriapp.presentation.presenter.main.MainPresenter
import com.gnoemes.shikimoriapp.presentation.view.bottom.BottomTabContainer
import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseActivity
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider
import com.gnoemes.shikimoriapp.presentation.view.main.provider.MainResourceProvider
import com.gnoemes.shikimoriapp.utils.colorAttr
import com.gnoemes.shikimoriapp.utils.ifNotNull
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.layout_bottom_bar.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.SupportAppNavigator
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject

class MainActivity : BaseActivity<MainPresenter, MainView>(), MainView, RouterProvider {

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter = presenterProvider.get()

    @Inject
    lateinit var localNavigatorHolder: NavigatorHolder

    @Inject
    lateinit var resourceProvider: MainResourceProvider

    private val tabs = arrayOf(
            Tab(R.id.tab_favorite, R.string.common_favorite, R.drawable.ic_star, BottomScreens.FAVORITE),
            Tab(R.id.tab_calendar, R.string.common_calendar, R.drawable.ic_calendar, BottomScreens.CALENDAR),
            Tab(R.id.tab_search, R.string.common_search, R.drawable.ic_search, BottomScreens.SEARCH),
            Tab(R.id.tab_social, R.string.common_main, R.drawable.ic_group, BottomScreens.SOCIAL),
            Tab(R.id.tab_menu, R.string.common_menu, R.drawable.ic_menu, BottomScreens.MENU)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContainers()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putInt(AppExtras.ARGUMENT_SELECT_TAB, bottomNav.checked)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (bottomNav != null && savedInstanceState != null) {
            bottomNav.check(savedInstanceState.getInt(AppExtras.ARGUMENT_SELECT_TAB))
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    @LayoutRes
    override fun getLayoutActivity(): Int = R.layout.activity_main

    override fun getNavigator(): Navigator = object : SupportAppNavigator(this@MainActivity, fragmentManager, R.id.activity_container) {

        override fun createActivityIntent(context: Context?, screenKey: String?, data: Any?): Intent? = null
        override fun createFragment(screenKey: String?, data: Any?): Fragment? = null
        override fun showSystemMessage(message: String?) {
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }

        override fun replace(command: Replace) {
            testAnalytics(command.screenKey)
            val fm = fragmentManager
            val ta = fm.beginTransaction()
            tabs.forEach { tab ->
                val fragment = fm.findFragmentByTag(tab.screenKey)!!
                if (tab.screenKey == command.screenKey) {
                    if (fragment.isDetached) {
                        ta.attach(fragment)
                    }
                    ta.show(fragment)
                } else {
                    ta.hide(fragment)
                }
            }
            ta.commitNow()
        }

        var canExit = false

        override fun exit() {
            if (!canExit) {
                presenter.router.showSystemMessage(resourceProvider.exitMessage)
                canExit = true
                Handler().postDelayed({ canExit = false }, Constants.EXIT_TIMEOUT)
            } else {
                finish()
            }
        }
    }

    private fun testAnalytics(screenKey: String?) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, screenKey)
        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }

    override fun getNavigatorHolder(): NavigatorHolder = localNavigatorHolder

    override fun getPresenter() = mainPresenter

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    override fun onShowLoading() {}
    override fun onHideLoading() {}
    override fun setTitle(title: String) {}
    override fun initToolbar() {}

    override fun initBottomNavigation() {
        val items = mutableListOf<INavigationBarItem>()

        tabs.forEach { tab ->
            items.add(createItem(tab))
        }

        with(bottomNav) {
            setItemWidthFixed(true)
            add(*items.toTypedArray())
            check(tabs.first().id)
            setOnCheckChangedListener { oldIdRes, newIdRes ->
                val tab = tabs.first { it.id == newIdRes }
                if (oldIdRes == newIdRes) {
                    when (tab.screenKey) {
                        BottomScreens.FAVORITE -> presenter.onFavoriteReSelected()
                        BottomScreens.CALENDAR -> presenter.onCalendarReSelected()
                        BottomScreens.SEARCH -> presenter.onSearchReSelected()
                        BottomScreens.SOCIAL -> presenter.onSocialReSelected()
                        BottomScreens.MENU -> presenter.onMenuReSelected()
                    }
                } else {
                    when (tab.screenKey) {
                        BottomScreens.FAVORITE -> presenter.onFavoriteSelected()
                        BottomScreens.CALENDAR -> presenter.onCalendarSelected()
                        BottomScreens.SEARCH -> presenter.onSearchSelected()
                        BottomScreens.SOCIAL -> presenter.onSocialSelected()
                        BottomScreens.MENU -> presenter.onMenuSelected()
                    }
                }
            }
        }
    }

    private fun createItem(tab: Tab): INavigationBarItem = object : AbsNavigationBarItem() {
        override fun getColorInt(isChecked: Boolean): Int? = this@MainActivity.colorAttr(R.attr.colorBottomAccent)
        override fun getDrawableIdRes(isChecked: Boolean): Int = tab.icon
        override fun getText(isChecked: Boolean): String? = this@MainActivity.getString(tab.title)
        override fun getIdRes(): Int = tab.id
        override fun getTextColorInt(isChecked: Boolean): Int = 0
    }

    override fun clearMenuBackStack() {
        clearBackStack(BottomScreens.MENU)
    }

    override fun clearSocialBackStack() {
        clearBackStack(BottomScreens.SOCIAL)
    }

    override fun clearSearchBackStack() {
        clearBackStack(BottomScreens.SEARCH)
    }

    override fun clearCalendarBackStack() {
        clearBackStack(BottomScreens.CALENDAR)
    }

    override fun clearFavoriteBackStack() {
        clearBackStack(BottomScreens.FAVORITE)
    }

    ///////////////////////////////////////////////////////////////////////////
    // METHODS
    ///////////////////////////////////////////////////////////////////////////

    private fun clearBackStack(screenKey: String) {
        val fm = fragmentManager
        val fragment: Fragment? = fm.findFragmentByTag(screenKey)
        fragment.ifNotNull {
            localRouter.backTo(null)
        }
    }

    private fun initContainers() {
        val fm = fragmentManager
        val ta = fm.beginTransaction()
        tabs.forEach { tab ->
            var fragment: Fragment? = fm.findFragmentByTag(tab.screenKey)
            if (fragment == null) {
                fragment = BottomTabContainer.newInstance()
                ta.add(R.id.activity_container, fragment, tab.screenKey)
                        .detach(fragment)
                        .commitNow()
            }
        }
        ta.commitNow()
    }


    override fun getLocalRouter(): Router = presenter.router

    override fun getLocalNavigator(): Navigator = navigator

    data class Tab(
            val id: Int,
            val title: Int,
            val icon: Int,
            val screenKey: String
    )
}
