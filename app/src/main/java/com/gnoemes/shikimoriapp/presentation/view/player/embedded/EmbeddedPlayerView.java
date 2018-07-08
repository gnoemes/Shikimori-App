package com.gnoemes.shikimoriapp.presentation.view.player.embedded;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationWithSources;
import com.gnoemes.shikimoriapp.entity.series.domain.PlayVideo;
import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseNetworkView;

public interface EmbeddedPlayerView extends BaseNetworkView {

    void setPlayerData(TranslationWithSources translation, int position);

    void showSystemMessage(String s);

    void playOrAddNewVideo(PlayVideo playVideo);
}
