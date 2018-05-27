package com.gnoemes.shikimoriapp.data.repository.comments.converter;

import com.gnoemes.shikimoriapp.data.repository.user.converter.UserBriefResponseConverter;
import com.gnoemes.shikimoriapp.entity.comments.data.CommentResponse;
import com.gnoemes.shikimoriapp.entity.comments.domain.Comment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CommentsResponseConverterImpl implements CommentsResponseConverter {

    private UserBriefResponseConverter converter;

    @Inject
    public CommentsResponseConverterImpl(UserBriefResponseConverter converter) {
        this.converter = converter;
    }

    @Override
    public List<Comment> apply(List<CommentResponse> commentResponses) {
        List<Comment> comments = new ArrayList<>();

        for (CommentResponse response : commentResponses) {
            comments.add(convertResponse(response));
        }


        return comments;
    }

    @Override
    public Comment convertResponse(CommentResponse response) {
        return new Comment(response.getId(),
                response.getUserId(),
                response.getCommentableId(),
                response.getBody(),
                response.getCreatedDate(),
                response.isSummary(),
                converter.apply(response.getUserBriefResponse()));
    }
}
