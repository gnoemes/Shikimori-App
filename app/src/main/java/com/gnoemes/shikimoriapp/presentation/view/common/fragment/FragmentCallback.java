package com.gnoemes.shikimoriapp.presentation.view.common.fragment;

public interface FragmentCallback {
    /**
     * Set title
     */
    void onSetTitle(String title);

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

    /**
     * Then back pressed
     */
    void onBackPressed();
}
