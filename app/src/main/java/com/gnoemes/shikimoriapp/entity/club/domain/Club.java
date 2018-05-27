package com.gnoemes.shikimoriapp.entity.club.domain;

import com.gnoemes.shikimoriapp.entity.app.domain.ClubPolicy;

public class Club {
    private long id;
    private String name;
    private String largeLogoUrl;
    private String smallLogoUrl;
    private boolean isCensored;
    private ClubPolicy joinClubPolicy;
    private ClubPolicy commentClubPolicy;

    public Club(long id, String name, String largeLogoUrl,
                String smallLogoUrl, boolean isCensored, ClubPolicy joinClubPolicy,
                ClubPolicy commentClubPolicy) {
        this.id = id;
        this.name = name;
        this.largeLogoUrl = largeLogoUrl;
        this.smallLogoUrl = smallLogoUrl;
        this.isCensored = isCensored;
        this.joinClubPolicy = joinClubPolicy;
        this.commentClubPolicy = commentClubPolicy;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLargeLogoUrl() {
        return largeLogoUrl;
    }

    public String getSmallLogoUrl() {
        return smallLogoUrl;
    }

    public boolean isCensored() {
        return isCensored;
    }

    public ClubPolicy getJoinClubPolicy() {
        return joinClubPolicy;
    }

    public ClubPolicy getCommentClubPolicy() {
        return commentClubPolicy;
    }
}
