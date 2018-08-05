package com.gnoemes.shikimoriapp.data.repository.social.converter;

import com.gnoemes.shikimoriapp.entity.forum.data.ForumResponse;
import com.gnoemes.shikimoriapp.entity.forum.domain.Forum;
import com.gnoemes.shikimoriapp.entity.forum.domain.ForumType;
import com.gnoemes.shikimoriapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ForumResponseConverterImpl implements ForumResponseConverter {

    @Inject
    public ForumResponseConverterImpl() {
    }

    @Override
    public List<Forum> apply(List<ForumResponse> responses) {
        List<Forum> items = new ArrayList<>();

        for (ForumResponse response : responses) {
            items.add(convertResponse(response));
        }

        return items;
    }

    @Override
    public Forum convertResponse(ForumResponse response) {
        return new Forum(response.getId(),
                response.getName(),
                convertForumType(response.getForumType()),
                Utils.appendHostIfNeed(response.getUrl()));
    }

    private ForumType convertForumType(String forumType) {
        for (ForumType type : ForumType.values()) {
            if (type.isEqualType(forumType)) {
                return type;
            }
        }
        return ForumType.ALL;
    }
}
