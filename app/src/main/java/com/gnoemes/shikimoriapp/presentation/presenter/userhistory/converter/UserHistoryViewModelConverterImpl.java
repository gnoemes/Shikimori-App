package com.gnoemes.shikimoriapp.presentation.presenter.userhistory.converter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.DoubleDividerItem;
import com.gnoemes.shikimoriapp.entity.user.domain.UserHistory;
import com.gnoemes.shikimoriapp.entity.user.presentation.history.BaseHistoryItem;
import com.gnoemes.shikimoriapp.entity.user.presentation.history.DateHistoryItem;
import com.gnoemes.shikimoriapp.entity.user.presentation.history.HistoryItem;
import com.gnoemes.shikimoriapp.utils.date.DateTimeUtils;
import com.gnoemes.shikimoriapp.utils.date.converter.DateTimeConverter;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class UserHistoryViewModelConverterImpl implements UserHistoryViewModelConverter {

    private static final int HALF_WEEK = 2;
    private DateTimeConverter converter;
    private DateTimeUtils utils;

    @Inject
    public UserHistoryViewModelConverterImpl(DateTimeConverter converter,
                                             DateTimeUtils utils) {
        this.converter = converter;
        this.utils = utils;
    }

    @Override
    public List<BaseItem> convertFrom(@Nullable List<UserHistory> prevList, List<UserHistory> historyList) {
        List<BaseItem> items = new ArrayList<>();

        DateTime prevActionDate = prevList == null || prevList.isEmpty() ? null : prevList.get(historyList.size() - 1).getActionDate();

        boolean days;
        boolean weeks;
        boolean months;


        boolean group;

        //TODO too hard logic
        if (historyList != null && !historyList.isEmpty()) {
            for (UserHistory history : historyList) {
                boolean hasDate = prevActionDate != null;
                days = !hasDate || (utils.isSameWeek(history.getActionDate(), prevActionDate)
                        && !utils.isSameDay(history.getActionDate(), prevActionDate)
                        && isHalfWeek(history.getActionDate(), utils.getNowDateTime()));
                weeks = hasDate && (utils.isSameMonth(history.getActionDate(), prevActionDate)
                        && (!utils.isSameWeek(history.getActionDate(), prevActionDate)
                        || (!isHalfWeek(history.getActionDate(), prevActionDate)
                        && utils.isSameWeek(history.getActionDate())))
                        && utils.isSameMonth(history.getActionDate()));
                months = hasDate && (utils.isSameYear(history.getActionDate(), prevActionDate)
                        && !utils.isSameMonth(history.getActionDate(), prevActionDate)
                        && utils.isSameYear(history.getActionDate()));

                group = days || weeks || months || !utils.isSameYear(history.getActionDate(), prevActionDate);

                if (group) {
                    items.add(new DoubleDividerItem());
                    items.add(convertDateItem(history));
                }
                prevActionDate = history.getActionDate();
                items.add(convertValueItem(history));
            }
        }
        return items;
    }

    private boolean isHalfWeek(DateTime first, DateTime second) {
        return Math.abs(Days.daysBetween(first, second).getDays()) < HALF_WEEK;
    }

    private BaseHistoryItem convertValueItem(UserHistory history) {
        String date = converter.convertDateAgoToString(history.getActionDate());

        Long targetId = null;
        String targetName = null;
        String targetImage = null;
        Type targetType = null;
        if (history.getTarget() != null) {
            targetId = history.getTarget().getId();
            targetName = TextUtils.isEmpty(history.getTarget().getRussianName()) ? history.getTarget().getName() : history.getTarget().getRussianName();
            targetImage = history.getTarget().getAnimeImage().getOriginal();
            targetType = Type.ANIME;
        }
        return new HistoryItem(history.getId(),
                date,
                history.getDescription(),
                history.getTarget() != null,
                targetId,
                targetName,
                targetImage,
                targetType);
    }

    private BaseHistoryItem convertDateItem(UserHistory history) {
        String date = converter.convertHistoryDateToString(history.getActionDate());
        return new DateHistoryItem(date);
    }
}
