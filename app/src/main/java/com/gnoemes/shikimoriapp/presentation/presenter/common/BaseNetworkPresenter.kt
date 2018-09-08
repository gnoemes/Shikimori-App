package com.gnoemes.shikimoriapp.presentation.presenter.common

import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseNetworkView
import com.gnoemes.shikimoriapp.utils.rx.ErrorUtils
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseNetworkPresenter<View : BaseNetworkView> : BaseNavigationPresenter<View>() {

    private var compositeDisposable = CompositeDisposable()

    override fun initData() {}

    override fun onDestroy() {
        compositeDisposable.clear()
    }

    fun Disposable.addToDisposables() {
        compositeDisposable.add(this)
    }

    protected fun unsubscribeOnDestroy(@NonNull disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    protected open fun processErrors(throwable: Throwable) {
        val errorUtils = ErrorUtils()
        errorUtils.processErrors(throwable, router, viewState)
    }
}