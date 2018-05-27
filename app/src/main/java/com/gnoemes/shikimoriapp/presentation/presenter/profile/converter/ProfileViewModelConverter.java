package com.gnoemes.shikimoriapp.presentation.presenter.profile.converter;

import com.gnoemes.shikimoriapp.entity.club.domain.Club;
import com.gnoemes.shikimoriapp.entity.user.domain.Favorites;
import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief;
import com.gnoemes.shikimoriapp.entity.user.domain.UserProfile;
import com.gnoemes.shikimoriapp.entity.user.domain.UserStats;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.BaseProfileItem;

import java.util.List;

public interface ProfileViewModelConverter {

    BaseProfileItem convertActivityItem(UserStats stats);

    BaseProfileItem convertHeadItem(UserProfile profile);

    BaseProfileItem convertSocialItem(List<UserBrief> userBriefs, List<Club> clubs);

    BaseProfileItem convertRatesInfoItem(UserStats stats);

    BaseProfileItem convertOtherItem(Favorites favorites);
}
