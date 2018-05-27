package com.gnoemes.shikimoriapp.data.repository.user.converter;

import com.gnoemes.shikimoriapp.data.repository.comments.converter.CommentsResponseConverter;
import com.gnoemes.shikimoriapp.entity.user.data.UserBanResponse;
import com.gnoemes.shikimoriapp.entity.user.domain.UserBan;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class UserBanConverterImpl implements UserBanConverter {

    private CommentsResponseConverter commentConverter;
    private UserBriefResponseConverter userBriefConverter;

    @Inject
    public UserBanConverterImpl(CommentsResponseConverter commentConverter,
                                UserBriefResponseConverter userBriefConverter) {
        this.commentConverter = commentConverter;
        this.userBriefConverter = userBriefConverter;
    }

    @Override
    public List<UserBan> apply(List<UserBanResponse> userBanResponses) {
        List<UserBan> bans = new ArrayList<>();

        for (UserBanResponse response : userBanResponses) {
            bans.add(convertResponse(response));
        }

        return bans;
    }

    private UserBan convertResponse(UserBanResponse response) {
        return new UserBan(response.getId(),
                response.getUserId(),
                response.getModeratorId(),
                commentConverter.convertResponse(response.getComment()),
                response.getReason(),
                response.getCreatedAt(),
                response.getDuration(),
                userBriefConverter.apply(response.getUserBriefResponse()),
                userBriefConverter.apply(response.getModeratorBriefResponse())
        );
    }
}
