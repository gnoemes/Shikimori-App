package com.gnoemes.shikimoriapp;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatDelegate;

import com.crashlytics.android.Crashlytics;
import com.gnoemes.shikimoriapp.di.app.component.DaggerAppComponent;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import net.danlew.android.joda.JodaTimeAndroid;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.fabric.sdk.android.Fabric;

public class App extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        JodaTimeAndroid.init(this);
        DaggerAppComponent.builder().create(this).inject(this);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .showImageOnFail(R.drawable.missing_original)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .delayBeforeLoading(0)
                .displayer(new FadeInBitmapDisplayer(500, true, true, true))
                .handler(new Handler())
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCache(new LruMemoryCache(5 * 1024 * 1024))
                .memoryCacheSize(5 * 1024 * 1024)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
//                .denyCacheImageMultipleSizesInMemory()
                .threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY)
                .defaultDisplayImageOptions(options)
                .build();

        ImageLoader.getInstance().init(config);

//        Picasso.setSingletonInstance(new Picasso.Builder(getApplicationContext())
//                .memoryCache(new LruMemoryCache(5 * 1024 * 1024))
//                .defaultBitmapConfig(Bitmap.Config.ARGB_8888)
//                .loggingEnabled(true)
//                .indicatorsEnabled(true)
//                .build());
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

}

