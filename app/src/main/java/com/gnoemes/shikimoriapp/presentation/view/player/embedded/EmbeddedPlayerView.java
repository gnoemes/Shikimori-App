package com.gnoemes.shikimoriapp.presentation.view.player.embedded;

import com.gnoemes.shikimoriapp.entity.series.domain.PlayVideo;
import com.gnoemes.shikimoriapp.entity.series.domain.VideoTrack;
import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseNetworkView;

public interface EmbeddedPlayerView extends BaseNetworkView {

    void showSystemMessage(String s);

    void updateInformation(PlayVideo playVideo);

    void playOrAddNewVideo(VideoTrack track);

    void disableNextButton();

    void disablePrevButton();

    void enableNextButton();

    void enablePrevButton();
}
