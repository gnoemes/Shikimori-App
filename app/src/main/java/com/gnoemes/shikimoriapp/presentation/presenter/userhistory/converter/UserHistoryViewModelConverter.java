package com.gnoemes.shikimoriapp.presentation.presenter.userhistory.converter;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.user.domain.UserHistory;

import java.util.List;

public interface UserHistoryViewModelConverter {

    List<BaseItem> convertFrom(List<UserHistory> prevList, List<UserHistory> historyList);
}
