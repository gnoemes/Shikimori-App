package com.gnoemes.shikimoriapp.data.repository.user.converter;

import com.gnoemes.shikimoriapp.entity.user.data.UserBriefResponse;
import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief;

import javax.inject.Inject;

public class UserBriefResponseConverterImpl implements UserBriefResponseConverter {


    @Inject
    public UserBriefResponseConverterImpl() {
    }

    @Override
    public UserBrief apply(UserBriefResponse response) {
        return new UserBrief(response.getId(),
                response.getNickname(),
                response.getAvatar(),
                response.getImageResponse().getX48Url(),
                response.getLastOnline(),
                response.getName(),
                response.getSex(),
                response.getWebsite());
    }
}
