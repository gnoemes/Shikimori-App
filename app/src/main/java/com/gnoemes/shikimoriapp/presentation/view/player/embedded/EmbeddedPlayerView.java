package com.gnoemes.shikimoriapp.presentation.view.player.embedded;

import com.gnoemes.shikimoriapp.entity.series.domain.PlayVideo;
import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseNetworkView;

public interface EmbeddedPlayerView extends BaseNetworkView {

    void playOrAddNewVideo(PlayVideo playVideo, int position);

    void showSystemMessage(String s);
}
