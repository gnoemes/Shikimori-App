package com.gnoemes.shikimoriapp.presentation.presenter.profile.converter;

import com.gnoemes.shikimoriapp.BuildConfig;
import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.club.domain.Club;
import com.gnoemes.shikimoriapp.entity.user.domain.Favorites;
import com.gnoemes.shikimoriapp.entity.user.domain.Status;
import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief;
import com.gnoemes.shikimoriapp.entity.user.domain.UserProfile;
import com.gnoemes.shikimoriapp.entity.user.domain.UserStats;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.BaseProfileItem;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.CombinedStatusViewModel;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.ImageContent;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.ProfileHeadItem;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.ProfileOtherItem;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.ProfileRatesInfoItem;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.ProfileSocialItem;
import com.gnoemes.shikimoriapp.presentation.view.common.widget.RateProgressView;
import com.gnoemes.shikimoriapp.presentation.view.profile.provider.ProfileResourceProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ProfileViewModelConverterImpl implements ProfileViewModelConverter {

    private ProfileResourceProvider resourceProvider;

    @Inject
    public ProfileViewModelConverterImpl(ProfileResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;
    }

    @Override
    public BaseProfileItem convertActivityItem(UserStats stats) {
        return null;
    }

    @Override
    public BaseProfileItem convertHeadItem(UserProfile profile) {
        return new ProfileHeadItem(profile.getId(),
                profile.getNickname(),
                profile.getLargeImage(),
                profile.getSmallImage(),
                profile.getLastOnline(),
                profile.getName(),
                profile.getSex(),
                profile.getFullYears(),
                profile.getWebsite(),
                profile.getLocation(),
                profile.isBanned(),
                convertAbout(profile.getAbout()),
                profile.getCommmonInfo(),
                profile.isMe(),
                profile.isShowComments(),
                profile.isFriend(),
                profile.isIgnored());
    }

    private String convertAbout(String about) {
        if (about == null) {
            return null;
        }
        return about.replaceAll("\\[.+?]", "");
    }

    @Override
    public BaseProfileItem convertSocialItem(List<UserBrief> userBriefs, List<Club> clubsList) {
        List<ImageContent> friends = new ArrayList<>();
        List<ImageContent> clubs = new ArrayList<>();

        for (UserBrief brief : userBriefs) {
            friends.add(new ImageContent(brief.getId(), urlBuilder(brief.getSmallAvatarUrl())));
        }

        for (Club club : clubsList) {
            clubs.add(new ImageContent(club.getId(), urlBuilder(club.getImage().getOriginal())));
        }

        return new ProfileSocialItem(friends, clubs);
    }

    private String urlBuilder(String url) {
        if (url.contains("http")) {
            return url;
        }
        return BuildConfig.ShikimoriBaseUrl.concat(url);
    }

    @Override
    public BaseProfileItem convertRatesInfoItem(UserStats stats) {
        int watchedCount = 0;
        int plannedCount = 0;
        int watchingCount = 0;
        int onHoldCount = 0;
        int rewatchingCount = 0;
        int droppedCount = 0;

        List<String> rateStasuses = new ArrayList<>();
        List<String> formatStrings = resourceProvider.getRates();

        for (Status status : stats.getAnimeStatuses()) {
            switch (status.getStatus()) {
                case COMPLETED:
                    watchedCount += status.getSize();
                    break;
                case PLANNED:
                    plannedCount += status.getSize();
                    break;
                case WATCHING:
                    watchingCount += status.getSize();
                    break;
                case ON_HOLD:
                    onHoldCount += status.getSize();
                    break;
                case DROPPED:
                    droppedCount += status.getSize();
                    break;
                case REWATCHING:
                    rewatchingCount += status.getSize();
                    break;
            }
        }

        int inProgressCount = plannedCount + watchingCount + onHoldCount;

        rateStasuses.add(formatStrings.get(0));
        rateStasuses.add(createFormatedString(formatStrings.get(1), watchingCount));
        rateStasuses.add(createFormatedString(formatStrings.get(2), plannedCount));
        rateStasuses.add(createFormatedString(formatStrings.get(3), rewatchingCount));
        rateStasuses.add(createFormatedString(formatStrings.get(4), watchedCount));
        rateStasuses.add(createFormatedString(formatStrings.get(5), onHoldCount));
        rateStasuses.add(createFormatedString(formatStrings.get(6), droppedCount));

        return new ProfileRatesInfoItem(
                watchedCount == 0 ? null : new CombinedStatusViewModel(watchedCount, RateProgressView.RateBarStatus.WATCHED, Type.ANIME),
                inProgressCount == 0 ? null : new CombinedStatusViewModel(inProgressCount, RateProgressView.RateBarStatus.IN_PROGRESS, Type.ANIME),
                droppedCount == 0 ? null : new CombinedStatusViewModel(droppedCount, RateProgressView.RateBarStatus.DROPPED, Type.ANIME),
                rateStasuses,
                stats.isHasAnime());
    }

    private String createFormatedString(String format, int count) {
        return String.format(format, String.valueOf(count));
    }

    @Override
    public BaseProfileItem convertOtherItem(Favorites favorites) {
        return new ProfileOtherItem(favorites.getAll());
    }
}
