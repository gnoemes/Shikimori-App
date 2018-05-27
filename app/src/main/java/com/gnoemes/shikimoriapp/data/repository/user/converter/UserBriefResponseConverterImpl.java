package com.gnoemes.shikimoriapp.data.repository.user.converter;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.user.data.UserBriefResponse;
import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class UserBriefResponseConverterImpl implements UserBriefResponseConverter {


    @Inject
    public UserBriefResponseConverterImpl() {
    }

    @Override
    public UserBrief apply(@Nullable UserBriefResponse response) {

        if (response == null) {
            return null;
        }
        return new UserBrief(response.getId(),
                response.getNickname(),
                response.getAvatar(),
                response.getImageResponse().getX48Url(),
                response.getLastOnline(),
                response.getName(),
                response.getSex(),
                response.getWebsite());
    }

    @Override
    public List<UserBrief> convertList(List<UserBriefResponse> response) {
        List<UserBrief> userBriefs = new ArrayList<>();

        for (UserBriefResponse briefResponse : response) {
            userBriefs.add(apply(briefResponse));
        }
        return userBriefs;
    }
}
