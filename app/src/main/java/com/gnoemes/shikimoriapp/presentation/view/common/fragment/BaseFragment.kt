package com.gnoemes.shikimoriapp.presentation.view.common.fragment

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatFragment
import com.gnoemes.shikimoriapp.R
import com.gnoemes.shikimoriapp.presentation.presenter.common.BasePresenter
import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseNetworkView
import com.gnoemes.shikimoriapp.utils.inputMethodManager
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import javax.inject.Provider

abstract class BaseFragment<Presenter : BasePresenter<View>, View : BaseNetworkView> : MvpAppCompatFragment(), BaseFragmentView {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @Inject
    protected lateinit var presenterProvider: Provider<Presenter>

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): android.view.View? {
        val view = inflater.inflate(R.layout.fragment_base, container, false)
//        return if (getFragmentLayout() != android.view.View.NO_ID) {
//            inflater.inflate(getFragmentLayout(), view.findViewById(R.id.fragment_content), true)
//        } else {
        return view
//        }
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (getView() != null) {
            ButterKnife.bind(this, getView()!!)
        }

        if (::toolbar.isInitialized) {
            toolbar.visibility = android.view.View.VISIBLE
        }
    }

    override fun onDestroyView() {
        hideSoftInput()
        super.onDestroyView()
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Get current presenter
     */
    protected abstract fun getPresenter(): Presenter

    @LayoutRes
    protected abstract fun getFragmentLayout(): Int

    ///////////////////////////////////////////////////////////////////////////
    // UI METHODS
    ///////////////////////////////////////////////////////////////////////////

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                getPresenter().onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun hideSoftInput() {
        activity?.let {
            val focus = it.currentFocus
            if (focus != null) {
                val inputMethodManager = it.inputMethodManager()
                inputMethodManager?.hideSoftInputFromWindow(focus.windowToken, 0)
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    override fun setTitle(title: String) {
        if (::toolbar.isInitialized) {
            toolbar.title = title
        }

    }

    override fun setTitle(title: Int) {
        if (::toolbar.isInitialized) {
            toolbar.setTitle(title)
        }
    }

    override fun onBackPressed() = getPresenter().onBackPressed()

    override fun onShowLoading() {}

    override fun onHideLoading() {}

    override fun initToolbar() {}

    override fun onShowError(error: String?) {}
}