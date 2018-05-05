package com.gnoemes.shikimoriapp;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatDelegate;

import com.gnoemes.shikimoriapp.di.app.component.DaggerAppComponent;
import com.gnoemes.shikimoriapp.utils.imageloader.LruMemoryCache;
import com.squareup.picasso.Picasso;

import net.danlew.android.joda.JodaTimeAndroid;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class App extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
        DaggerAppComponent.builder().create(this).inject(this);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

//        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .cacheInMemory(true)
//                .resetViewBeforeLoading(true)
//                .cacheOnDisk(true)
//                .bitmapConfig(Bitmap.Config.ARGB_8888)
//                .displayer(new FadeInBitmapDisplayer(500, true, true, true))
//                .handler(new Handler())
//                .build();
//
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
//                .memoryCache(new LruMemoryCache(5 * 1024 * 1024))
//                .memoryCacheSize(5 * 1024 * 1024)
//                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
//                .denyCacheImageMultipleSizesInMemory()
//                .threadPoolSize(5)
//                .threadPriority(Thread.NORM_PRIORITY)
//                .defaultDisplayImageOptions(options)
//                .writeDebugLogs()
//                .build();
//
//        ImageLoader.getInstance().init(config);

        Picasso.setSingletonInstance(new Picasso.Builder(getApplicationContext())
                .memoryCache(new LruMemoryCache(5 * 1024 * 1024))
                .defaultBitmapConfig(Bitmap.Config.ARGB_8888)
                .loggingEnabled(true)
                .indicatorsEnabled(true)
                .build());
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

}

