package com.gnoemes.shikimoriapp.di.app.module;

import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeResponseConverterImpl;
import com.gnoemes.shikimoriapp.utils.date.DateTimeUtils;
import com.gnoemes.shikimoriapp.utils.date.DateTimeUtilsImpl;
import com.gnoemes.shikimoriapp.utils.date.converter.DateTimeConverter;
import com.gnoemes.shikimoriapp.utils.date.converter.DateTimeConverterImpl;
import com.gnoemes.shikimoriapp.utils.date.provider.DateTimeResourceProvider;
import com.gnoemes.shikimoriapp.utils.date.provider.DateTimeResourceProviderImpl;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.imageloader.PicassoImageLoaderImpl;
import com.gnoemes.shikimoriapp.utils.rx.ErrorProcessing;
import com.gnoemes.shikimoriapp.utils.rx.ErrorResourceProvider;
import com.gnoemes.shikimoriapp.utils.rx.ErrorResourceProviderImpl;
import com.gnoemes.shikimoriapp.utils.rx.RxUtils;
import com.gnoemes.shikimoriapp.utils.rx.SingleErrorHandler;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class UtilModule {

    @Provides
    @Singleton
    static ErrorProcessing bindErrorProcessing(ErrorResourceProvider errorResourceProvider) {
        return new ErrorProcessing(errorResourceProvider);
    }

    @Provides
    @Singleton
    static SingleErrorHandler bindSingleErrorHandler(ErrorProcessing errorProcessing) {
        return new SingleErrorHandler(errorProcessing);
    }

    @Provides
    @Singleton
    static RxUtils bindRxUtils() {
        return new RxUtils();
    }

    @Binds
    @Singleton
    abstract ImageLoader bindImageLoader(PicassoImageLoaderImpl picassoImageLoader);

    @Binds
    @Singleton
    abstract ErrorResourceProvider bindErrorResourceProvider(ErrorResourceProviderImpl resourceProvider);

    @Binds
    abstract AnimeResponseConverter bindAnimeResponseConverter(AnimeResponseConverterImpl converter);

    @Binds
    abstract DateTimeConverter bindDateTimeConverter(DateTimeConverterImpl converter);

    @Binds
    abstract DateTimeResourceProvider bindResourceProvider(DateTimeResourceProviderImpl provider);

    @Binds
    @Singleton
    abstract DateTimeUtils bindDateTimeUtils(DateTimeUtilsImpl utils);
}
