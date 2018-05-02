package com.gnoemes.shikimoriapp.presentation.view.common.activity;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;

public interface BaseView extends MvpView {

    /**
     * Hide keyboard
     */
    void hideSoftInput();

    /**
     * Show error message
     */
    void onShowError(String error);

    /**
     * Show loading Dialog
     */
    void onShowLoading();

    /**
     * Hide loading dialog
     */
    void onHideLoading();

    /**
     * Set title
     */
    void setTitle(String title);

    /**
     * Set title
     */
    void setTitle(@StringRes int stringRes);

    /**
     * Initialize toolbar
     */
    void initToolbar();
}
