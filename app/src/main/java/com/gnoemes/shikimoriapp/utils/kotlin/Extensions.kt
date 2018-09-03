package com.gnoemes.shikimoriapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.support.annotation.AttrRes
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.view.ContextThemeWrapper
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gnoemes.shikimoriapp.BuildConfig
import com.gnoemes.shikimoriapp.R
import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseView
import io.reactivex.Single

fun String.appendHostIfNeed(): String {
    return if (this.contains("http")) this else BuildConfig.ShikimoriBaseUrl + this
}

fun String.firstUpperCase(): String? {
    return if (TextUtils.isEmpty(this)) null else this.substring(0, 1).toUpperCase() + this.substring(1)
}

fun String.toUri() = Uri.parse(this)

fun Context.inflateLayout(layoutResId: Int): View =
        inflateView(this, layoutResId, null, false)

fun Context.inflateLayout(layoutResId: Int, parent: ViewGroup): View =
        inflateLayout(layoutResId, parent, true)

fun Context.inflateLayout(layoutResId: Int, parent: ViewGroup, attachToRoot: Boolean): View =
        inflateView(this, layoutResId, parent, attachToRoot)

fun Context.drawable(@DrawableRes drawableResId: Int): Drawable? {
    return ContextCompat.getDrawable(this, drawableResId)
}

fun Context.themeDrawable(@DrawableRes drawableResId: Int, @AttrRes attrResId: Int): Drawable? {
    val drawable = ContextCompat.getDrawable(this, drawableResId)
    drawable?.tint(this.colorAttr(attrResId))
    return drawable
}

fun Context.dialogContext(): Context {
    return ContextThemeWrapper(this, R.style.DialogStyle)
}

fun Drawable.tint(colorResId: Int) {
    DrawableCompat.setTint(this, colorResId)
}

fun ViewGroup.inflate(@LayoutRes layoutResId: Int, attachToRoot: Boolean): View =
        inflateView(context, layoutResId, this, attachToRoot)

@SuppressLint("ResourceType")
fun Toolbar.addBackButton() {
    this.navigationIcon = this.context.themeDrawable(R.drawable.ic_arrow_back, R.attr.colorText)
}

fun View.onClick(l: (v: android.view.View?) -> Unit) {
    setOnClickListener(l)
}

fun <T> Single<T>.appendLoadingLogic(viewState: BaseView): Single<T> =
        this.doOnSubscribe { viewState.onShowLoading() }
                .doAfterTerminate { viewState.onHideLoading() }
                .doOnEvent { _, _ -> viewState.onHideLoading() }

inline fun <T : Fragment> T.withArgs(
        argsBuilder: Bundle.() -> Unit): T =
        this.apply {
            arguments = Bundle().apply(argsBuilder)
        }

fun <T : Any> T?.ifNotNull(f: (it: T) -> Unit) {
    if (this != null) f(this)
}


private fun inflateView(context: Context, layoutResId: Int, parent: ViewGroup?,
                        attachToRoot: Boolean): View =
        LayoutInflater.from(context).inflate(layoutResId, parent, attachToRoot)