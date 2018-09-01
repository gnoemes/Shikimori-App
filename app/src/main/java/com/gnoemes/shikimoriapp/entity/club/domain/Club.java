package com.gnoemes.shikimoriapp.entity.club.domain;

import com.gnoemes.shikimoriapp.entity.app.domain.ClubPolicy;
import com.gnoemes.shikimoriapp.entity.app.domain.LinkedContent;
import com.gnoemes.shikimoriapp.entity.app.domain.LinkedType;

public class Club extends LinkedContent {
    private long id;
    private String name;
    private ClubImage image;
    private boolean isCensored;
    private ClubPolicy joinClubPolicy;
    private ClubPolicy commentClubPolicy;

    public Club(long id, String name, ClubImage image,
                boolean isCensored, ClubPolicy joinClubPolicy,
                ClubPolicy commentClubPolicy) {
        super(id, name, LinkedType.CLUB, image.getOriginal());
        this.id = id;
        this.name = name;
        this.image = image;
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

    public ClubImage getImage() {
        return image;
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
