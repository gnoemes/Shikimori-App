package com.gnoemes.shikimoriapp.data.repository.user.converter;

import com.gnoemes.shikimoriapp.BuildConfig;
import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.rates.domain.RateStatus;
import com.gnoemes.shikimoriapp.entity.user.data.ActivityPointResponse;
import com.gnoemes.shikimoriapp.entity.user.data.StatusResponse;
import com.gnoemes.shikimoriapp.entity.user.data.UserProfileResponse;
import com.gnoemes.shikimoriapp.entity.user.data.UserStatsResponse;
import com.gnoemes.shikimoriapp.entity.user.domain.GraphInterval;
import com.gnoemes.shikimoriapp.entity.user.domain.Status;
import com.gnoemes.shikimoriapp.entity.user.domain.UserProfile;
import com.gnoemes.shikimoriapp.entity.user.domain.UserStats;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class UserProfileResponseConverterImpl implements UserProfileResponseConverter {

    @Inject
    public UserProfileResponseConverterImpl() {
    }

    @Override
    public UserProfile apply(UserProfileResponse response) {
        return new UserProfile(
                response.getId(),
                response.getNickname(),
                convertUrl(response.getImageResponse().getX160Url()),
                convertUrl(response.getImageResponse().getX48Url()),
                response.getLastOnlineDateTime(),
                response.getLastOnline(),
                response.getName(),
                response.getSex(),
                response.getFullYears(),
                response.getWebsite(),
                response.getLocation(),
                response.isBanned(),
                response.getAbout(),
                convertCommonInfo(response.getCommonInfo()),
                false,
                response.isShowComments(),
                response.getFriend(),
                response.isIgnored(),
                convertUserStats(response.getStatsResponse())
        );
    }

    private String convertUrl(String url) {
        if (url.contains("http")) {
            return url;
        }
        return BuildConfig.ShikimoriBaseUrl.concat(url);
    }

    private String convertCommonInfo(List<String> commonInfo) {
        final String DELIMITER = " / ";
        StringBuilder builder = new StringBuilder();
        for (String s : commonInfo) {
            builder.append(s)
                    .append(DELIMITER);
        }

        builder.replace(builder.lastIndexOf(DELIMITER), builder.length() - 1, "");

        return builder.toString()
                .replaceAll("<.[spn].+?>", "")
                .replaceAll("(class.\".+?\" )", "");
    }

    private UserStats convertUserStats(UserStatsResponse response) {
        List<Status> statuses = new ArrayList<>();
        for (StatusResponse statusResponse : response.getStatusResponse().getAnimeStasuses()) {
            statuses.add(convertStatus(statusResponse));
        }

        return new UserStats(statuses, response.isHasAnime());
    }

    @Deprecated
    private GraphInterval convertGraphPoints(ActivityPointResponse response) {
        DateTime start = new DateTime(response.getDates().get(0));
        DateTime end = new DateTime(response.getDates().get(1));

        return new GraphInterval(start, end, response.getValue());
    }

    private Status convertStatus(StatusResponse statusResponse) {
        return new Status(statusResponse.getId(),
                convertRateStatus(statusResponse.getName()),
                statusResponse.getSize(),
                convertType(statusResponse.getType()));
    }

    private Type convertType(String typeString) {
        for (Type type : Type.values()) {
            if (type.isEqualType(typeString)) {
                return type;
            }
        }
        return null;
    }

    private RateStatus convertRateStatus(String name) {
        for (RateStatus rateStatus : RateStatus.values()) {
            if (rateStatus.isEqualStatus(name)) {
                return rateStatus;
            }
        }
        return null;
    }
}
