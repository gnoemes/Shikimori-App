package com.gnoemes.shikimoriapp.data.repository.user.converter;

import com.gnoemes.shikimoriapp.entity.user.data.UserHistoryResponse;
import com.gnoemes.shikimoriapp.entity.user.domain.UserHistory;

import org.joda.time.DateTime;

import java.util.List;

import io.reactivex.functions.Function;

public interface UserHistoryResponseConverter extends Function<List<UserHistoryResponse>, List<UserHistory>> {

    int compare(DateTime first, DateTime second);
}
