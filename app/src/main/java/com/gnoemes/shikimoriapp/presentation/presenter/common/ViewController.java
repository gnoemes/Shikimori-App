package com.gnoemes.shikimoriapp.presentation.presenter.common;

import java.util.List;

public interface ViewController<T> {

    void showEmptyError(boolean show, Throwable throwable);

    void showEmptyView(boolean show);

    void showList(boolean show, List<T> list);

    void showRefreshProgress(boolean show);

    void showPageProgress(boolean show);

    void showEmptyProgress(boolean show);

    void showError(Throwable throwable);
}
