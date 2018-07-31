package com.gnoemes.shikimoriapp.di.app.module;

import com.gnoemes.shikimoriapp.data.local.preferences.UserSettingsConverter;
import com.gnoemes.shikimoriapp.data.local.preferences.UserSettingsConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.RolesResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.RolesResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.app.converter.LinkedContentResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.app.converter.LinkedContentResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.club.ClubResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.club.ClubResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.comments.converter.CommentsResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.comments.converter.CommentsResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.manga.MangaResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.manga.MangaResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.rates.converter.AnimeRateResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.rates.converter.AnimeRateResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.roles.converter.PersonResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.roles.converter.PersonResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.roles.converter.SeyuResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.roles.converter.SeyuResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.social.converter.ForumResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.social.converter.ForumResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.social.converter.TopicResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.social.converter.TopicResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.user.converter.FavoritesResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.user.converter.FavoritesResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.user.converter.TargetResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.user.converter.TargetResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.user.converter.UserBanConverter;
import com.gnoemes.shikimoriapp.data.repository.user.converter.UserBanConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.user.converter.UserBriefResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.user.converter.UserBriefResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.user.converter.UserHistoryResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.user.converter.UserHistoryResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.user.converter.UserProfileResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.user.converter.UserProfileResponseConverterImpl;
import com.gnoemes.shikimoriapp.presentation.presenter.fav.converter.RateStatusCountConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.fav.converter.RateStatusCountConverterImpl;
import com.gnoemes.shikimoriapp.presentation.view.anime.provider.RateResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.anime.provider.RateResourceProviderImpl;
import com.gnoemes.shikimoriapp.presentation.view.fav.converter.AnimeRateViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.fav.converter.AnimeRateViewModelConverterImpl;
import com.gnoemes.shikimoriapp.presentation.view.fav.provider.UserRatesAnimeResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.fav.provider.UserRatesAnimeResourceProviderImpl;
import com.gnoemes.shikimoriapp.utils.date.DateTimeUtils;
import com.gnoemes.shikimoriapp.utils.date.DateTimeUtilsImpl;
import com.gnoemes.shikimoriapp.utils.date.converter.DateTimeConverter;
import com.gnoemes.shikimoriapp.utils.date.converter.DateTimeConverterImpl;
import com.gnoemes.shikimoriapp.utils.date.provider.DateTimeResourceProvider;
import com.gnoemes.shikimoriapp.utils.date.provider.DateTimeResourceProviderImpl;
import com.gnoemes.shikimoriapp.utils.imageloader.GlideImageLoaderImpl;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
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
//    ImageLoader bindImageLoader(PicassoImageLoaderImpl picassoImageLoader);
//    ImageLoader bindImageLoader(UniversalImageLoader picassoImageLoader);
    ImageLoader bindImageLoader(GlideImageLoaderImpl loader);

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

    @Binds
    UserBriefResponseConverter bindUserBriefResponseConverter(UserBriefResponseConverterImpl converter);

    @Binds
    AnimeRateResponseConverter bindAnimeRateResponseConverter(AnimeRateResponseConverterImpl converter);

    @Binds
    RateResourceProvider bindRateResourceProvider(RateResourceProviderImpl provider);

    @Binds
    UserRatesAnimeResourceProvider bindUserRatesAnimeResourceProvider(UserRatesAnimeResourceProviderImpl provider);

    @Binds
    AnimeRateViewModelConverter bindAnimeRateViewModelConverter(AnimeRateViewModelConverterImpl converter);

    @Binds
    UserProfileResponseConverter bindUserProfileResponseConverter(UserProfileResponseConverterImpl converter);

    @Binds
    FavoritesResponseConverter bindFavoritesResponseConverter(FavoritesResponseConverterImpl converter);

    @Binds
    ClubResponseConverter bindClubResponseConverter(ClubResponseConverterImpl converter);

    @Binds
    CommentsResponseConverter bindCommentsResponseConverter(CommentsResponseConverterImpl converter);

    @Binds
    UserBanConverter bindUserBanConverter(UserBanConverterImpl converter);

    @Binds
    UserHistoryResponseConverter bindUserHistoryResponseConverter(UserHistoryResponseConverterImpl converter);

    @Binds
    TargetResponseConverter bindTargetResponseConverter(TargetResponseConverterImpl converter);

    @Binds
    MangaResponseConverter bindMangaResponseConverter(MangaResponseConverterImpl converter);

    @Binds
    RolesResponseConverter bindRolesResponseConverter(RolesResponseConverterImpl converter);

    @Binds
    SeyuResponseConverter bindSeyuResponseConverter(SeyuResponseConverterImpl converter);

    @Binds
    RateStatusCountConverter bindRateStatusCountConverter(RateStatusCountConverterImpl converter);

    @Binds
    ForumResponseConverter bindForumResponseConverter(ForumResponseConverterImpl converter);

    @Binds
    TopicResponseConverter bindTopicResponseConverter(TopicResponseConverterImpl converter);

    @Binds
    LinkedContentResponseConverter bindContentResponseConveter(LinkedContentResponseConverterImpl converter);

    @Binds
    PersonResponseConverter bindPersonResponseConverter(PersonResponseConverterImpl converter);
}
