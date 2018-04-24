package com.gnoemes.shikimoriapp.presentation.presenter.common;

public interface Paginator {

    /**
     * Restart
     */
    void restart();

    /**
     * Refresh
     */
    void refresh();

    /**
     * Load new page
     */
    void loadNewPage();

    /**
     * Release
     */
    void release();
}
