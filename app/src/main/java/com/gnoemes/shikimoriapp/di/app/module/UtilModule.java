package com.gnoemes.shikimoriapp.di.app.module;

import com.gnoemes.shikimoriapp.data.local.preferences.UserSettingsConverter;
import com.gnoemes.shikimoriapp.data.local.preferences.UserSettingsConverterImpl;
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
import com.gnoemes.shikimoriapp.utils.rx.CompletableErrorHandler;
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
public interface UtilModule {

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
    static CompletableErrorHandler bindCompletableErrorHandler(ErrorProcessing errorProcessing) {
        return new CompletableErrorHandler(errorProcessing);
    }

    @Provides
    @Singleton
    static RxUtils bindRxUtils() {
        return new RxUtils();
    }

    @Binds
    @Singleton
    ImageLoader bindImageLoader(PicassoImageLoaderImpl picassoImageLoader);
//    ImageLoader bindImageLoader(UniversalImageLoader picassoImageLoader);

    @Binds
    @Singleton
    ErrorResourceProvider bindErrorResourceProvider(ErrorResourceProviderImpl resourceProvider);

    @Binds
    AnimeResponseConverter bindAnimeResponseConverter(AnimeResponseConverterImpl converter);

    @Binds
    DateTimeConverter bindDateTimeConverter(DateTimeConverterImpl converter);

    @Binds
    DateTimeResourceProvider bindResourceProvider(DateTimeResourceProviderImpl provider);

    @Binds
    @Singleton
    DateTimeUtils bindDateTimeUtils(DateTimeUtilsImpl utils);

    @Binds
    UserSettingsConverter bindUserSettingsConverter(UserSettingsConverterImpl converter);
}
