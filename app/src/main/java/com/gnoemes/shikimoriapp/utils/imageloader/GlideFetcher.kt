package com.gnoemes.shikimoriapp.utils.imageloader

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.github.piasy.biv.loader.ImageLoader
import com.gnoemes.shikimoriapp.utils.imageloader.glide.GlideProgressSupport
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.ConcurrentHashMap


class GlideFetcher(context: Context,
                   client: OkHttpClient?
) : ImageLoader {
    private val mRequestManager: RequestManager
    private val mRequestTargetMap = ConcurrentHashMap<Int, ImageDownloadTarget>()

    init {
        GlideProgressSupport.init(Glide.get(context), client)
        mRequestManager = Glide.with(context)
    }

    companion object {
        @JvmStatic
        fun with(context: Context, client: OkHttpClient? = null) = GlideFetcher(context, client)
    }

    override fun loadImage(requestId: Int, uri: Uri?, callback: ImageLoader.Callback?) {
        val target = object : ImageDownloadTarget(uri?.toString()) {

            override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                super.onResourceReady(resource, transition)
                callback?.onCacheHit(0, resource)
                callback?.onSuccess(resource)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                super.onLoadFailed(errorDrawable)
                callback?.onFail(GlideLoaderException(errorDrawable))
            }

            override fun onDownloadStart() {
                callback?.onStart()
            }

            override fun onProgress(progress: Int) {
                callback?.onProgress(progress)
            }

            override fun onDownloadFinish() {
                callback?.onFinish()
            }
        }
        clearTarget(requestId)
        saveTarget(requestId, target)

        mRequestManager
                .downloadOnly()
                .load(uri)
                .into(target)
    }

    private fun saveTarget(requestId: Int, target: ImageDownloadTarget) {
        mRequestTargetMap[requestId] = target
    }

    private fun clearTarget(requestId: Int) {
        val target = mRequestTargetMap.remove(requestId)
        if (target != null) {
            mRequestManager.clear(target)
        }
    }

    override fun prefetch(uri: Uri?) {
        mRequestManager
                .downloadOnly()
                .load(uri)
                .into(object : SimpleTarget<File>() {
                    override fun onResourceReady(resource: File,
                                                 transition: Transition<in File>?) {
                        // not interested in result
                    }
                })
    }

    override fun cancel(requestId: Int) = clearTarget(requestId)

    internal abstract class ImageDownloadTarget(private val url: String?
    ) : SimpleTarget<File>(), GlideProgressSupport.ProgressListener {

        override fun onResourceReady(resource: File, transition: Transition<in File>?) = GlideProgressSupport.forget(url)

        override fun onLoadStarted(placeholder: Drawable?) {
            super.onLoadStarted(placeholder)
            GlideProgressSupport.expect(url, this)
        }

        override fun onLoadFailed(errorDrawable: Drawable?) {
            super.onLoadFailed(errorDrawable)
            GlideProgressSupport.forget(url)
        }

        override fun onLoadCleared(placeholder: Drawable?) {
            super.onLoadCleared(placeholder)
            GlideProgressSupport.forget(url)
        }
    }

    inner class GlideLoaderException(val errorDrawable: Drawable?) : RuntimeException()

}