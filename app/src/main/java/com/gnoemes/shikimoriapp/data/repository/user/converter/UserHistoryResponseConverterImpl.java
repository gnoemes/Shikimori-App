package com.gnoemes.shikimoriapp.data.repository.user.converter;

import com.gnoemes.shikimoriapp.entity.user.data.UserHistoryResponse;
import com.gnoemes.shikimoriapp.entity.user.domain.UserHistory;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class UserHistoryResponseConverterImpl implements UserHistoryResponseConverter {

    private TargetResponseConverter converter;
    private DateTimeComparator comparator;

    @Inject
    public UserHistoryResponseConverterImpl(TargetResponseConverter converter) {
        this.converter = converter;
        comparator = DateTimeComparator.getInstance();
    }

    @Override
    public List<UserHistory> apply(List<UserHistoryResponse> userHistoryResponses) {
        List<UserHistory> histories = new ArrayList<>();

        for (UserHistoryResponse response : userHistoryResponses) {
            histories.add(convertResponse(response));
        }

        return histories;
    }

    private UserHistory convertResponse(UserHistoryResponse response) {
        return new UserHistory(response.getId(),
                response.getActionDate(),
                response.getDescription(),
                converter.convertFrom(response.getAnimeResponse()));
    }

    @Override
    public int compare(DateTime first, DateTime second) {
        return comparator.compare(first, second);
    }
}
