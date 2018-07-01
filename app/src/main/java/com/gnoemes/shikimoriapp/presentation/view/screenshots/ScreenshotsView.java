package com.gnoemes.shikimoriapp.presentation.view.screenshots;

import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseNetworkView;

import java.util.List;

public interface ScreenshotsView extends BaseNetworkView {

    void setSubtitle(String subtitle);

    void setScreenshots(List<String> urls);
}
