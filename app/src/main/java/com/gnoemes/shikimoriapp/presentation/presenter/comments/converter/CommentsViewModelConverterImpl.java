package com.gnoemes.shikimoriapp.presentation.presenter.comments.converter;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.DoubleDividerItem;
import com.gnoemes.shikimoriapp.entity.comments.domain.Comment;
import com.gnoemes.shikimoriapp.entity.comments.presentation.BaseCommentItem;
import com.gnoemes.shikimoriapp.entity.comments.presentation.CommentViewModel;
import com.gnoemes.shikimoriapp.utils.date.converter.DateTimeConverter;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class CommentsViewModelConverterImpl implements CommentsViewModelConverter {

    private final static String QUOTE_REGEX = "(\\[[/q.][^cr].*?])";
    private final static String IGNORE_REGEX = "(\\[+?.+?])";

    private DateTimeConverter dateTimeConverter;

    @Inject
    public CommentsViewModelConverterImpl(DateTimeConverter dateTimeConverter) {
        this.dateTimeConverter = dateTimeConverter;
    }

    @Override
    public List<BaseItem> convertListFrom(List<Comment> list) {
        List<BaseItem> items = new ArrayList<>();

        for (Comment comment : list) {
            items.add(new DoubleDividerItem());
            items.add(convertComment(comment));
        }

        return items;
    }

    private BaseCommentItem convertComment(Comment comment) {
        return new CommentViewModel(comment.getId(),
                comment.getUserId(),
                comment.getCommentableId(),
                convertBody(comment.getBody()),
                convertDate(comment.getCreatedDate()),
                comment.isSummary(),
                comment.getUserBrief().getSmallAvatarUrl(),
                comment.getUserBrief().getNickname());
    }

    private String convertDate(DateTime createdDate) {
        return dateTimeConverter.convertCommentDateTimeToString(createdDate);
    }

    private List<String> convertBody(String body) {
        List<String> elems = new ArrayList<>();
        if (body.regionMatches(true, 0, QUOTE_REGEX, 0, 0)) {
            elems = Arrays.asList(body.split(QUOTE_REGEX));
        } else {
            elems.add(body);
        }

        for (int i = 0; i < elems.size(); i++) {
            elems.set(i, elems.get(i).replaceAll(IGNORE_REGEX, ""));
        }

        return elems;
    }
}
