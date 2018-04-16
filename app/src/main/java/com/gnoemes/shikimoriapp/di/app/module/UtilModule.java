package com.gnoemes.shikimoriapp.di.app.module;

import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.imageloader.PicassoImageLoaderImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public interface UtilModule {

    @Binds
    @Singleton
    ImageLoader bindImageLoader(PicassoImageLoaderImpl picassoImageLoader);
}
