package com.gnoemes.shikimoriapp.entity.user.presentation.profile;

import android.support.annotation.Nullable;

import java.util.List;

public class ProfileRatesInfoItem extends BaseProfileItem {

    @Nullable
    private CombinedStatusViewModel watchedStatus;
    @Nullable
    private CombinedStatusViewModel inProgressStatus;
    @Nullable
    private CombinedStatusViewModel droppedStatus;
    private List<String> statuses;
    private boolean hasAnime;

    public ProfileRatesInfoItem() {
    }

    public ProfileRatesInfoItem(@Nullable CombinedStatusViewModel watchedStatus,
                                @Nullable CombinedStatusViewModel inProgressStatus,
                                @Nullable CombinedStatusViewModel droppedStatus,
                                List<String> statuses,
                                boolean hasAnime) {
        this.watchedStatus = watchedStatus;
        this.inProgressStatus = inProgressStatus;
        this.droppedStatus = droppedStatus;
        this.statuses = statuses;
        this.hasAnime = hasAnime;
    }

    @Nullable
    public CombinedStatusViewModel getWatchedStatus() {
        return watchedStatus;
    }

    @Nullable
    public CombinedStatusViewModel getInProgressStatus() {
        return inProgressStatus;
    }

    @Nullable
    public CombinedStatusViewModel getDroppedStatus() {
        return droppedStatus;
    }

    public List<String> getStatuses() {
        return statuses;
    }

    public boolean isHasAnime() {
        return hasAnime;
    }
}
