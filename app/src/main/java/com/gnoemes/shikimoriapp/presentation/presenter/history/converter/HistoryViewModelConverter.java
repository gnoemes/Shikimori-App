package com.gnoemes.shikimoriapp.presentation.presenter.history.converter;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.user.domain.UserHistory;

import java.util.List;

public interface HistoryViewModelConverter {

    List<BaseItem> convertFrom(List<UserHistory> prevList, List<UserHistory> historyList);
}
