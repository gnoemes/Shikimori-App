package com.gnoemes.shikimoriapp.presentation.view.common.fragment;

public interface ActivityCallback {
    /**
     * Show loading
     */
    void onShowLoading();

    /**
     * Hide loading
     */
    void onHideLoading();

    /**
     * Show error
     */
    void onShowError(String message);

}
