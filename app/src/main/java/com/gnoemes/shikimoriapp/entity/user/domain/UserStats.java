package com.gnoemes.shikimoriapp.entity.user.domain;

import java.util.List;

public class UserStats {

    private List<Status> animeStatuses;
    private boolean hasAnime;

    public UserStats(List<Status> animeStatuses, boolean hasAnime) {
        this.animeStatuses = animeStatuses;
        this.hasAnime = hasAnime;
    }

    public List<Status> getAnimeStatuses() {
        return animeStatuses;
    }

    public boolean isHasAnime() {
        return hasAnime;
    }

}
