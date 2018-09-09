package com.gnoemes.shikimoriapp.di.app.module;

import com.gnoemes.shikimoriapp.data.local.preferences.UserSettingsConverter;
import com.gnoemes.shikimoriapp.data.local.preferences.UserSettingsConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.FranchiseResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.FranchiseResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.LinkResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.LinkResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.RolesResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.RolesResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.app.converter.GenreResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.app.converter.GenreResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.app.converter.ImageResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.app.converter.ImageResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.app.converter.LinkedContentResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.app.converter.LinkedContentResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.club.ClubResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.club.ClubResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.comments.converter.CommentsResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.comments.converter.CommentsResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.download.converter.PlayVideoToDownloadConverter;
import com.gnoemes.shikimoriapp.data.repository.download.converter.PlayVideoToDownloadConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.download.converter.SmotretAnimeDownloadConverter;
import com.gnoemes.shikimoriapp.data.repository.download.converter.SmotretAnimeDownloadConveterImpl;
import com.gnoemes.shikimoriapp.data.repository.manga.converter.MangaResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.manga.converter.MangaResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.rates.converter.AnimeRateResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.rates.converter.AnimeRateResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.roles.converter.CharacterResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.roles.converter.CharacterResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.roles.converter.PersonResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.roles.converter.PersonResponseConverterImpl;
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
import com.gnoemes.shikimoriapp.presentation.presenter.anime.converter.LinkViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.converter.LinkViewModelConverterImpl;
import com.gnoemes.shikimoriapp.presentation.presenter.fav.converter.RateStatusCountConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.fav.converter.RateStatusCountConverterImpl;
import com.gnoemes.shikimoriapp.presentation.view.anime.converter.FranchiseNodeToStringConverter;
import com.gnoemes.shikimoriapp.presentation.view.anime.converter.FranchiseNodeToStringConverterImpl;
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
import dagger.Reusable;

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
    static SingleErrorHandler<Object> bindKotlinSingleErrorHandler(ErrorProcessing errorProcessing) {
        return new SingleErrorHandler<>(errorProcessing);
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
    @Reusable
    AnimeResponseConverter bindAnimeResponseConverter(AnimeResponseConverterImpl converter);

    @Binds
    @Reusable
    DateTimeConverter bindDateTimeConverter(DateTimeConverterImpl converter);

    @Binds
    @Reusable
    DateTimeResourceProvider bindResourceProvider(DateTimeResourceProviderImpl provider);

    @Binds
    @Singleton
    DateTimeUtils bindDateTimeUtils(DateTimeUtilsImpl utils);

    @Binds
    @Reusable
    UserSettingsConverter bindUserSettingsConverter(UserSettingsConverterImpl converter);

    @Binds
    @Reusable
    UserBriefResponseConverter bindUserBriefResponseConverter(UserBriefResponseConverterImpl converter);

    @Binds
    @Reusable
    AnimeRateResponseConverter bindAnimeRateResponseConverter(AnimeRateResponseConverterImpl converter);

    @Binds
    @Reusable
    RateResourceProvider bindRateResourceProvider(RateResourceProviderImpl provider);

    @Binds
    @Reusable
    UserRatesAnimeResourceProvider bindUserRatesAnimeResourceProvider(UserRatesAnimeResourceProviderImpl provider);

    @Binds
    @Reusable
    AnimeRateViewModelConverter bindAnimeRateViewModelConverter(AnimeRateViewModelConverterImpl converter);

    @Binds
    @Reusable
    UserProfileResponseConverter bindUserProfileResponseConverter(UserProfileResponseConverterImpl converter);

    @Binds
    @Reusable
    FavoritesResponseConverter bindFavoritesResponseConverter(FavoritesResponseConverterImpl converter);

    @Binds
    @Reusable
    ClubResponseConverter bindClubResponseConverter(ClubResponseConverterImpl converter);

    @Binds
    @Reusable
    CommentsResponseConverter bindCommentsResponseConverter(CommentsResponseConverterImpl converter);

    @Binds
    @Reusable
    UserBanConverter bindUserBanConverter(UserBanConverterImpl converter);

    @Binds
    @Reusable
    UserHistoryResponseConverter bindUserHistoryResponseConverter(UserHistoryResponseConverterImpl converter);

    @Binds
    @Reusable
    TargetResponseConverter bindTargetResponseConverter(TargetResponseConverterImpl converter);

    @Binds
    @Reusable
    MangaResponseConverter bindMangaResponseConverter(MangaResponseConverterImpl converter);

    @Binds
    @Reusable
    RolesResponseConverter bindRolesResponseConverter(RolesResponseConverterImpl converter);

    @Binds
    @Reusable
    RateStatusCountConverter bindRateStatusCountConverter(RateStatusCountConverterImpl converter);

    @Binds
    @Reusable
    ForumResponseConverter bindForumResponseConverter(ForumResponseConverterImpl converter);

    @Binds
    @Reusable
    TopicResponseConverter bindTopicResponseConverter(TopicResponseConverterImpl converter);

    @Binds
    @Reusable
    LinkedContentResponseConverter bindContentResponseConveter(LinkedContentResponseConverterImpl converter);

    @Binds
    @Reusable
    PersonResponseConverter bindPersonResponseConverter(PersonResponseConverterImpl converter);

    @Binds
    @Reusable
    PlayVideoToDownloadConverter bindPlayVideoToDownloadConverter(PlayVideoToDownloadConverterImpl converter);

    @Binds
    SmotretAnimeDownloadConverter bindSmotretAnimeDownloadConverter(SmotretAnimeDownloadConveterImpl conveter);

    @Binds
    @Reusable
    ImageResponseConverter bindImageResponseConverter(ImageResponseConverterImpl converter);

    @Binds
    @Reusable
    FranchiseResponseConverter bindAnimeFranchiseResponseConverter(FranchiseResponseConverterImpl converter);

    @Binds
    @Reusable
    FranchiseNodeToStringConverter bindAnimeFranchiseNodeToStringConverter(FranchiseNodeToStringConverterImpl converter);

    @Binds
    @Reusable
    LinkResponseConverter bindAnimeLinkResponseConverter(LinkResponseConverterImpl converter);

    @Binds
    @Reusable
    LinkViewModelConverter bindAnimeLinkViewModelConverter(LinkViewModelConverterImpl converter);

    @Binds
    @Reusable
    GenreResponseConverter bindGenreResponseConverter(GenreResponseConverterImpl converter);

    @Binds
    @Reusable
    CharacterResponseConverter bindCharacterResponseConverter(CharacterResponseConverterImpl converter);
}
