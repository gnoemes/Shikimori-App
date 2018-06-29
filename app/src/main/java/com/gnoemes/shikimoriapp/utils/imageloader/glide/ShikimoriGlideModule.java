package com.gnoemes.shikimoriapp.utils.imageloader.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.InputStream;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

@GlideModule
public class ShikimoriGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, 50 * 1024 * 1024));
        builder.setDefaultRequestOptions(new RequestOptions().format(DecodeFormat.PREFER_RGB_565));
        builder.setDefaultTransitionOptions(Drawable.class, DrawableTransitionOptions.withCrossFade());
        builder.setLogLevel(Log.DEBUG);
        builder.setMemoryCache(new LruResourceCache(5L * 1024 * 1024));
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        OkHttpClient client = new OkHttpClient.Builder().cache(new Cache(new File(context.getCacheDir(), "net_cache"), 5L * 1024 * 1024)).build();
//        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(new NetworkHelper(context).getClient()));
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
    }
}
