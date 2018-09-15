package com.gnoemes.shikimoriapp.presentation.presenter.fav.converter;

import com.gnoemes.shikimoriapp.entity.user.domain.Status;
import com.gnoemes.shikimoriapp.entity.user.domain.UserProfile;
import com.gnoemes.shikimoriapp.presentation.view.anime.provider.RateResourceProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RateStatusCountConverterImpl implements RateStatusCountConverter {

    private RateResourceProvider resourceProvider;

    @Inject
    public RateStatusCountConverterImpl(RateResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;
    }

    @Override
    public List<String> apply(UserProfile profile) {
        int watchedCount = 0;
        int plannedCount = 0;
        int watchingCount = 0;
        int onHoldCount = 0;
        int rewatchingCount = 0;
        int droppedCount = 0;

        List<String> stasuses = new ArrayList<>();
        List<String> formatStrings = resourceProvider.getAnimeRatesWithCount();

        for (Status status : profile.getUserStats().getAnimeStatuses()) {
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

        stasuses.add(createFormatedString(formatStrings.get(0), watchingCount));
        stasuses.add(createFormatedString(formatStrings.get(1), plannedCount));
        stasuses.add(createFormatedString(formatStrings.get(2), rewatchingCount));
        stasuses.add(createFormatedString(formatStrings.get(3), watchedCount));
        stasuses.add(createFormatedString(formatStrings.get(4), onHoldCount));
        stasuses.add(createFormatedString(formatStrings.get(5), droppedCount));
        return stasuses;
    }

    private String createFormatedString(String format, int count) {
        return String.format(format, String.valueOf(count));
    }
}
